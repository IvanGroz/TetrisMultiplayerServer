package main.java.com.tetrismultiplayer.server.engine.user;

/**
 * Class representing one of user moves that will be added to move queue.
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
