package main.java.com.tetrismultiplayer.server.engine.terominos;

import java.awt.*;

/**
 * Tetromino factory class creates new tetromino for users.
 */
public class TetrominoFactory
{
    private Color color;
    private Point position;
    private Boolean isRandomColor;

    /**
     * Creates new tetromino factory for user in multiplayer games with one color.
     * @param position
     */
    public TetrominoFactory(Integer position)
    {
        this.isRandomColor = false;
        color = getRandomColor();
        setPosition(position);
    }

    /**
     * Creates tetromino factory for single player game returns tetrominos with random color.
     */
    public TetrominoFactory()
    {
        this.color = null;
        this.isRandomColor = true;
        setPosition(1);
    }

    /**
     * Method setting up new tetromino position based on user position on game panel.
     * @param positionNumber
     */
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

    /**
     * Returns new tetromino.
     * @return
     */
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
