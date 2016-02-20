package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Class and thread for cooperation game.
 */
public class CooperationGame extends ParentGameEngine
{
    public CooperationGame(RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed, int playersNumber)
    {
        super(GameStatus.WAITING, ownerUser, mainPanel, gameSpeed, GameType.COOPERATION, playersNumber);
    }

    /**
     * Main cooperation game thread for cooperation game. Maintains all users connected to game.
     * @return null
     * @throws InterruptedException
     */
    public Object doInBackground() throws InterruptedException
    {
        mainPanel.writeLineInTextArea("Nowa gra wspolna utworzona przez uzytkownika "
                + usersList.getFirst().getNick());
        if (waitForUsers())
        {
            mainPanel.writeLineInTextArea("Gra w wspolna id: " + getIdentifier() + " zostala rozpoczeta");

            long startFrameTime = System.currentTimeMillis();
            usersList.forEach(user -> placeNewTetromino(user));
            while (true)
            {
                checkPlayersMove(false);

                clearLine(checkForLineToClear(-1), -1);
                for (RemoteUser user : usersList)
                {
                    if (checkForInactiveBlock(user))
                    {
                        if (!placeNewTetromino(user))
                        {
                            return null;
                        }
                    }
                }

                if (System.currentTimeMillis() - startFrameTime >= getFrameInterval())
                {
                    moveDownAllActiveBlocks();
                    startFrameTime = System.currentTimeMillis();
                }
            }
        }
        else
        {
            mainPanel.writeLineInTextArea("Czas oczekiwania na nowych graczy gry: "
                    + getIdentifier() + " zostal przekroczony");
        }
        return null;
    }
}
