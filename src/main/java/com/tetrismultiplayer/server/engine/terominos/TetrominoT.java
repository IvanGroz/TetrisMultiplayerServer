package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoT extends Tetromino
{
    public TetrominoT(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.T);
        bricksList.add(new Brick(x, y - Brick.LENGTH));
        bricksList.add(new Brick(x - Brick.LENGTH, y));
        bricksList.add(new Brick(x, y));
        bricksList.add(new Brick(x + Brick.LENGTH, y));
    }

    public TetrominoT(Tetromino tetromino)
    {
        super(tetromino);
    }
}
