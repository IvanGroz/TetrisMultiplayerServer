package com.muskalanawrot.tetrismultiplayer.server.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.muskalanawrot.tetrismultiplayer.server.Main;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class StopActionListener implements ActionListener
{
    private Main main;

    public StopActionListener(Main main)
    {
	this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	main.getMainServerThread().cancel(true);
	
	MainPanel mainPanel = main.getMainPanel();
	
	mainPanel.setStartBtnStatus(true);
	mainPanel.setStopBtnStatus(false);
	mainPanel.setConnectionsBtnStatus(false);
	mainPanel.getPortTextField().setEditable(true);
	mainPanel.setActiveGamesNumber(0);
	mainPanel.setActivePlayersNumber(0);
    }

}
