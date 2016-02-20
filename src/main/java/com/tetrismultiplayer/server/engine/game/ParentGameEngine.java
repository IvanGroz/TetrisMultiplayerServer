package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import main.java.com.tetrismultiplayer.server.engine.user.Move;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.engine.user.UserMove;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Created by Marcin on 2016-02-16.
 */
public abstract class ParentGameEngine extends SwingWorker<Object, Object>
{
    protected MainPanel mainPanel;
    protected ConcurrentLinkedQueue<UserMove> moveQueue;
    protected LinkedList<RemoteUser> usersList;
    protected LinkedList<Tetromino> allTetrominos;
    private long frameInterval;
    private int rowNumber;
    private int columnNumber;
    private GameType gameType;
    private GameStatus gameStatus;
    protected int playersNumber;
    protected RemoteUser ownerUser;
    protected String identifier;

    public ParentGameEngine(GameStatus gameStatus, RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed, GameType gameType, int playersNumber)
    {
        this.gameStatus = gameStatus;
        this.mainPanel = mainPanel;
        this.moveQueue = new ConcurrentLinkedQueue<>();
        this.allTetrominos = new LinkedList<>();
        this.usersList = new LinkedList<>();
        this.frameInterval = getFrameInterval(gameSpeed);
        this.gameType = gameType;
        this.columnNumber = 10 * playersNumber;
        this.rowNumber = 20;
        this.playersNumber = playersNumber;
        this.ownerUser = ownerUser;
        this.identifier = ownerUser.getIdentifier();
        addUser(ownerUser);
    }

    private long getFrameInterval(GameSpeed gameSpeed)
    {
        if (gameSpeed.equals(GameSpeed.SLOW)) return 1500;
        else if (gameSpeed.equals(GameSpeed.NORMAL)) return 1000;
        else return 500;
    }

    public enum GameStatus
    {
        WAITING, RUNNING
    }

    public enum GameType
    {
        SINGLE, CONCURRENT, COOPERATION
    }

    public enum GameSpeed
    {
        SLOW, NORMAL, FAST
    }

    public void registerMove(UserMove move)
    {
        moveQueue.add(move);
    }

    protected long getFrameInterval()
    {
        return frameInterval;
    }

    protected int getRowNumber()
    {
        return rowNumber;
    }

    protected int getColumnNumber()
    {
        return columnNumber;
    }

    protected void checkPlayersMove(boolean isConcurrent)
    {
        if (!moveQueue.isEmpty())
        {
            UserMove newMove = moveQueue.poll();
            Tetromino previousTetromino = newMove.getUser().getActiveTetromino();
            Tetromino tetrominoCopy = Tetromino.getTetrominoCopy(previousTetromino);
            if (newMove.getMove().toString().equals("drop"))
            {
                Integer dropAmount = 0;
                tetrominoCopy.moveDown();
                while (!isCollision(tetrominoCopy, previousTetromino, true))
                {
                    previousTetromino.moveDown();
                    tetrominoCopy.moveDown();
                    dropAmount++;
                }
                if (dropAmount > 0)
                {
                    for (RemoteUser user : usersList)
                    {
                        user.sendToUser(new JSONObject().put("cmd", "move")
                                .put("identifier", newMove.getUser().getIdentifier())
                                .put("key", newMove.getMove()).put("amount", dropAmount));
                    }
                }
            }
            else
            {
                if (newMove.getMove() == (Move.DOWN)) tetrominoCopy.moveDown();
                else if (newMove.getMove() == (Move.LEFT)) tetrominoCopy.moveLeft();
                else if (newMove.getMove() == (Move.RIGHT)) tetrominoCopy.moveRight();
                else if (newMove.getMove() == (Move.ROTATE)) tetrominoCopy.rotate();
                if (!isCollision(tetrominoCopy, previousTetromino, true)
                        && (!isConcurrent || previousTetromino.getPosition().y > 2))
                {
                    if (newMove.getMove().equals(Move.DOWN)) previousTetromino.moveDown();
                    else if (newMove.getMove().equals(Move.LEFT)) previousTetromino.moveLeft();
                    else if (newMove.getMove().equals(Move.RIGHT)) previousTetromino.moveRight();
                    else if (newMove.getMove().equals(Move.ROTATE)) previousTetromino.rotate();

                    usersList.forEach(user -> user.sendToUser(new JSONObject().put("cmd", "move")
                            .put("identifier", newMove.getUser().getIdentifier()).put("key", newMove.getMove())));
                }
            }
        }
    }

