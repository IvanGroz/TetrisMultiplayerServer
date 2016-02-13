package com.muskalanawrot.tetrismultiplayer.server.engine;

import javax.swing.SwingWorker;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class MainServerThread extends SwingWorker<Object, Object>
{
    MainPanel mainPanel;

    public MainServerThread(MainPanel mainPanel)
    {
	this.mainPanel = mainPanel;
    }

    @Override
    protected Object doInBackground() throws Exception
    {
	while(true)
	{
	    mainPanel.writeLineInTextArea("start serwera ");
	    return null;
	}
    }

}
