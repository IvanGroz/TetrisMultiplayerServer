package main.java.com.tetrismultiplayer.server.gui.frame;

import main.java.com.tetrismultiplayer.server.gui.panel.SettingsPanel;

import javax.swing.*;

/**
 * Settings frame class.
 */
public class SettingsFrame extends JFrame
{
    private static final long serialVersionUID = -5099033041385494292L;

    private SettingsPanel settingsPanel;

    /**
     * Create the frame.
     */
    public SettingsFrame()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Ustawienia");
        settingsPanel = new SettingsPanel();
        setContentPane(settingsPanel);
        setSize(500,400);
        setLocationRelativeTo(null);
    }

}
