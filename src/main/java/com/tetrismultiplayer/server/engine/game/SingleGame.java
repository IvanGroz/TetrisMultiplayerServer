package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.engine.terominos.TetrominoL;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;
import org.json.JSONObject;

import java.awt.*;

/**
 * Created by Marcin on 2016-02-16.
 */
public class SingleGame extends GameEngine
{
    public SingleGame(RemoteUser remoteUser, MainPanel mainPanel)
    {
        super(mainPanel);
        usersList.add(remoteUser);
    }

    public Object doInBackground()
    {
        mainPanel.writeLineInTextArea("Nowa gra pojedyncza rozpoczeta przez uzytkownika "
                + usersList.getFirst().getNick());
        RemoteUser thisUser = usersList.getFirst();
        thisUser.sendToUser(new TetrominoL(Color.PINK, 5, 10).toJSON().put("identifier", thisUser.getIdentifier()).put("cmd", "newTetromino"));
        while (true)
        {
            if (!moveQueue.isEmpty())
            {
                UserMove newMove = moveQueue.poll();
                if (newMove.getMove().toString().equals("drop"))
                {
                    thisUser.sendToUser(new JSONObject().put("cmd", "move").put("identifier", thisUser.getIdentifier()).put("key", newMove.getMove()).put("amount",3));
                }
                else
                {
                    thisUser.sendToUser(new JSONObject().put("cmd", "move").put("identifier", thisUser.getIdentifier()).put("key", newMove.getMove()));
                }

            }
        }
    }
}
