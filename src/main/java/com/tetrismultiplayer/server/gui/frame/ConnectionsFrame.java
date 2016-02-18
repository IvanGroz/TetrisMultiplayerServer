package main.java.com.tetrismultiplayer.server.gui.frame;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.gui.panel.ConnectionsPanel;

import javax.swing.*;

public class ConnectionsFrame extends JFrame
{
    private static final long serialVersionUID = -2715354987475235043L;

    private ConnectionsPanel connectionsPanel;

    /**
     * Create the frame.
     */
    public ConnectionsFrame(Main main)
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connectionsPanel = new ConnectionsPanel(main);
        setContentPane(connectionsPanel);
        setResizable(false);
        setTitle("Połączenia");
        setSize(400, 400);
        setLocationRelativeTo(null);
        System.out.println("tutaj");
    }

}
