package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Created by Marcin on 2016-02-16.
 */
public class CooperationGame extends GameEngine
{
    public CooperationGame(MainPanel mainPanel, GameSpeed gameSpeed, int playersNumber)
    {
        super(mainPanel, gameSpeed, GameType.COOPERATION, playersNumber);
    }

    public Object doInBackground()
    {
        return null;
    }
}
