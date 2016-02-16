package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.engine.game.RemoteUser;
import org.json.JSONObject;

import javax.swing.*;

public class UserServerThread extends SwingWorker<Boolean, Object>
{
    private RemoteUser user;

    public UserServerThread(RemoteUser user)
    {
        this.user = user;
    }

    @Override
    protected Boolean doInBackground()
    {
        user.sendToUser(new JSONObject().put("state", "connected"));
        JSONObject newMsg;
        while (!(newMsg = user.readJSON()).getString("cmd").equals("end"))
        {

            System.out.println(newMsg);
        }
        return true;
    }

    @Override
    protected void done()
    {
        user.closeConnection();
    }
}
