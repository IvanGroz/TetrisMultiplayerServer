package main.java.com.tetrismultiplayer.server.engine.game;

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

    public GameEngine(MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        this.moveQueue = new ConcurrentLinkedQueue<>();
        this.usersList = new LinkedList<>();
    }

    public void registerMove(UserMove move)
    {
        moveQueue.add(move);
    }
}
