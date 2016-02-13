package com.muskalanawrot.tetrismultiplayer.server.gui.actionlistener;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.muskalanawrot.tetrismultiplayer.server.gui.frame.ConnectionsFrame;

public class ConnectionsActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
	EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    ConnectionsFrame frame = new ConnectionsFrame();
		    frame.setVisible(true);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

}
