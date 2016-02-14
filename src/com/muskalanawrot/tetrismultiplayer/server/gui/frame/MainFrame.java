package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.Main;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = -6903076602874962620L;

    private MainPanel mainPanel;
    private Main main;

    public MainFrame(Main main)
    {
	this.main = main;
	this.mainPanel = new MainPanel(main);
	init();
    }

    private void init()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(745, 468);
	setLocationRelativeTo(null);
	setTitle("Tetris Multiplayer - Server");
	setContentPane(mainPanel);
	setResizable(false);
	setVisible(true);
	addWindowListener(new WindowListener()
	{

	    @Override
	    public void windowOpened(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void windowClosing(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void windowClosed(WindowEvent e)
	    {
		if (main.getEntityManagerFactory().isOpen())
		{
		    main.getEntityManagerFactory().close();
		}
	    }

	    @Override
	    public void windowIconified(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void windowDeiconified(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void windowActivated(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void windowDeactivated(WindowEvent e)
	    {
		// TODO Auto-generated method stub

	    }

	});
    }

    public MainPanel getMainPanel()
    {
	return mainPanel;
    }
}
