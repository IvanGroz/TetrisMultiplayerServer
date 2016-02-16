package main.java.com.tetrismultiplayer.server.engine;

import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserServerThread extends SwingWorker<Boolean, Object>
{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public UserServerThread(Socket socket)
    {
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

    @Override
    protected Boolean doInBackground()
    {
        out.println(new JSONObject().put("state", "connected"));
        JSONObject newMsg;
        while (!(newMsg = readJSON()).getString("cmd").equals("end"))
        {
            System.out.println(newMsg);
        }
        return true;
    }

    private JSONObject readJSON()
    {
        try
        {
            return new JSONObject(in.readLine());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void done()
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
}
