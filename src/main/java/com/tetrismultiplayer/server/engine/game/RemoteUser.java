package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
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
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private LinkedList<Tetromino> tetrominos;
    private Tetromino activeTetromino;

    public RemoteUser(String nick, String identifier, Socket socket)
    {
        this.nick = nick;
        this.identifier = identifier;
        this.tetrominos = new LinkedList<>();
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
        System.out.println("wyslano: " + json);
        out.println(json);
    }

    public JSONObject readJSON()
    {
        try
        {
            JSONObject newObject = new JSONObject(in.readLine());
            System.out.println("odebrano: " + newObject);
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
            out.println(new JSONObject().put("cmd", "end"));
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

    public String getNick()
    {
        return nick;
    }

    public String getIdentifier()
    {
        return identifier;
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
}
