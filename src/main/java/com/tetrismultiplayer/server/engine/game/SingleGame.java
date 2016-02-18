package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import java.util.LinkedList;

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

        long startFrameTime = System.currentTimeMillis();
        placeNewTetromino(remoteUser);
        while (true)
        {
            checkPlayersMove();
            clearLine(checkForLineToClear());
            if (checkForInactiveBlock(remoteUser))
            {
                if (!placeNewTetromino(remoteUser))
                {
                    System.out.println("koniec gry");
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

    private int checkForLineToClear()
    {
        LinkedList<Brick> allBricks = new LinkedList<>();
        for (Tetromino tetromino : allTetrominos)
        {
            for (Brick brick : tetromino.getBricksList())
            {
                allBricks.add(brick);
            }
        }

        allBricks.sort((x, y) -> (Integer.valueOf(x.getPosition().y).compareTo(Integer.valueOf(y.getPosition().y))));
        int counter = 1;
        int i;
        for (i = 0; i < allBricks.size() - 1; i++)
        {
            if (allBricks.get(i).getPosition().y == allBricks.get(i + 1).getPosition().y)
            {
                counter++;
                if (counter == getColumnNumber()) return allBricks.get(i).getPosition().y;
            }
            else
            {
                counter = 1;
            }
        }
        return -1;
    }

    private void clearLine(int line)
    {
        if (line != -1)
        {
            for (int i = 0; i < allTetrominos.size(); i++)
            {
                LinkedList<Brick> bricks = allTetrominos.get(i).getBricksList();
                for (int j = 0; j < bricks.size(); j++)
                {
                    if (bricks.get(j).getPosition().y == line)
                    {
                        allTetrominos.get(i).removeBrick(bricks.get(j));
                        j--;
                        if (allTetrominos.get(i).getBricksList().isEmpty())
                        {
                            remoteUser.removeTetromino(allTetrominos.get(i));
                            i--;
                        }
                    }
                }
            }
            System.out.println("linia do usuniecia "+line);
            remoteUser.getTetrominos().stream().filter(tetromino -> tetromino.getPosition().y <= line)
                    .forEach(tetromino1 -> tetromino1.moveDown());

            remoteUser.sendToUser(new JSONObject()
                    .put("cmd", "clearLine").put("identifier", remoteUser.getIdentifier()).put("row", line));
        }
    }
}