    protected boolean isCollision(Tetromino tetromino, Tetromino previousTetromino, boolean isUserMove)
    {
        for (Brick brick : tetromino.getBricksList())
        {
            Point brickPosition = brick.getPosition();
            if (brickPosition.x < 0 || brickPosition.x >= getColumnNumber() || brickPosition.y >= getRowNumber())
            {
                return true;
            }
            LinkedList<Tetromino> allTetrominosCopy = (LinkedList<Tetromino>) allTetrominos.clone();
            if (!isUserMove) usersList.forEach(user -> allTetrominosCopy.remove(user.getActiveTetromino()));
            for (Tetromino existingTetromino : allTetrominosCopy)
            {
                if (existingTetromino.getPosition() != previousTetromino.getPosition())
                {
                    for (Brick existingBrick : existingTetromino.getBricksList())
                    {
                        if (brickPosition.equals(existingBrick.getPosition()))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void moveDownAllActiveBlocks()
    {
        usersList.forEach(user -> {
            Tetromino activeTetromino = user.getActiveTetromino();
            Tetromino movedTetromino = Tetromino.getTetrominoCopy(activeTetromino);
            movedTetromino.moveDown();
            if (!isCollision(movedTetromino, activeTetromino, false))
            {
                activeTetromino.moveDown();
                usersList.forEach(user2 -> user2.sendToUser(new JSONObject().put("cmd", "move")
                        .put("identifier", user.getIdentifier()).put("key", Move.DOWN.toString())));
            }
        });
    }

    protected boolean placeNewTetromino(RemoteUser user)
    {
        Tetromino newTetromino = user.getNewTetromino();

        for (Brick newBrick : newTetromino.getBricksList())
        {
            for (Tetromino userTetromino : allTetrominos)
            {
                for (Brick brick : userTetromino.getBricksList())
                {
                    if (brick.getPosition().equals(newBrick.getPosition())) return false;
                }
            }
        }

        user.addTetromino(newTetromino);
        allTetrominos.add(newTetromino);
        usersList.forEach(u -> u.sendToUser(newTetromino.toJSON()
                .put("identifier", user.getIdentifier()).put("cmd", "newTetromino")));
        return true;
    }

    protected boolean checkForInactiveBlock(RemoteUser remoteUser)
    {
        LinkedList<Tetromino> allTetrominosCopy = (LinkedList<Tetromino>) allTetrominos.clone();
        usersList.forEach(user -> allTetrominosCopy.remove(user.getActiveTetromino()));
        Tetromino activeTetromino = remoteUser.getActiveTetromino();

        for (Brick activeTetrominoBrick : activeTetromino.getBricksList())
        {
            Point activeBrickPosition = activeTetrominoBrick.getPosition();
            if (activeBrickPosition.y == getRowNumber() - 1) return true;
            for (Tetromino tetromino : allTetrominosCopy)
            {
                for (Brick tetrominoBrick : tetromino.getBricksList())
                {
                    Point brickPosition = tetrominoBrick.getPosition();
                    if (activeBrickPosition.y == brickPosition.y - 1
                            && activeBrickPosition.x == brickPosition.x) return true;
                }
            }
        }
        return false;
    }

    public void addUser(RemoteUser user)
    {
        if (usersList.size() < playersNumber)
        {
            usersList.add(user);
            if (gameType.equals(GameType.SINGLE))
            {
                user.setTetrominoFactory(new TetrominoFactory());
                user.setStatus("Gra pojedyncza");
            }
            else
            {
                if (gameType.equals(GameType.CONCURRENT)) user.setStatus("Gra wspólna");
                else user.setStatus("Współzawodnictwo");

                user.setTetrominoFactory(new TetrominoFactory(usersList.size()));
            }
        }
    }

    protected boolean waitForUsers()
    {
        int timeoutCounter = 59;
        long startTime = System.currentTimeMillis();
        while (usersList.size() < playersNumber && timeoutCounter > 0)
        {
            if (System.currentTimeMillis() - startTime >= 1000)
            {
                for (RemoteUser user : usersList)
                {
                    user.sendToUser(new JSONObject().put("cmd", "waiting").put("time", timeoutCounter));
                }
                startTime = System.currentTimeMillis();
                timeoutCounter--;
            }
        }
        if (usersList.size() == playersNumber)
        {
            JSONObject helloMsg = new JSONObject();
            JSONArray players = new JSONArray();

            usersList.forEach(user -> players.put(new JSONObject().put("nick", user.getNick())
                    .put("identifier", user.getIdentifier()).put("ip", user.getIp()).put("ranking", user.getRanking())));

            helloMsg.put("cmd", "gameStarted").put("type", gameType.toString().toLowerCase())
                    .put("playersNumber", playersNumber).put("players", players);
            usersList.forEach(user -> user.sendToUser(helloMsg));
            setGameStatus(GameStatus.RUNNING);
            return true;
        }
        else
        {
            usersList.forEach(user -> user.sendToUser(new JSONObject().put("cmd", "timeout")));
            return false;
        }
    }

    protected void done()
    {
        usersList.forEach(user -> user.sendToUser(new JSONObject().put("cmd", "endGame")));
    }

    public GameStatus getGameStatus()
    {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus)
    {
        this.gameStatus = gameStatus;
    }

    public RemoteUser getOwnerUser()
    {
        return ownerUser;
    }

    public LinkedList<RemoteUser> getUsersList()
    {
        return usersList;
    }

    public GameType getGameType()
    {
        return gameType;
    }

    public int getPlayersNumber()
    {
        return playersNumber;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    protected LinkedList<Integer> checkForLineToClear(Integer position)
    {
        int minColumn;
        int maxColumn;
        if (position == -1)
        {
            minColumn = 0;
            maxColumn = getColumnNumber();
        }
        else
        {
            minColumn = position * 10;
            maxColumn = (position + 1) * 10;
        }
        LinkedList<Integer> linesToDelete = new LinkedList<>();
        LinkedList<Brick> allBricks = new LinkedList<>();
        LinkedList<Tetromino> allTetrominosCopy = (LinkedList<Tetromino>) allTetrominos.clone();
        allTetrominosCopy = allTetrominosCopy.stream()
                .filter(f -> (f.getPosition().x >= minColumn && f.getPosition().x <= maxColumn))
                .collect(Collectors.toCollection(LinkedList<Tetromino>::new));
        for (RemoteUser user : usersList)
        {
            allTetrominosCopy.remove(user.getActiveTetromino());
        }

        for (Tetromino tetromino : allTetrominosCopy)
        {
            for (Brick brick : tetromino.getBricksList())
            {
                allBricks.add(brick);
            }
        }

        allBricks.sort((x, y) -> (Integer.valueOf(x.getPosition().y).compareTo(Integer.valueOf(y.getPosition().y))));
        int counter = 1;
        int i;
        for (i = 0; i < allBricks.size() - 1; i++)
        {
            if (allBricks.get(i).getPosition().y == allBricks.get(i + 1).getPosition().y)
            {
                counter++;
                if (counter == (position == -1 ? getColumnNumber() : 10))
                {
                    linesToDelete.add(allBricks.get(i).getPosition().y);
                    counter = 1;
                }
            }
            else
            {
                counter = 1;
            }
        }
        return linesToDelete;
    }

    protected void clearLine(LinkedList<Integer> linesToClear, Integer position)
    {
        int minColumn;
        int maxColumn;
        if (position == -1)
        {
            minColumn = 0;
            maxColumn = getColumnNumber();
        }
        else
        {
            minColumn = position * 10;
            maxColumn = (position + 1) * 10;
        }
        linesToClear.forEach(line ->
        {
            usersList.forEach(user -> {
                LinkedList<Tetromino> tetrominos = user.getTetrominos();
                for (int i = 0; i < tetrominos.size(); i++)
                {
                    if (tetrominos.get(i).getPosition().x >= minColumn && tetrominos.get(i).getPosition().x <= maxColumn)
                    {
                        LinkedList<Brick> bricks = tetrominos.get(i).getBricksList();
                        for (int j = 0; j < bricks.size(); j++)
                        {
                            if (bricks.get(j).getPosition().y == line)
                            {
                                tetrominos.get(i).removeBrick(bricks.get(j));
                                j--;
                                if (tetrominos.get(i).getBricksList().isEmpty())
                                {
                                    user.removeTetromino(tetrominos.get(i));
                                    i--;
                                }
                            }
                        }
                    }
                }
            });

            usersList.forEach(user -> {
                user.getTetrominos().forEach(tetromino -> {
                    if (tetromino.getPosition().x >= minColumn && tetromino.getPosition().x <= maxColumn)
                    {
                        LinkedList<Brick> bricks = tetromino.getBricksList();
                        bricks.sort((brick1, brick2) -> Integer.valueOf(brick1.getPosition().y).compareTo(brick2.getPosition().y));
                        if (bricks.getLast().getPosition().y < line)
                        {
                            tetromino.moveDown();
                        }
                    }
                });
            });
            usersList.forEach(user -> user.sendToUser(new JSONObject()
                    .put("cmd", "clearLine").put("position", position).put("row", line)));
        });
        if (!linesToClear.isEmpty())
        {
            System.out.println("usunieto " + linesToClear.size() + " linii z pozycji " + position);
        }
    }
}
