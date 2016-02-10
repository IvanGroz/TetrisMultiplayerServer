package com.muskalanawrot.tetrismultiplayer.server.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayersPanel extends JPanel
{
	private JTextField textField;
	private JTextField textField_1;

    /**
     * Create the panel.
     */
    public PlayersPanel()
    {
    	setLayout(null);
    	
    	textField = new JTextField();
    	textField.setBounds(20, 31, 86, 20);
    	add(textField);
    	textField.setColumns(10);
    	
    	textField_1 = new JTextField();
    	textField_1.setBounds(145, 31, 86, 20);
    	add(textField_1);
    	textField_1.setColumns(10);

    }
}
