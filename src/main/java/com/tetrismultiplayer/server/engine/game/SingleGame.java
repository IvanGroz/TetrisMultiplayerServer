package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Class and thread for single game.
 */
public class SingleGame extends ParentGameEngine
{
    public SingleGame(RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed)
    {
        super(GameStatus.RUNNING, ownerUser, mainPanel, gameSpeed, GameType.SINGLE, 1);
    }

    /**
     * Main single game thread for single game. Maintains all users connected to game.
     * @return null
     * @throws InterruptedException
     */
    public Object doInBackground()
    {
        mainPanel.writeLineInTextArea("Nowa gra pojedyncza rozpoczeta przez uzytkownika "
                + usersList.getFirst().getNick());

        long startFrameTime = System.currentTimeMillis();
        placeNewTetromino(ownerUser);
        while (true)
        {
            checkPlayersMove(false);
            clearLine(checkForLineToClear(-1),-1);
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
