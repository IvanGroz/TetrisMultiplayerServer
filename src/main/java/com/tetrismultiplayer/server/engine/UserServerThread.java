package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.engine.game.*;
import main.java.com.tetrismultiplayer.server.engine.user.Move;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.engine.user.UserMove;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import javax.swing.*;

public class UserServerThread extends SwingWorker<Boolean, Object>
{
    private RemoteUser user;
    private ParentGameEngine game;
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
                    ParentGameEngine.GameSpeed gameSpeed = ParentGameEngine.GameSpeed.NORMAL;
                    switch (newMsg.getString("difficultyLvl"))
                    {
                        case "easy":
                            gameSpeed = ParentGameEngine.GameSpeed.SLOW;
                            break;
                        case "normal":
                            gameSpeed = ParentGameEngine.GameSpeed.NORMAL;
                            break;
                        case "hard":
                            gameSpeed = ParentGameEngine.GameSpeed.FAST;
                            break;
                    }
                    if (newMsg.getString("gameType").equals("single"))
                    {
                        user.sendToUser(new JSONObject().put("cmd", "gameStarted").put("type", "single"));
                        game = new SingleGame(user, mainPanel, gameSpeed);
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
        if (key.equals(Move.LEFT.toString()))
        {
            game.registerMove(new UserMove(user, Move.LEFT));
        }
        else if (key.equals(Move.RIGHT.toString()))
        {
            game.registerMove(new UserMove(user, Move.RIGHT));
        }
        else if (key.equals(Move.DOWN.toString()))
        {
            game.registerMove(new UserMove(user, Move.DOWN));
        }
        else if (key.equals(Move.DROP.toString()))
        {
            game.registerMove(new UserMove(user, Move.DROP));
        }
        else if (key.equals(Move.ROTATE.toString()))
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
