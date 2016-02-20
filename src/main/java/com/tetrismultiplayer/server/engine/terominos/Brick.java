package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Class for single brick from tetromino.
 */
public class Brick
{
    private Point position;
    private Dimension size;

    public static final Integer LENGTH = 1;

    public Brick(int positionX, int positionY)
    {
        super();
        this.position = new Point(positionX, positionY);
        this.size = new Dimension(LENGTH, LENGTH);
    }

    /**
     * Moves brick desired rows and columns number.
     * @param x rows number
     * @param y columns number
     */
    public void moveBrick(int x, int y)
    {
        position = new Point(position.x + x, position.y + y);
    }

    /**
     * Sets new location to brick.
     * @param x
     * @param y
     */
    public void setNewLocation(int x, int y)
    {
        position = new Point(x, y);
    }

    /**
     * Returns actual brick position.
     * @return
     */
    public Point getPosition()
    {
        return position;
    }

    public Dimension getSize()
    {
        return size;
    }
}
