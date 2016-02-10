package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.RankingPanel;

public class RankingFrame extends JFrame
{
    private static final long serialVersionUID = -8209794248344578503L;

    private RankingPanel rankingPanel;

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
		    RankingFrame frame = new RankingFrame();
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
    public RankingFrame()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	rankingPanel = new RankingPanel();
	setContentPane(rankingPanel);
    }

}
