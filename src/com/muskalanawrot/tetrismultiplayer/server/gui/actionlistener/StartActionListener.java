package com.muskalanawrot.tetrismultiplayer.server.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.muskalanawrot.tetrismultiplayer.server.Main;
import com.muskalanawrot.tetrismultiplayer.server.engine.MainServerThread;

public class StartActionListener implements ActionListener
{
    private Main main;

    public StartActionListener(Main main)
    {
	this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	main.getMainPanel().writeLineInTextArea("Uruchamianie serwera.");
	Integer serverPort;
	try
	{
	    serverPort = Integer.parseInt(main.getMainPanel().getPortTextField().getText());
	    if (serverPort <= 49152 || serverPort >= 65535) throw new NumberFormatException();

	    MainServerThread mainServerThread = new MainServerThread(main, serverPort, 1);
	    main.setMainServerThread(mainServerThread);
	    mainServerThread.execute();
	    main.getMainPanel().writeLineInTextArea("Uruchomiono serwer gry.");
	    main.getMainPanel().setStartBtnStatus(false);
	    main.getMainPanel().setStopBtnStatus(true);
	    main.getMainPanel().setConnectionsBtnStatus(true);
	    main.getMainPanel().getPortTextField().setEditable(false);
	}
	catch (NumberFormatException e2)
	{
	    main.getMainPanel().writeLineInTextArea("Wprowadz poprawny numer portu!");
	}
    }
}
