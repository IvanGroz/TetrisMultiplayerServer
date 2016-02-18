package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-18.
 */
public class TetrominoFactory
{
    private Color color;
    private Point position;

    public TetrominoFactory(Color color, Integer position)
    {
        this.color = color;
        setPosition(position);
    }

    public TetrominoFactory(Integer position)
    {
        this.color = null;
        setPosition(position);
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
        if (color == null)
        {
            switch ((int) Math.round(Math.random() * 7))
            {
                case 0:
                    tetrominoColor = Color.PINK;
                    break;
                case 1:
                    tetrominoColor = Color.CYAN;
                    break;
                case 2:
                    tetrominoColor = Color.BLUE;
                    break;
                case 3:
                    tetrominoColor = Color.GRAY;
                    break;
                case 4:
                    tetrominoColor = Color.GREEN;
                    break;
                case 5:
                    tetrominoColor = Color.ORANGE;
                    break;
                case 6:
                    tetrominoColor = Color.YELLOW;
                    break;
                default:
                    tetrominoColor = Color.RED;
                    break;
            }
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
}
