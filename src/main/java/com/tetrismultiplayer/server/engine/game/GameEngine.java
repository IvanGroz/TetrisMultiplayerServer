package main.java.com.tetrismultiplayer.server.engine.game;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Marcin on 2016-02-16.
 */
public abstract class GameEngine extends SwingWorker<Object, Object>
{
    protected BlockingQueue<UserMove> moveQueue;
    protected LinkedList<RemoteUser> usersList;


}
