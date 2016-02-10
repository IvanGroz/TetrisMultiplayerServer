package com.muskalanawrot.tetrismultiplayer.server;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.MainPanel;

public class Main implements Runnable
{
    private MainPanel mainPanel;
    private JFrame mainFrame;

    public Main()
    {
	this.mainPanel = new MainPanel();
	this.mainFrame = new JFrame();
    }

    public static void main(String args[])
    {
	new Thread(new Main()).start();
    }

    @Override
    public void run()
    {
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainFrame.setBounds(100, 100, 793, 495);
	mainFrame.setTitle("Tetris Multiplayer - Server");
	mainFrame.setContentPane(mainPanel);
	mainFrame.setResizable(false);
	mainFrame.setVisible(true);
    }
}
