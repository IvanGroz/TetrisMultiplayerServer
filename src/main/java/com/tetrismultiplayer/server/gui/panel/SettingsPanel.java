package main.java.com.tetrismultiplayer.server.gui.panel;

import main.java.com.tetrismultiplayer.server.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel showing server settings and saving it.
 */
public class SettingsPanel extends JPanel
{
    private JLabel labelMaxGames, labelMaxUsers;
    private JTextField textMaxGames, textMaxUsers;
    private JButton saveChanges;

    public SettingsPanel()
    {
        setLayout(null);

        labelMaxGames = new JLabel("Maksymalna ilość gier:");
        labelMaxGames.setBounds(80, 50, 150, 20);

        textMaxGames = new JTextField();
        textMaxGames.setText(Main.maxGames.toString());
        textMaxGames.setBounds(240, 50, 100, 25);

        labelMaxUsers = new JLabel("Maksymalna ilość graczy:");
        labelMaxUsers.setBounds(80, 100, 150, 20);

        textMaxUsers = new JTextField();
        textMaxUsers.setText(Main.maxUsers.toString());
        textMaxUsers.setBounds(240, 100, 100, 25);

        saveChanges = new JButton("Zapisz");
        saveChanges.setBounds(240, 150, 100, 25);
        saveChanges.setBackground(Color.WHITE);
        saveChanges.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setValuesFromFields();
                SwingUtilities.getWindowAncestor(getParent()).dispose();
            }
        });
        addComponents();
    }

    public void addComponents()
    {
        add(labelMaxGames);
        add(textMaxGames);
        add(labelMaxUsers);
        add(textMaxUsers);
        add(saveChanges);
    }

    public void setValuesFromFields()
    {
        Main.maxGames = Integer.parseInt(textMaxGames.getText());
        Main.maxUsers = Integer.parseInt(textMaxUsers.getText());
    }
}
