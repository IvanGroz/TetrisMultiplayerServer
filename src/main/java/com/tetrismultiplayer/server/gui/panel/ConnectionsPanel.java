package main.java.com.tetrismultiplayer.server.gui.panel;

import main.java.com.tetrismultiplayer.server.engine.MainServerThread;

import javax.swing.*;
import java.awt.*;

public class ConnectionsPanel extends JPanel
{
    public JList<String> conns;

    public ConnectionsPanel()
    {
	setLayout(new BorderLayout());

	conns = new JList();
	DefaultListModel  listModel = new DefaultListModel();
	MainServerThread.usersIp.forEach(listModel::addElement);


	JList list = new JList(listModel);
	add(list);

    }
}
