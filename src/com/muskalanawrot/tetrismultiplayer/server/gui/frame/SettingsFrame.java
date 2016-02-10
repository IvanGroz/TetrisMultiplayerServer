package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.SettingsPanel;

public class SettingsFrame extends JFrame
{
    private static final long serialVersionUID = -5099033041385494292L;

    private SettingsPanel settingsPanel;

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
		    SettingsFrame frame = new SettingsFrame();
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
    public SettingsFrame()
    {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	settingsPanel = new SettingsPanel();
	setContentPane(settingsPanel);
    }

}
