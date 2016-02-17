package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.engine.game.*;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import javax.swing.*;

public class UserServerThread extends SwingWorker<Boolean, Object>
{
    private RemoteUser user;
    private GameEngine game;
    private MainPanel mainPanel;

    public UserServerThread(RemoteUser user, MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        this.user = user;
    }

    @Override
    protected Boolean doInBackground()
    {
        user.sendToUser(new JSONObject().put("state", "connected"));
        JSONObject newMsg;
        while (!(newMsg = user.readJSON()).getString("cmd").equals("end"))
        {
            switch (newMsg.getString("cmd"))
            {
                case "newGame":
                    if (newMsg.getString("gameType").equals("single"))
                    {
                        user.sendToUser(new JSONObject().put("cmd", "gameStarted").put("type", "single"));
                        game = new SingleGame(user, mainPanel);
                        game.execute();
                    }
                    break;
                case "move":
                    forwardMove(newMsg);
                    break;
            }
        }
        return true;
    }

    private void forwardMove(JSONObject newMsg)
    {
        String key = newMsg.getString("key");
        System.out.println(newMsg);
        if (key.equals(Move.LEFT))
        {
            game.registerMove(new UserMove(user, Move.LEFT));
        }
        else if (key.equals(Move.RIGHT))
        {
            game.registerMove(new UserMove(user, Move.RIGHT));
        }
        else if (key.equals(Move.DOWN))
        {
            game.registerMove(new UserMove(user, Move.DOWN));
        }
        else if (key.equals(Move.DROP))
        {
            game.registerMove(new UserMove(user, Move.DROP));
        }
        else if (key.equals(Move.ROTATE))
        {
            game.registerMove(new UserMove(user, Move.ROTATE));
        }
    }

    @Override
    protected void done()
    {
        user.closeConnection();
    }
}
