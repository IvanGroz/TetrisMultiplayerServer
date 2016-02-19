package test.java.com.tetrismultiplayer.server;

import main.java.com.tetrismultiplayer.server.engine.terominos.Brick;
import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoS;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class TetrominoSTest
{
    TetrominoS tetromino;

    @Before
    public void setUp() throws Exception
    {
	tetromino = new TetrominoS(Color.BLUE, 100, 100);
    }

    @Test
    public void testRotate() throws Exception
    {
	LinkedList<Brick> all = tetromino.getBricksList();
	assertEquals(99, all.get(0).getPosition().x);
	assertEquals(100, all.get(1).getPosition().x);
	assertEquals(98, all.get(2).getPosition().x);
	assertEquals(99, all.get(3).getPosition().x);

	assertEquals(99, all.get(0).getPosition().y);
	assertEquals(99, all.get(1).getPosition().y);
	assertEquals(100, all.get(2).getPosition().y);
	assertEquals(100, all.get(3).getPosition().y);

	tetromino.rotate();

	assertEquals(100, all.get(0).getPosition().x);
	assertEquals(100, all.get(1).getPosition().x);
	assertEquals(99, all.get(2).getPosition().x);
	assertEquals(99, all.get(3).getPosition().x);

	assertEquals(99, all.get(0).getPosition().y);
	assertEquals(100, all.get(1).getPosition().y);
	assertEquals(98, all.get(2).getPosition().y);
	assertEquals(99, all.get(3).getPosition().y);
    }

    @Test
    public void testMoveRight() throws Exception
    {
	assertEquals(100, tetromino.getPosition().x);
	tetromino.moveRight();
	assertEquals(101, tetromino.getPosition().x);
    }

    @Test
    public void testMoveLeft() throws Exception
    {
	assertEquals(100, tetromino.getPosition().x);
	tetromino.moveLeft();
	assertEquals(99, tetromino.getPosition().x);
    }

    @Test
    public void testMoveDown() throws Exception
    {
	assertEquals(100, tetromino.getPosition().y);
	tetromino.moveDown();
	assertEquals(101, tetromino.getPosition().y);
    }

    @Test
    public void testDrop() throws Exception
    {
	assertEquals(100, tetromino.getPosition().y);
	tetromino.drop(10);
	assertEquals(110, tetromino.getPosition().y);
    }

    @Test
    public void testGetPosition() throws Exception
    {
	assertEquals(100, tetromino.getPosition().x);
	assertEquals(100, tetromino.getPosition().y);
    }

    @Test
    public void testGetType() throws Exception
    {
	assertEquals("S", tetromino.getType().name());
    }
}