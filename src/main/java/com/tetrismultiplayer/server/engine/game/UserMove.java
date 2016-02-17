package main.java.com.tetrismultiplayer.server.engine.game;

/**
 * Created by Marcin on 2016-02-16.
 */
public class UserMove
{
    Move move;
    RemoteUser user;

    public UserMove(RemoteUser user, Move move)
    {
        this.move = move;
        this.user = user;
    }

    public String toString()
    {
        return move + user.getNick();
    }

    public Move getMove()
    {
        return move;
    }

    public RemoteUser getUser()
    {
        return user;
    }
}
