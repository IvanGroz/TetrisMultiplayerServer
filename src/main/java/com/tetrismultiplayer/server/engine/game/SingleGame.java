package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.user.Move;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import java.util.LinkedList;

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
            LinkedList<Brick> allBricks = new LinkedList<>();
            for (Tetromino tetromino : allTetrominos)
            {
                for (Brick brick : tetromino.getBricksList())
                {
                    allBricks.add(brick);
                }
            }
            allBricks.sort((x, y) -> (Integer.valueOf(x.getPosition().y).compareTo(Integer.valueOf(y.getPosition().y))));

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
                            ownerUser.removeTetromino(allTetrominos.get(i));
                            i--;
                        }
                    }
                }
            }
            ownerUser.getTetrominos().stream().filter(tetromino -> tetromino.getPosition().y <= line)
                    .forEach(tetromino1 -> tetromino1.moveDown());
            ownerUser.sendToUser(new JSONObject()
                    .put("cmd", "clearLine").put("identifier", ownerUser.getIdentifier()).put("row", line));
            for (Tetromino tetromino : ownerUser.getTetrominos())
            {
                boolean isStabile = false;
                for (Brick brick : tetromino.getBricksList())
                {
                    for (Tetromino tetromino2 : ownerUser.getTetrominos())
                    {
                        if (!tetromino2.equals(tetromino))
                        {
                            for (Brick brick2 : tetromino2.getBricksList())
                            {
                                if (brick.getPosition().x == brick2.getPosition().x
                                        && brick.getPosition().y == brick2.getPosition().y - 1)
                                {
                                    isStabile = true;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                if (!isStabile)
                {
                    System.out.println("Bedzie upuszczane");
                    Tetromino tetrominoCopy = Tetromino.getTetrominoCopy(tetromino);
                    Integer dropAmount = 0;
                    tetrominoCopy.moveDown();
                    while (!isCollision(tetrominoCopy, tetromino))
                    {
                        tetromino.moveDown();
                        tetrominoCopy.moveDown();
                        dropAmount++;
                    }
                    if (dropAmount > 0)
                    {
                        tetromino.moveDown();
                        for (RemoteUser user : usersList)
                        {
                            user.sendToUser(new JSONObject().put("cmd", "move")
                                    .put("identifier", ownerUser.getIdentifier())
                                    .put("key", Move.DROP.toString()).put("amount", dropAmount));
                        }
                    }
                }
            }
        }
    }
}
