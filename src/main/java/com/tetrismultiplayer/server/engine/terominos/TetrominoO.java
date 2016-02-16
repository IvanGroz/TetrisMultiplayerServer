package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoO extends Tetromino
{
    public TetrominoO(Color color, int x, int y)
    {
        super(x, y);
        bricksList.add(new Brick(x - Brick.LENGTH, y - Brick.LENGTH, color));
        bricksList.add(new Brick(x, y - Brick.LENGTH, color));
        bricksList.add(new Brick(x - Brick.LENGTH, y, color));
        bricksList.add(new Brick(x, y, color));
    }
}