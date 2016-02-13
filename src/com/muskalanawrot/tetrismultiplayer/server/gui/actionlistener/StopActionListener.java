package com.muskalanawrot.tetrismultiplayer.server.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.muskalanawrot.tetrismultiplayer.server.Main;

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
	main.getMainPanel().setStartBtnStatus(true);
	main.getMainPanel().setStopBtnStatus(false);
	main.getMainPanel().setConnectionsBtnStatus(false);
	main.getMainPanel().getPortTextField().setEditable(true);
    }

}
