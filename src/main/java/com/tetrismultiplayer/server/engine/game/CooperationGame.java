package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Created by Marcin on 2016-02-16.
 */
public class CooperationGame extends ParentGameEngine
{
    public CooperationGame(RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed, int playersNumber)
    {
        super(GameStatus.WAITING,ownerUser, mainPanel, gameSpeed, GameType.COOPERATION, playersNumber);
    }

    public Object doInBackground()
    {
        if (waitForUsers())
        {

        }
        return null;
    }
}
