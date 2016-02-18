package main.java.com.tetrismultiplayer.server.gui.actionlistener;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.gui.frame.ConnectionsFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionsActionListener implements ActionListener
{

    private Main main;

    public ConnectionsActionListener(Main main)
    {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ConnectionsFrame frame = new ConnectionsFrame(main);
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}