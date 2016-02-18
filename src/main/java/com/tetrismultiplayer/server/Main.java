package main.java.com.tetrismultiplayer.server;

import main.java.com.tetrismultiplayer.server.database.ConnectToDB;
import main.java.com.tetrismultiplayer.server.engine.MainServerThread;
import main.java.com.tetrismultiplayer.server.gui.frame.MainFrame;
import main.java.com.tetrismultiplayer.server.gui.panel.MainPanel;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;


public class Main implements Runnable {
    private MainFrame mainFrame;
    private EntityManagerFactory entityManagerFactory;
    private MainPanel mainPanel;
    private MainServerThread mainServerThread;

    private Main() {
        this.mainFrame = new MainFrame(this);
        this.mainPanel = mainFrame.getMainPanel();
    }

    public static void main(String args[]) {
        new Main().run();
    }

    @Override
    public void run() {
        mainFrame.setVisible(true);
        mainPanel.setStartBtnStatus(true);
        mainPanel.setRankingBtnStatus(true);
        connectToDB();
    }

    private void connectToDB() {
        ConnectToDB task = new ConnectToDB(mainPanel);
        task.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (task.isDone()) {
                    try {
                        entityManagerFactory = task.get();
                        if (entityManagerFactory != null) {
                            mainPanel.setStartBtnStatus(true);
                            mainPanel.setRankingBtnStatus(true);
                            mainPanel.writeLineInTextArea("Polaczenie z baza danych zostalo nawiazane.");
                        } else {
                            mainPanel.writeLineInTextArea("Podczas laczenia z baza danych wystapil blad,"
                                    + " zrestartuj program aby sprobowac ponownie.");
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        mainPanel.writeLineInTextArea("Podczas laczenia z baza wystapil blad.");
                        e.printStackTrace();
                    }
                }
            }
        });
        mainPanel.writeLineInTextArea("Trwa laczenie z baza danych.");
        task.execute();
    }

    public MainServerThread getMainServerThread() {
        return mainServerThread;
    }

    public void setMainServerThread(MainServerThread mainServerThread) {
        this.mainServerThread = mainServerThread;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
