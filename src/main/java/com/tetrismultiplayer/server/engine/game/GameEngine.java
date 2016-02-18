package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Marcin on 2016-02-16.
 */
public abstract class GameEngine extends SwingWorker<Object, Object>
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

    public GameEngine(MainPanel mainPanel, GameSpeed gameSpeed, GameType gameType, int playersNumber)
    {
        this.mainPanel = mainPanel;
        this.moveQueue = new ConcurrentLinkedQueue<>();
        this.allTetrominos = new LinkedList<>();
        this.usersList = new LinkedList<>();
        this.frameInterval = getFrameInterval(gameSpeed);
        this.columnNumber = 20;
        this.gameType = gameType;
        this.rowNumber = getRowNumber(gameType);
        this.playersNumber = playersNumber;
    }

    private int getRowNumber(GameType gameType)
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
}
