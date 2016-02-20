package main.java.com.tetrismultiplayer.server.engine.user;

/**
 * Enum representing user move types.
 */
public enum Move
{
    DOWN, LEFT, RIGHT, DROP, ROTATE;

    public String toString()
    {
        return name().toLowerCase();
    }
}

