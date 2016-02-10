package com.muskalanawrot.tetrismultiplayer.server;

import com.muskalanawrot.tetrismultiplayer.server.gui.frame.MainFrame;

public class Main implements Runnable
{
    private MainFrame mainFrame;

    public Main()
    {
	this.mainFrame = new MainFrame();
    }

    public static void main(String args[])
    {
	new Main().run();
    }

    @Override
    public void run()
    {
	mainFrame.setVisible(true);
    }
}
