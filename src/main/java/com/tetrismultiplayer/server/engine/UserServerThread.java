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
        out.println(new JSONObject().put("state","connected"));
        while(isCancelled())
        {

        }
        return true;
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
