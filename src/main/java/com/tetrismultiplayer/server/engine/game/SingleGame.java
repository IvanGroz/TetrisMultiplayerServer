package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Created by Marcin on 2016-02-16.
 */
public class SingleGame extends ParentGameEngine
{
    private RemoteUser remoteUser;

    public SingleGame(RemoteUser remoteUser, MainPanel mainPanel, GameSpeed gameSpeed)
    {
        super(mainPanel, gameSpeed, GameType.SINGLE, 1);
        this.remoteUser = remoteUser;
        this.tetrominoFactory = new TetrominoFactory(1);
        usersList.add(remoteUser);
    }

    public Object doInBackground()
    {
        mainPanel.writeLineInTextArea("Nowa gra pojedyncza rozpoczeta przez uzytkownika "
                + usersList.getFirst().getNick());
        // thisUser.sendToUser(new JSONObject().put("cmd", "clearLine").put("identifier", thisUser.getIdentifier()).put("row", 2));
        long startFrameTime = System.currentTimeMillis();
        placeNewTetromino(remoteUser);
        while (true)
        {
            checkPlayersMove();
            checkForLineToClear();
            if (checkForInactiveBlocks())
            {
                if (!placeNewTetromino(remoteUser)) return null;
            }

            if (System.currentTimeMillis() - startFrameTime >= getFrameInterval())
            {
                moveDownAllActiveBlocks();
                startFrameTime = System.currentTimeMillis();
            }
        }
    }


    private void checkForLineToClear()
    {

    }


}
