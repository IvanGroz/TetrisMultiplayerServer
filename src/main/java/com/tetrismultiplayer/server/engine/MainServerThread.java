package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.database.dao.UserDAO;
import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;
import main.java.com.tetrismultiplayer.server.engine.game.ParentGameEngine;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for main server thread connecting and disconnecting new users and maintaining running games
 */
public class MainServerThread extends SwingWorker<Object, Object>
{
    private Main main;
    private MainPanel mainPanel;
    private ServerSocket serverSocket;
    private Integer serverPort;
    private Executor userThreadExecutor;
    private AtomicInteger clientsNumber;
    private Integer maxUserThreads;
    private LinkedList<UserServerThread> userThreadList;
    private LinkedList<RemoteUser> usersList;
    private HashMap<String, ParentGameEngine> gamesList;

    public MainServerThread(Main main, Integer serverPort, Integer maxUserThreads)
    {
        this.main = main;
        this.mainPanel = main.getMainPanel();
        this.serverPort = serverPort;
        this.maxUserThreads = maxUserThreads;
        this.clientsNumber = new AtomicInteger(0);
        this.userThreadExecutor = Executors.newFixedThreadPool(maxUserThreads);
        this.userThreadList = new LinkedList<>();
        this.usersList = new LinkedList<>();
        this.gamesList = new HashMap<>();
    }

    /**
     * Main server method accepting new connections from users.
     * @return
     */
    @Override
    protected Object doInBackground()
    {
        try
        {
            serverSocket = new ServerSocket(serverPort);
            mainPanel.getIpTextField().setText(InetAddress.getLocalHost().getHostAddress());
            while (!isCancelled())
            {
                Socket socket = serverSocket.accept();

                JSONObject connectionMsg =
                        new JSONObject(new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
                String nick = connectionMsg.getString("nick");
                String identifier = connectionMsg.getString("identifier");
                String ip = socket.getRemoteSocketAddress().toString().substring(1);

                mainPanel.writeLineInTextArea("Nowe polaczenie z ip: "
                        + ip + " nick: " + nick);

                if (clientsNumber.get() < maxUserThreads)
                {
                    acceptNewConnection(nick, identifier, ip, socket);
                }
                else
                {
                    rejectNewConnection(socket);
                }
            }
        }
        catch (IOException e)
        {
            mainPanel.writeLineInTextArea("Blad podczas pracy serwera.");
            changeBtnStatus();
        }
        return null;
    }

    /**
     * Method accepting new user connection and starting independend user listener thread.
     * @param nick
     * @param identifier
     * @param ip
     * @param socket
     */
    private void acceptNewConnection(String nick, String identifier, String ip, Socket socket)
    {
        addUserToDatabase(nick);
        RemoteUser newUser = new RemoteUser(nick, identifier, ip, socket, "Połączony", getUserRankingInd(nick));
        usersList.add(newUser);
        UserServerThread userThread = new UserServerThread(main, newUser, mainPanel);
        userThread.addPropertyChangeListener(propertyChange -> {
            if (userThread.isDone())
            {
                mainPanel.setActivePlayersNumber(clientsNumber.decrementAndGet());
                mainPanel.writeLineInTextArea("Użytkownik " + newUser.getNick() + " rozłączony.");
            }
        });
        userThreadList.add(userThread);
        userThreadExecutor.execute(userThreadList.getLast());
        mainPanel.setActivePlayersNumber(clientsNumber.incrementAndGet());
    }

    /**
     * Method adding user to database.
     * @param nick
     */
    private void addUserToDatabase(String nick)
    {
        UserDTO user = new UserDTO();
        try
        {
            user = new UserDAO(main.getEntityManagerFactory()).getUserByNickname(nick);
        }
        catch (Exception e)
        {
            user.setName(nick);
            new UserDAO(main.getEntityManagerFactory()).insert(user);
        }
    }

    /**
     * Method getting user ranking in single game.
     * @param nick
     * @return
     */
    private int getUserRankingInd(String nick)
    {
        try
        {
            return Collections.max(new UserDAO(main.getEntityManagerFactory()).getUsersInvScores(nick));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * Method getting user ranking in cooperation game
     * @param nick
     * @return
     */
    private int getUserRankingCoop(String nick)
    {
        try
        {
            return Collections.max(new UserDAO(main.getEntityManagerFactory()).getUsersCoopScores(nick));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * Method getting user ranking in cooperation game.
     * @param nick
     * @return
     */
    private int getUserRankingConc(String nick)
    {
        try
        {
            return Collections.max(new UserDAO(main.getEntityManagerFactory()).getUsersConcScores(nick));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * Method rejecting new user connection if too many users are already connected
     * @param socket
     * @throws IOException
     */
    private void rejectNewConnection(Socket socket) throws IOException
    {
        JSONObject rejectedObj = new JSONObject();
        rejectedObj.put("state", "rejected");
        new PrintWriter(socket.getOutputStream(), true).println(rejectedObj.toString());
        mainPanel.writeLineInTextArea(
                "Polaczenie odrzucone, przekroczona maksymalna ilosc klientow: " + maxUserThreads);
    }

    /**
     * Method changin button status to default.
     */
    private void changeBtnStatus()
    {
        mainPanel.setStartBtnStatus(true);
        mainPanel.setStopBtnStatus(false);
        mainPanel.setConnectionsBtnStatus(false);
        mainPanel.getPortTextField().setEditable(true);
    }

    /**
     * Method executed after closing server thread, closing all games and user threads.
     */
    @Override
    protected void done()
    {
        try
        {
            userThreadList.forEach(userThread -> userThread.cancel(true));
            gamesList.entrySet().forEach(gameThread -> gameThread.getValue().cancel(true));

            serverSocket.close();
        }
        catch (IOException e)
        {
            mainPanel.writeLineInTextArea("Blad podczas konczenia pracy serwera.");
        }
    }

    public LinkedList<RemoteUser> getUsersList()
    {
        return usersList;
    }

    public HashMap<String, ParentGameEngine> getGamesList()
    {
        return gamesList;
    }

    /**
     * Method adding new game to games list
     * @param game
     */
    public void addNewGame(ParentGameEngine game)
    {
        gamesList.put(game.getIdentifier(), game);
    }
}

