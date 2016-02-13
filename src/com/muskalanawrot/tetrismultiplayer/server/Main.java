package com.muskalanawrot.tetrismultiplayer.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManagerFactory;

import com.muskalanawrot.tetrismultiplayer.server.database.ConnectToDB;
import com.muskalanawrot.tetrismultiplayer.server.gui.frame.MainFrame;
import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

public class Main implements Runnable
{
    private MainFrame mainFrame;
    private EntityManagerFactory entityManagerFactory;
    private MainPanel mainPanel;

    public Main()
    {
	this.mainFrame = new MainFrame(entityManagerFactory);
	this.mainPanel = mainFrame.getMainPanel();
    }

    public static void main(String args[])
    {
	new Main().run();
    }

    @Override
    public void run()
    {
	mainFrame.setVisible(true);
	connectToDB();
    }

    private void connectToDB()
    {
	ConnectToDB task = new ConnectToDB(mainPanel);
	task.addPropertyChangeListener(new PropertyChangeListener()
	{
	    @Override
	    public void propertyChange(PropertyChangeEvent evt)
	    {
		if (task.isDone())
		{
		    try
		    {
			entityManagerFactory = task.get();
			if (entityManagerFactory != null)
			{
			    mainPanel.setStartBtnStatus(true);
			    mainPanel.setRankingBtnStatus(true);
			    mainPanel.writeLineInTextArea("Polaczenie z baza danych zostalo nawiazane.");
			}
			else
			{
			    mainPanel.writeLineInTextArea("Podczas laczenia z baza danych wystapil blad,"
				    + " zrestartuj program aby sprobowac ponownie.");
			}
		    }
		    catch (ExecutionException | InterruptedException e)
		    {
			mainPanel.writeLineInTextArea("Podczas laczenia z baza wystapil blad.");
			e.printStackTrace();
		    }
		}
	    }
	});
	mainPanel.writeLineInTextArea("Trwa laczenie z baza danych.");
	task.execute();
    }
}
