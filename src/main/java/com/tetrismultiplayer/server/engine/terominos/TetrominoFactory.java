package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-18.
 */
public class TetrominoFactory
{
    private Color color;
    private Point position;
    private Boolean isRandomColor;

    public TetrominoFactory(Integer position)
    {
        this.isRandomColor = false;
        color = getRandomColor();
        setPosition(position);
    }

    public TetrominoFactory()
    {
        this.color = null;
        this.isRandomColor = true;
        setPosition(1);
    }

    private void setPosition(int positionNumber)
    {
        switch (positionNumber)
        {
            case 1:
                position = new Point(5, 2);
                break;
            case 2:
                position = new Point(15, 2);
                break;
            case 3:
                position = new Point(25, 2);
                break;
            case 4:
                position = new Point(35, 2);
                break;
        }
    }

    public Tetromino getNewTetromino()
    {
        Color tetrominoColor = null;
        if (isRandomColor)
        {
            tetrominoColor = getRandomColor();
        }
        else
        {
            tetrominoColor = color;
        }

        Tetromino newTetromino;
        switch ((int) Math.round(Math.random() * 6))
        {
            case 0:
                newTetromino = new TetrominoI(tetrominoColor, position.x, position.y);
                break;
            case 1:
                newTetromino = new TetrominoJ(tetrominoColor, position.x, position.y);
                break;
            case 2:
                newTetromino = new TetrominoL(tetrominoColor, position.x, position.y);
                break;
            case 3:
                newTetromino = new TetrominoO(tetrominoColor, position.x, position.y);
                break;
            case 4:
                newTetromino = new TetrominoS(tetrominoColor, position.x, position.y);
                break;
            case 5:
                newTetromino = new TetrominoT(tetrominoColor, position.x, position.y);
                break;
            default:
                newTetromino = new TetrominoZ(tetrominoColor, position.x, position.y);
                break;
        }
        return newTetromino;
    }

    private Color getRandomColor()
    {
        switch ((int) Math.round(Math.random() * 7))
        {
            case 0:
                return Color.PINK;
            case 1:
                return Color.CYAN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GRAY;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.ORANGE;
            case 6:
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }
}
