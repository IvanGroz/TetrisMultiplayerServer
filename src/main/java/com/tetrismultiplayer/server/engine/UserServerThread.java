package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.engine.game.ConcurrentGame;
import main.java.com.tetrismultiplayer.server.engine.game.CooperationGame;
import main.java.com.tetrismultiplayer.server.engine.game.ParentGameEngine;
import main.java.com.tetrismultiplayer.server.engine.game.SingleGame;
import main.java.com.tetrismultiplayer.server.engine.user.Move;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.engine.user.UserMove;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class UserServerThread extends SwingWorker<Boolean, Object>
{
    private RemoteUser user;
    private ParentGameEngine game;
    private MainPanel mainPanel;
    private Main main;

    public UserServerThread(Main main, RemoteUser user, MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        this.main = main;
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
                    startNewGame(newMsg);
                    break;
                case "move":
                    forwardMove(newMsg);
                    break;
                case "getRanking":
                    sendRanking();
                    break;
                case "getWaitingGames":
                    sendWaitingGames();
                    break;
                case "connectToGame":
                    connectToGame(newMsg);
                    break;
            }
        }
        return true;
    }

    private void connectToGame(JSONObject newMsg)
    {
        ParentGameEngine newGame = main.getMainServerThread().getGamesList().get(newMsg.getString("identifier"));
        this.game = newGame;
        newGame.addUser(user);
    }

    private void sendWaitingGames()
    {
        JSONObject gamesWaiting = new JSONObject().put("cmd", "setGamesList");
        if (main.getMainServerThread().getGamesList().isEmpty())
        {
            gamesWaiting.put("isEmpty", "true");
        }
        else
        {
            gamesWaiting.put("isEmpty", "false");
            JSONArray gamesList = new JSONArray();
            main.getMainServerThread().getGamesList().entrySet().stream().map(Map.Entry::getValue)
                    .filter(g -> g.getGameStatus().toString()
                            .equals(ParentGameEngine.GameStatus.WAITING.toString())).forEach(game -> {
                JSONObject thisGame = new JSONObject().put("owner", game.getOwnerUser().getIdentifier())
                        .put("type", game.getGameType().toString())
                        .put("playersNumber", game.getPlayersNumber());
                JSONArray users = new JSONArray();
                game.getUsersList().forEach(user -> {
                    users.put(new JSONObject().put("nick", user.getNick()).put("identifier", user.getIdentifier())
                            .put("ip", user.getIp()).put("ranking", user.getRanking()));
                });
                thisGame.put("users", users);
                gamesList.put(thisGame);
            });
            gamesWaiting.put("gamesList", gamesList);
        }
        user.sendToUser(gamesWaiting);
    }

    private void sendRanking()
    {

    }

    private void startNewGame(JSONObject newMsg)
    {
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
        switch (newMsg.getString("gameType"))
        {
            case "single":
                user.sendToUser(new JSONObject().put("cmd", "gameStarted").put("type", "single"));
                game = new SingleGame(user, mainPanel, gameSpeed);
                break;
            case "concurrent":
                game = new ConcurrentGame(user, mainPanel, gameSpeed, newMsg.getInt("pNumber"));
                break;
            case "cooperation":
                game = new CooperationGame(user, mainPanel, gameSpeed, newMsg.getInt("pNumber"));
                break;
        }
        main.getMainServerThread().addNewGame(game);
        game.addPropertyChangeListener(propertyChange -> {
            if (game.isDone()) main.getMainServerThread().getGamesList().remove(game);
        });
        game.execute();
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
