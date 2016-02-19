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
        super(GameStatus.WAITING, ownerUser, mainPanel, gameSpeed, GameType.COOPERATION, playersNumber);
    }

    public Object doInBackground() throws InterruptedException
    {
        mainPanel.writeLineInTextArea("Nowa gra wspolna utworzona przez uzytkownika "
                + usersList.getFirst().getNick());
        if (waitForUsers())
        {
            mainPanel.writeLineInTextArea("Gra wspolna id:" + getIdentifier() + "rozpoczeta"
                    + usersList.getFirst().getNick());

            long startFrameTime = System.currentTimeMillis();
            usersList.forEach(user -> placeNewTetromino(user));
            while (true)
            {
                checkPlayersMove();

                clearLine(checkForLineToClear());
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
