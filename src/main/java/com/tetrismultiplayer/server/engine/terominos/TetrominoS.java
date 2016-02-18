package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoS extends Tetromino
{
    public TetrominoS(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.S);
        bricksList.add(new Brick(x - Brick.LENGTH, y - Brick.LENGTH));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
        bricksList.add(new Brick(x - (Brick.LENGTH * 2), y));
        bricksList.add(new Brick(x - Brick.LENGTH, y));
    }

    public TetrominoS(Tetromino tetromino)
    {
        super(tetromino);
    }
}
