package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Created by Marcin on 2016-02-16.
 */
public class SingleGame extends ParentGameEngine
{
    private RemoteUser remoteUser;
    private TetrominoFactory tetrominoFactory;

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
        long frameTime = System.currentTimeMillis();
        placeNewTetromino();
        while (true)
        {
            checkPlayersMove();
            checkForLineToClear();
            if (checkForInactiveBlock())
            {
                if (!placeNewTetromino()) return null;
            }
        }
    }



    private void checkForLineToClear()
    {

    }

    private boolean checkForInactiveBlock()
    {
        return false;
    }

    private boolean placeNewTetromino()
    {
        Tetromino newTetromino = tetrominoFactory.getNewTetromino();

        for (Brick newBrick : newTetromino.getBricksList())
        {
            for (Tetromino userTetromino : remoteUser.getTetrominos())
            {
                for (Brick brick : userTetromino.getBricksList())
                {
                    if (brick.getPosition().equals(newBrick.getPosition())) return false;
                }
            }
        }

        remoteUser.addTetromino(newTetromino);
        allTetrominos.add(newTetromino);
        remoteUser.sendToUser(newTetromino.toJSON()
                .put("identifier", remoteUser.getIdentifier()).put("cmd", "newTetromino"));
        return true;
    }
}
