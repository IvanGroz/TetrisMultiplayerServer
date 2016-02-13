package com.muskalanawrot.tetrismultiplayer.server.engine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.SwingWorker;

import com.muskalanawrot.tetrismultiplayer.server.Main;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class MainServerThread extends SwingWorker<Object, Object>
{
    private MainPanel mainPanel;
    private ServerSocket serverSocket;
    private Integer serverPort;

    public MainServerThread(Main main, Integer serverPort)
    {
	this.mainPanel = main.getMainPanel();
	this.serverPort = serverPort;
    }

    @Override
    protected Object doInBackground()
    {
	try
	{
	    serverSocket = new ServerSocket(serverPort);
	    mainPanel.getIpTextField().setText(InetAddress.getLocalHost().getHostAddress());
	    while (!isCancelled())
	    {
		System.out.println("Dziala");
		Socket socket = serverSocket.accept();
	    }
	}
	catch (SocketException e)
	{
	    mainPanel.setStartBtnStatus(true);
	    changeBtnStatus();
	}
	catch (IOException e)
	{
	    mainPanel.writeLineInTextArea("Blad podczas pracy serwera.");
	    changeBtnStatus();
	}
	return null;
    }

    private void changeBtnStatus()
    {
	mainPanel.setStartBtnStatus(true);
	mainPanel.setStopBtnStatus(false);
	mainPanel.setConnectionsBtnStatus(false);
	mainPanel.getPortTextField().setEditable(true);;
    }

    @Override
    protected void done()
    {
	System.out.println("KONIEC");
	try
	{
	    serverSocket.close();
	}
	catch (IOException e)
	{
	    mainPanel.writeLineInTextArea("Blad podczas konczenia pracy serwera.");
	}
    }
}
