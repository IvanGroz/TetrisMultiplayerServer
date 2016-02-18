package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoJ extends Tetromino
{
    public TetrominoJ(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.J);
        bricksList.add(new Brick(x - (Brick.LENGTH * 2), y));
        bricksList.add(new Brick(x - Brick.LENGTH, y));
        bricksList.add(new Brick(x, y));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
    }

    public TetrominoJ(Tetromino tetromino)
    {
        super(tetromino);
    }
}
