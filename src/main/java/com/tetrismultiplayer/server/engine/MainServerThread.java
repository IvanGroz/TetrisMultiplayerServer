package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.Main;
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
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
    private LinkedList<ParentGameEngine> gamesList;

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
        this.gamesList = new LinkedList<>();
    }

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

    private void acceptNewConnection(String nick, String identifier, String ip, Socket socket)
    {
        RemoteUser newUser = new RemoteUser(nick, identifier, ip, socket, "Połączony");
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

    private void rejectNewConnection(Socket socket) throws IOException
    {
        JSONObject rejectedObj = new JSONObject();
        rejectedObj.put("state", "rejected");
        new PrintWriter(socket.getOutputStream(), true).println(rejectedObj.toString());
        mainPanel.writeLineInTextArea(
                "Polaczenie odrzucone, przekroczona maksymalna ilosc klientow: " + maxUserThreads);
    }

    private void changeBtnStatus()
    {
        mainPanel.setStartBtnStatus(true);
        mainPanel.setStopBtnStatus(false);
        mainPanel.setConnectionsBtnStatus(false);
        mainPanel.getPortTextField().setEditable(true);
    }

    @Override
    protected void done()
    {
        try
        {
            userThreadList.forEach(userThread -> userThread.cancel(true));
            gamesList.forEach(gameThread -> gameThread.cancel(true));

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

    public LinkedList<ParentGameEngine> getGamesList()
    {
        return gamesList;
    }
}

