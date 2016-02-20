package main.java.com.tetrismultiplayer.server.engine;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.database.dao.GameDAO;
import main.java.com.tetrismultiplayer.server.database.dao.ScoreDAO;
import main.java.com.tetrismultiplayer.server.database.dto.GameDTO;
import main.java.com.tetrismultiplayer.server.database.dto.ScoreDTO;
import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;
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
import java.util.LinkedList;
import java.util.Map;

/**
 * Class listening to user messages and performing selected work.
 */
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

    /**
     * Main running method receiving messages from user and performing selected job.
     * @return
     */
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

    /**
     * Method connecting user to selected game
     * @param newMsg
     */
    private void connectToGame(JSONObject newMsg)
    {
        ParentGameEngine newGame = main.getMainServerThread().getGamesList().get(newMsg.getString("identifier"));
        this.game = newGame;
        newGame.addUser(user);
    }

    /**
     * Method sending to user list of waiting multiplayer games.
     */
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

    /**
     * Method starting new selected by user game.
     * @param newMsg
     */
    private void startNewGame(JSONObject newMsg)
    {
        if (main.getMainServerThread().getGamesList().size() < main.maxGames)
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
                if (game.isDone())
                {
                    addGameToDb(game);
                    game.getUsersList().forEach(user -> user.getTetrominos().clear());
                    main.getMainServerThread().getGamesList().remove(game.getIdentifier());
                    mainPanel.setActiveGamesNumber(main.getMainServerThread().getGamesList().size());
                }
            });
            mainPanel.setActiveGamesNumber(main.getMainServerThread().getGamesList().size());
            game.execute();
        }
        else
        {
            JSONObject fullGames = new JSONObject().put("cmd", "isFullGames");
            user.sendToUser(fullGames);
            mainPanel.writeLineInTextArea(
                    "Polecenie rozpoczęcia gry odrzucone, przekroczona maksymalna ilosc gier: " + main.maxGames);
        }
    }

    /**
     * Method adding game to database after game is finished.
     * @param game
     */
    private void addGameToDb(ParentGameEngine game)
    {
        GameDTO newGameInDb = new GameDTO();
        Long date = System.currentTimeMillis();
        newGameInDb.setGameDate(date);
        newGameInDb.setGameType(game.getGameType().name());
        LinkedList<UserDTO> users = new LinkedList<UserDTO>();

        for (int i = 0; i < game.getPlayersNumber(); i++)
        {
            UserDTO user = new UserDTO();
            user.setName(game.getUsersList().get(i).getNick());
            users.add(user);
        }
        LinkedList<ScoreDTO> scores = new LinkedList<ScoreDTO>();
        for (int i = 0; i < game.getPlayersNumber(); i++)
        {
            ScoreDTO score = new ScoreDTO();
            score.setScore(game.getUsersList().get(i).getScore());
            scores.add(score);
        }
        newGameInDb.setScore(scores);
        new GameDAO(main.getEntityManagerFactory()).insert(newGameInDb);

        for (int i = 0; i < game.getPlayersNumber(); i++)
        {
            ScoreDTO score = new ScoreDTO();
            score.setScore(game.getUsersList().get(i).getScore());
            score.setGame(new GameDAO(main.getEntityManagerFactory()).getGameByDate(date));
            score.setPlayer(users);
            new ScoreDAO(main.getEntityManagerFactory()).insert(score);
        }
    }

    /**
     * Method forwarding move to user game move queue.
     * @param newMsg
     */
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

    /**
     * Method closing connection with user after finishing thread.
     */
    @Override
    protected void done()
    {
        user.closeConnection();
    }
}
