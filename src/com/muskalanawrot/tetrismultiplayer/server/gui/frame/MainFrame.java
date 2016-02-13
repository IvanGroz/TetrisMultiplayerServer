package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = -6903076602874962620L;

    private MainPanel mainPanel;

    public MainFrame(EntityManagerFactory entityManagerFactory)
    {
	mainPanel = new MainPanel(entityManagerFactory);
	init();
    }

    public void init()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(745, 468);
	setLocationRelativeTo(null);
	setTitle("Tetris Multiplayer - Server");
	setContentPane(mainPanel);
	setResizable(false);
	setVisible(true);
    }

    public MainPanel getMainPanel()
    {
	return mainPanel;
    }
}
