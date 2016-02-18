package main.java.com.tetrismultiplayer.server.engine.user;

/**
 * Created by Marcin on 2016-02-17.
 */
public enum Move
{
    DOWN, LEFT, RIGHT, DROP, ROTATE;

    public String toString()
    {
        return name().toLowerCase();
    }
}

