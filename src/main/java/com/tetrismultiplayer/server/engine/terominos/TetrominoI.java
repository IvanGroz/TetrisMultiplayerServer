package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class TetrominoI extends Tetromino
{
    public TetrominoI(Color color, int x, int y)
    {
        super(x, y, color);
        type = TetrominoType.I;
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y - (Brick.LENGTH * 2)));
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y - Brick.LENGTH));
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y));
        bricksList.add(new Brick(x - (Brick.LENGTH / 2), y + Brick.LENGTH));
    }
}
