package main.java.com.tetrismultiplayer.server.engine.user;

import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by Marcin on 2016-02-16.
 */
public class RemoteUser
{
    private String nick;
    private String identifier;
    private String ip;
    private String status;

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private LinkedList<Tetromino> tetrominos;
    private Tetromino activeTetromino;
    private TetrominoFactory tetrominoFactory;
    private Integer ranking;
    private Integer score;

    public RemoteUser(String nick, String identifier, String ip, Socket socket, String status, Integer ranking)
    {
        this.nick = nick;
        this.identifier = identifier;
        this.ip = ip;
        this.status = status;
        this.ranking = ranking;//TODO: dorobic wczytywanie rankingu
        this.tetrominos = new LinkedList<>();
        this.score = 0;
        try
        {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendToUser(JSONObject json)
    {
        System.out.println("wyslano: " + getIdentifier() + " " + json);
        out.println(json);
    }

    public JSONObject readJSON()
    {
        try
        {
            JSONObject newObject = new JSONObject(in.readLine());
            System.out.println("odebrano: " + getIdentifier() + " " + newObject);
            return newObject;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection()
    {
        try
        {
            sendToUser(new JSONObject().put("cmd", "end"));
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Tetromino getActiveTetromino()
    {
        return activeTetromino;
    }

    public void removeTetromino(Tetromino tetromino)
    {
        tetrominos.remove(tetromino);
    }

    public String getNick()
    {
        return nick;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getIp()
    {
        return ip;
    }

    public LinkedList<Tetromino> getTetrominos()
    {
        return tetrominos;
    }

    public void addTetromino(Tetromino newTetromino)
    {
        activeTetromino = newTetromino;
        tetrominos.add(newTetromino);
    }

    public String getStatus()
    {
        return status;
    }

    public void setTetrominoFactory(TetrominoFactory tetrominoFactory)
    {
        this.tetrominoFactory = tetrominoFactory;
    }

    public Tetromino getNewTetromino()
    {
        return tetrominoFactory.getNewTetromino();
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getRanking()
    {
        return ranking;
    }

    public void clearScore()
    {
        score = 0;
    }

    public void setRanking(Integer ranking)
    {
        this.ranking = ranking;
    }

    public Integer getScore()
    {
        return score;
    }

    public void addScore(Integer ranking)
    {
        this.score += score;
        this.ranking += score;
    }
}
