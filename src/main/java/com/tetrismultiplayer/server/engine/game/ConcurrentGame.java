package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

/**
 * Created by Marcin on 2016-02-16.
 */
public class ConcurrentGame extends ParentGameEngine
{
    public ConcurrentGame(RemoteUser ownerUser, MainPanel mainPanel, GameSpeed gameSpeed, int playersNumber)
    {
        super(GameStatus.WAITING, ownerUser, mainPanel, gameSpeed, GameType.CONCURRENT, playersNumber);
    }

    public Object doInBackground() throws InterruptedException
    {
        mainPanel.writeLineInTextArea("Nowa gra w trybie konkurencji utworzona przez uzytkownika "
                + usersList.getFirst().getNick());
        if (waitForUsers())
        {
            mainPanel.writeLineInTextArea("Gra w trybie konkurencji id: " + getIdentifier() + " zostala rozpoczeta");

            long startFrameTime = System.currentTimeMillis();
            usersList.forEach(user -> placeNewTetromino(user));
            while (true)
            {
                checkPlayersMove(true);
                for (int i = 0; i < usersList.size(); i++)
                {
                    clearLine(checkForLineToClear(i), i);
                }
                for (int i = 0; i < usersList.size(); i++)
                {
                    RemoteUser user = usersList.get(i);
                    if (checkForInactiveBlock(user))
                    {
                        if (!placeNewTetromino(user))
                        {
                            user.sendToUser(new JSONObject().put("cmd", "endGame"));
                            usersList.remove(i);
                            i--;
                            if (i <= 0) return null;
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
