package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoI extends Tetromino
{
    public TetrominoI(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.I);
        bricksList.add(new Brick(x - (Brick.LENGTH * 2), y - Brick.LENGTH));
        bricksList.add(new Brick(x - Brick.LENGTH, y - Brick.LENGTH));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
        bricksList.add(new Brick(x + Brick.LENGTH, y - Brick.LENGTH));
    }

    public TetrominoI(Tetromino tetromino)
    {
        super(tetromino);
    }
}
