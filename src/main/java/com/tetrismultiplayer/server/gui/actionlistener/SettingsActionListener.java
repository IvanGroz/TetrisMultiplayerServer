package main.java.com.tetrismultiplayer.server.gui.actionlistener;

import main.java.com.tetrismultiplayer.server.gui.frame.SettingsFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
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
}
