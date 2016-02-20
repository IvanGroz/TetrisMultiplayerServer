package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Class for L shaped tetromino.
 */
public class TetrominoL extends Tetromino
{
    public TetrominoL(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.L);
        bricksList.add(new Brick(x - (Brick.LENGTH * 2), y));
        bricksList.add(new Brick(x - Brick.LENGTH, y));
        bricksList.add(new Brick(x, y));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
    }

    public TetrominoL(Tetromino tetromino)
    {
        super(tetromino);
    }
}
