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

    protected Tetromino(int positionX, int positionY, Color color, TetrominoType type)
    {
        this.bricksList = new LinkedList<>();
        this.position = new Point(positionX, positionY);
        this.color = color;
        this.type = type;
        System.out.println("nowy " + type.toString());
    }

    protected Tetromino(Tetromino tetromino)
    {
        System.out.println("w konstruktorze");
        this.bricksList = new LinkedList<>();
        tetromino.getBricksList().forEach(brick -> bricksList.add(new Brick(brick.getPosition().x, brick.getPosition().y)));
        this.position = new Point(tetromino.getPosition().x, tetromino.getPosition().y);
        this.color = tetromino.getColor();
        this.type = tetromino.getType();
        System.out.println("nowy " + type.toString());
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

    public Color getColor()
    {
        return color;
    }

    public LinkedList<Brick> getBricksList()
    {
        return bricksList;
    }

    public void removeBrick(Brick brick)
    {
        bricksList.remove(brick);
    }

    public Point getPosition()
    {
        return position;
    }

    public TetrominoType getType()
    {
        return type;
    }

    public static Tetromino getTetrominoCopy(Tetromino tetromino)
    {
        TetrominoType type = tetromino.getType();

        if (type.equals(TetrominoType.I)) return new TetrominoI(tetromino);
        else if (type.equals(TetrominoType.J)) return new TetrominoJ(tetromino);
        else if (type.equals(TetrominoType.L)) return new TetrominoL(tetromino);
        else if (type.equals(TetrominoType.O)) return new TetrominoO(tetromino);
        else if (type.equals(TetrominoType.S)) return new TetrominoS(tetromino);
        else if (type.equals(TetrominoType.T)) return new TetrominoT(tetromino);
        else return new TetrominoZ(tetromino);
    }
}
