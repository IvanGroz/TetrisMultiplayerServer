package main.java.com.tetrismultiplayer.server.engine.game;

import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

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

        while (true)
        {
            if (!moveQueue.isEmpty())
            {
                UserMove newMove = moveQueue.poll();
                System.out.println(newMove);
            }
        }
    }
}
