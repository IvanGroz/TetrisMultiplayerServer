package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoJ extends Tetromino
{
    public TetrominoJ(Color color, int x, int y)
    {
        super(x, y, color);
        type = TetrominoType.J;
        bricksList.add(new Brick(x, y - (Brick.LENGTH + Brick.LENGTH / 2)));
        bricksList.add(new Brick(x, y - (Brick.LENGTH / 2)));
        bricksList.add(new Brick(x, y + (Brick.LENGTH / 2)));
        bricksList.add(new Brick(x - Brick.LENGTH, y + (Brick.LENGTH / 2)));
    }
}
