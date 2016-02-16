package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class Brick
{
    private Point position;
    private Dimension size;
    private Color color;

    public static final Integer LENGTH = 20;

    public Brick(int positionX, int positionY, Color color)
    {
        super();
        this.position = new Point(positionX, positionY);
        this.size = new Dimension(LENGTH, LENGTH);
        this.color = color;
    }

    public void moveBrick(int x, int y)
    {
        position = new Point(position.x + x, position.y + y);
    }

    public void setNewLocation(int x, int y)
    {
        position = new Point(x, y);
    }

    public Point getPosition()
    {
        return position;
    }

    public Dimension getSize()
    {
        return size;
    }
}
