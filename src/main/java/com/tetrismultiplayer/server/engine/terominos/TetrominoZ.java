package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoZ extends Tetromino
{
    public TetrominoZ(Color color, int x, int y)
    {
        super(x, y, color, TetrominoType.Z);
        bricksList.add(new Brick(x - Brick.LENGTH, y - Brick.LENGTH));
        bricksList.add(new Brick(x, y - Brick.LENGTH));
        bricksList.add(new Brick(x, y));
        bricksList.add(new Brick(x + Brick.LENGTH, y));
    }

    public TetrominoZ(Tetromino tetromino)
    {
        super(tetromino);
    }
}
