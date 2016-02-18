package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.Tetromino;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoFactory;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class SingleGame extends GameEngine
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
        }
    }

    private void checkPlayersMove()
    {
        if (!moveQueue.isEmpty())
        {
            UserMove newMove = moveQueue.poll();
            System.out.println("tutaj1");
            Tetromino activeTetromino = newMove.getUser().getActiveTetromino();
            System.out.println("tutaj2");
            Tetromino tetrominoCopy = Tetromino.getTetrominoCopy(activeTetromino);
            System.out.println("tutaj3");
            if (newMove.getMove().toString().equals("drop"))
            {
                System.out.println("tutaj");
                //thisUser.sendToUser(new JSONObject().put("cmd", "move").put("identifier", thisUser.getIdentifier()).put("key", newMove.getMove()).put("amount", 3));
            }
            else
            {
                System.out.println("tutaj");
                if (newMove.getMove() == (Move.DOWN)) tetrominoCopy.moveDown();
                else if (newMove.getMove() == (Move.LEFT))
                {
                    System.out.println("w lewo");
                    tetrominoCopy.moveLeft();
                }
                else if (newMove.getMove() == (Move.RIGHT)) tetrominoCopy.moveRight();
                else if (newMove.getMove() == (Move.ROTATE)) tetrominoCopy.rotate();
                System.out.println("tutaj");
                if (!isCollision(tetrominoCopy, activeTetromino))
                {
                    if (newMove.getMove().equals(Move.DOWN)) activeTetromino.moveDown();
                    else if (newMove.getMove().equals(Move.LEFT)) activeTetromino.moveLeft();
                    else if (newMove.getMove().equals(Move.RIGHT)) activeTetromino.moveRight();
                    else if (newMove.getMove().equals(Move.ROTATE)) activeTetromino.rotate();

                    newMove.getUser().sendToUser(new JSONObject().put("cmd", "move")
                            .put("identifier", newMove.getUser().getIdentifier()).put("key", newMove.getMove()));
                }

            }
        }
    }

    private boolean isCollision(Tetromino tetromino, Tetromino previousTetromino)
    {
        for (Brick brick : tetromino.getBricksList())
        {
            Point brickPosition = brick.getPosition();
            if (brickPosition.x < 0 || brickPosition.x < getColumnNumber() || brickPosition.y > getRowNumber())
                return true;
            for (Tetromino existingTetromino : allTetrominos)
            {
                if (existingTetromino.getPosition() != previousTetromino.getPosition())
                {
                    for (Brick existingBrick : existingTetromino.getBricksList())
                    {
                        if (brickPosition.equals(existingBrick.getPosition())) return true;
                    }
                }
            }
        }
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

        remoteUser.getTetrominos().add(newTetromino);
        allTetrominos.add(newTetromino);
        remoteUser.sendToUser(newTetromino.toJSON()
                .put("identifier", remoteUser.getIdentifier()).put("cmd", "newTetromino"));
        return true;
    }
}
