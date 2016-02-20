package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Class for O shaped tetromino.
 */
public class TetrominoO extends Tetromino
{
    public TetrominoO(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.O);
        bricksList.add(new Brick(x - Brick.LENGTH, y - Brick.LENGTH));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
        bricksList.add(new Brick(x - Brick.LENGTH, y));
        bricksList.add(new Brick(x, y));
    }

    public TetrominoO(Tetromino tetromino)
    {
        super(tetromino);
    }
}