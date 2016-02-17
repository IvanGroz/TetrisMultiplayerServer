package main.java.com.tetrismultiplayer.server.engine.terominos;

import org.json.JSONObject;

import java.awt.*;
import java.util.LinkedList;

/**
 * Common abstract class for all tetrominos.
 */
public abstract class Tetromino
{
    protected LinkedList<Brick> bricksList;
    protected Point position;
    protected TetrominoType type;
    protected Color color;

    protected Tetromino(int positionX, int positionY, Color color)
    {
        this.bricksList = new LinkedList<>();
        this.position = new Point(positionX, positionY);
        this.color = color;
    }

    public enum TetrominoType
    {
        I, J, L, O, S, T, Z
    }

    /**
     * Rotates tetromino clockwise.
     */
    public void rotate()
    {
        bricksList.forEach(brick -> {
            int newX = position.x + position.y - brick.getPosition().y - Brick.LENGTH;
            int newY = brick.getPosition().x + position.y - position.x;
            brick.setNewLocation(newX, newY);
        });
    }

    /**
     * Moves tetromino right.
     */
    public void moveRight()
    {
        bricksList.forEach(brick -> brick.moveBrick(1, 0));
        position.move(position.x + 1, position.y);
    }

    /**
     * Moves tetromino left.
     */
    public void moveLeft()
    {
        bricksList.forEach(brick -> brick.moveBrick(-1, 0));
        position.move(position.x - 1, position.y);
    }

    /**
     * Moves tetromino down.
     */
    public void moveDown()
    {
        bricksList.forEach(brick -> brick.moveBrick(0, 1));
        position.move(position.x, position.y + 1);
    }

    /**
     * Drops tetromino selected number of rows down.
     *
     * @param rowsNumber number of rows to drop down
     */
    public void drop(int rowsNumber)
    {
        bricksList.forEach(brick -> brick.moveBrick(0, rowsNumber));
        position.move(position.x, position.y + rowsNumber);
    }

    public JSONObject toJSON()
    {
        return new JSONObject().put("row", position.x).put("column", position.y)
                .put("color", color.getRGB()).put("tetrominoType", type);
    }
}
