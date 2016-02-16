package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoZ extends Tetromino
{
    public TetrominoZ(Color color, int x, int y)
    {
        super(x, y);
        bricksList.add(new Brick(x - (Brick.LENGTH / 2 + Brick.LENGTH), y - Brick.LENGTH, color));
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y - Brick.LENGTH, color));
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y, color));
        bricksList.add(new Brick(x + (Brick.LENGTH / 2), y, color));
    }
}
