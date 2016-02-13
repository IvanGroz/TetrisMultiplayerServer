package com.muskalanawrot.tetrismultiplayer.server.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.muskalanawrot.tetrismultiplayer.server.engine.MainServerThread;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class StartActionListener implements ActionListener
{
    private MainPanel mainPanel;

    public StartActionListener(MainPanel mainPanel)
    {
	this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	MainServerThread thread = new MainServerThread(mainPanel);
	thread.execute();
    }

}
