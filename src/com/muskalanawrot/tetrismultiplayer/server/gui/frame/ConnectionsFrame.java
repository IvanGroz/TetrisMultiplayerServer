package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.ConnectionsPanel;

public class ConnectionsFrame extends JFrame
{
    private static final long serialVersionUID = -2715354987475235043L;

    private ConnectionsPanel connectionsPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
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

    /**
     * Create the frame.
     */
    public ConnectionsFrame()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	connectionsPanel = new ConnectionsPanel();
	setContentPane(connectionsPanel);
    }

}
