package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Created by Marcin on 2016-02-16.
 */
public class SingleGame extends ParentGameEngine
{
    public SingleGame(RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed)
    {
        super(GameStatus.RUNNING, ownerUser, mainPanel, gameSpeed, GameType.SINGLE, 1);
    }

    public Object doInBackground()
    {
        mainPanel.writeLineInTextArea("Nowa gra pojedyncza rozpoczeta przez uzytkownika "
                + usersList.getFirst().getNick());

        long startFrameTime = System.currentTimeMillis();
        placeNewTetromino(ownerUser);
        while (true)
        {
            checkPlayersMove();
            clearLine(checkForLineToClear());
            if (checkForInactiveBlock(ownerUser))
            {
                if (!placeNewTetromino(ownerUser))
                {
                    return null;
                }
            }

            if (System.currentTimeMillis() - startFrameTime >= getFrameInterval())
            {
                moveDownAllActiveBlocks();
                startFrameTime = System.currentTimeMillis();
            }
        }
    }
}
