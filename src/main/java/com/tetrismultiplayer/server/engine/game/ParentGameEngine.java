package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.user.Move;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.engine.user.UserMove;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    protected int playersNumber;

    public ParentGameEngine(MainPanel mainPanel, GameSpeed gameSpeed, GameType gameType, int playersNumber)
    {
        this.mainPanel = mainPanel;
        this.moveQueue = new ConcurrentLinkedQueue<>();
        this.allTetrominos = new LinkedList<>();
        this.usersList = new LinkedList<>();
        this.frameInterval = getFrameInterval(gameSpeed);
        this.gameType = gameType;
        this.columnNumber = getColumnNumber(gameType);
        this.rowNumber = 20;
        this.playersNumber = playersNumber;
    }

    private int getColumnNumber(GameType gameType)
    {
        if (gameType == GameType.CONCURRENT || gameType == GameType.COOPERATION) return 10 * playersNumber;
        else return 10;
    }

    private long getFrameInterval(GameSpeed gameSpeed)
    {
        if (gameSpeed.equals(GameSpeed.SLOW)) return 1500;
        else if (gameSpeed.equals(GameSpeed.NORMAL)) return 1000;
        else return 500;
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

    protected void checkPlayersMove()
    {
        if (!moveQueue.isEmpty())
        {
            UserMove newMove = moveQueue.poll();
            Tetromino previousTetromino = newMove.getUser().getActiveTetromino();
            Tetromino tetrominoCopy = Tetromino.getTetrominoCopy(previousTetromino);
            if (newMove.getMove().toString().equals("drop"))
            {
                System.out.println("drop the bass");
                //thisUser.sendToUser(new JSONObject().put("cmd", "move").put("identifier", thisUser.getIdentifier()).put("key", newMove.getMove()).put("amount", 3));
            }
            else
            {
                if (newMove.getMove() == (Move.DOWN)) tetrominoCopy.moveDown();
                else if (newMove.getMove() == (Move.LEFT)) tetrominoCopy.moveLeft();
                else if (newMove.getMove() == (Move.RIGHT)) tetrominoCopy.moveRight();
                else if (newMove.getMove() == (Move.ROTATE)) tetrominoCopy.rotate();
                if (!isCollision(tetrominoCopy, previousTetromino))
                {
                    if (newMove.getMove().equals(Move.DOWN)) previousTetromino.moveDown();
                    else if (newMove.getMove().equals(Move.LEFT)) previousTetromino.moveLeft();
                    else if (newMove.getMove().equals(Move.RIGHT)) previousTetromino.moveRight();
                    else if (newMove.getMove().equals(Move.ROTATE)) previousTetromino.rotate();

                    newMove.getUser().sendToUser(new JSONObject().put("cmd", "move")
                            .put("identifier", newMove.getUser().getIdentifier()).put("key", newMove.getMove()));
                }
            }
        }
    }

    protected boolean isCollision(Tetromino tetromino, Tetromino previousTetromino)
    {
        for (Brick brick : tetromino.getBricksList())
        {
            Point brickPosition = brick.getPosition();
            if (brickPosition.x < 0 || brickPosition.x >= getColumnNumber() || brickPosition.y >= getRowNumber())
            {
                return true;
            }
            for (Tetromino existingTetromino : allTetrominos)
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
            if (!isCollision(movedTetromino, activeTetromino))
            {
                activeTetromino.moveDown();
                user.sendToUser(new JSONObject().put("cmd", "move")
                        .put("identifier", user.getIdentifier()).put("key", Move.DOWN.toString()));
            }
        });
    }
}
