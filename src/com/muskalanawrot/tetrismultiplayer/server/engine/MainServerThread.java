package com.muskalanawrot.tetrismultiplayer.server.engine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

import org.json.JSONObject;

import com.muskalanawrot.tetrismultiplayer.server.Main;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class MainServerThread extends SwingWorker<Object, Object>
{
    private MainPanel mainPanel;
    private ServerSocket serverSocket;
    private Integer serverPort;
    private Executor userThreadExecutor;
    private AtomicInteger clientsNumber;
    private Integer maxUserThreads;
    private LinkedList<UserServerThread> userThreadList;

    public MainServerThread(Main main, Integer serverPort, Integer maxUserThreads)
    {
	this.mainPanel = main.getMainPanel();
	this.serverPort = serverPort;
	this.maxUserThreads = maxUserThreads;
	this.clientsNumber = new AtomicInteger(0);
	this.userThreadExecutor = Executors.newFixedThreadPool(maxUserThreads);
	this.userThreadList = new LinkedList<UserServerThread>();
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
		Socket socket = serverSocket.accept();
		String nick = new JSONObject(
			new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine())
				.getString("nick");
		mainPanel.writeLineInTextArea(
			"Nowe polaczenie z ip: " + socket.getRemoteSocketAddress().toString().substring(1) + " nick: "
				+ nick);
		if (clientsNumber.get() < maxUserThreads)
		{
		    UserServerThread userThread = new UserServerThread(socket);
		    userThread.addPropertyChangeListener(new PropertyChangeListener()
		    {
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
			    if (userThread.isDone())
			    {
				mainPanel.setActivePlayersNumber(clientsNumber.decrementAndGet());
			    }
			}
		    });
		    userThreadList.add(new UserServerThread(socket));
		    userThreadExecutor.execute(userThreadList.getLast());
		    mainPanel.setActivePlayersNumber(clientsNumber.incrementAndGet());
		}
		else
		{
		    JSONObject rejectedObj = new JSONObject();
		    rejectedObj.put("state", "rejected");
		    new PrintWriter(socket.getOutputStream(), true).println(rejectedObj.toString());
		    mainPanel.writeLineInTextArea(
			    "Polaczenie odrzucone, przekroczona maksymalna ilosc klientow: " + maxUserThreads);
		}
	    }
	}
	catch (IOException e)
	{
	    mainPanel.writeLineInTextArea("Blad podczas pracy serwera.");
	    changeBtnStatus();
	}
	finally
	{
	    try
	    {
		serverSocket.close();
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
	return null;
    }

    private void changeBtnStatus()
    {
	mainPanel.setStartBtnStatus(true);
	mainPanel.setStopBtnStatus(false);
	mainPanel.setConnectionsBtnStatus(false);
	mainPanel.getPortTextField().setEditable(true);
    }

    @Override
    protected void done()
    {
	try
	{
	    userThreadList.forEach(userThread->{
		userThread.cancel(true);
	    });
	    serverSocket.close();
	}
	catch (IOException e)
	{
	    mainPanel.writeLineInTextArea("Blad podczas konczenia pracy serwera.");
	}
    }
}
