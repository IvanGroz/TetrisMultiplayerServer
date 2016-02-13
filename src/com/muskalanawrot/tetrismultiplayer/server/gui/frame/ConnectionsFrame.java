package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.ConnectionsPanel;

public class ConnectionsFrame extends JFrame
{
    private static final long serialVersionUID = -2715354987475235043L;

    private ConnectionsPanel connectionsPanel;

    /**
     * Create the frame.
     */
    public ConnectionsFrame()
    {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	connectionsPanel = new ConnectionsPanel();
	setContentPane(connectionsPanel);
	setTitle("Polaczenia");
	setSize(500,400);
	setLocationRelativeTo(null);
    }

}
