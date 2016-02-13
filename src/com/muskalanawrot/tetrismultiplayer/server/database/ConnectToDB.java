package com.muskalanawrot.tetrismultiplayer.server.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.swing.SwingWorker;

import org.hibernate.exception.JDBCConnectionException;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.MainPanel;

/**
 * Class creating and connecting entity manager factory to database.
 */
public class ConnectToDB extends SwingWorker<EntityManagerFactory, Object>
{
    MainPanel mainPanel;

    public ConnectToDB(MainPanel mainPanel)
    {
	this.mainPanel = mainPanel;
    }

    /**
     * Creates EntityManagerFactory and checks connection to db.
     * 
     * @return EntityManagerFactory reference
     */
    @Override
    protected EntityManagerFactory doInBackground() throws Exception
    {
	EntityManagerFactory emf;
	for (int i = 0; i < 5; i++)
	{
	    try
	    {
		emf = Persistence.createEntityManagerFactory("persistenceUnit");
		emf.createEntityManager().createNativeQuery("select 1 from dual").getSingleResult();
		return emf;
	    }
	    catch (JDBCConnectionException | PersistenceException e)
	    {
		mainPanel.writeLineInTextArea("Podczas proby polaczenia z baza danych wystapil blad.");
		mainPanel.writeLineInTextArea("Ponowienie proby za: ");
		for (Integer j = 5; j > 0; j--)
		{
		    mainPanel.writeLineInTextArea(j.toString());
		    if (j != 1) Thread.sleep(1000);
		}
	    }
	}
	return null;
    }

}
