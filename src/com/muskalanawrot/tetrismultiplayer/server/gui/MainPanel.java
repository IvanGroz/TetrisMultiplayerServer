package com.muskalanawrot.tetrismultiplayer.server.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Main server application panel class.
 * 
 * @author Marcin
 *
 */
public class MainPanel extends JPanel
{
    private static final long serialVersionUID = 7747975451919372923L;

    private JTextField textField;
    private JTextField textField_1;
    private JPanel panel;
    private JButton btnUstawienia;
    private JButton btnStart;
    private JButton btnStop;
    private JButton btnRanking;
    private PlayersPanel playersPanel;
    private JSeparator separator_1;
    private JLabel lblKonfiguracja;
    private JLabel lblIpSerwera;
    private JLabel lblPort;
    private JLabel lblPoczeniGracze;
    private JSeparator separator_2;
    private JTextArea textArea;

    /**
     * Create the panel.
     */
    public MainPanel()
    {
	setLayout(null);
	setBackground(Color.DARK_GRAY);

	panel = new JPanel();
	btnUstawienia = new JButton("Ustawienia");
	btnStart = new JButton("Start");
	btnStop = new JButton("Stop");
	textField = new JTextField();
	textField_1 = new JTextField();
	btnRanking = new JButton("Ranking");
	playersPanel = new PlayersPanel();
	separator_1 = new JSeparator(SwingConstants.HORIZONTAL);
	lblKonfiguracja = new JLabel("Konfiguracja serwera:");
	lblIpSerwera = new JLabel("IP serwera:");
	lblPort = new JLabel("Port:");
	lblPoczeniGracze = new JLabel("Po\u0142\u0105czeni gracze:");
	separator_2 = new JSeparator(SwingConstants.HORIZONTAL);
	textArea = new JTextArea();

	initializeElements();
	addElements();
    }

    public void addElements()
    {
	add(panel);
	add(textArea);

	panel.add(btnUstawienia);
	panel.add(btnStart);
	panel.add(btnStop);
	panel.add(textField);
	textField.setColumns(10);
	panel.add(textField_1);
	textField_1.setColumns(10);
	panel.add(btnRanking);
	panel.add(playersPanel);
	panel.add(separator_1);
	panel.add(lblKonfiguracja);
	panel.add(lblIpSerwera);
	panel.add(lblPort);
	panel.add(lblPoczeniGracze);
	panel.add(separator_2);
    }

    public void initializeElements()
    {
	panel.setBounds(10, 11, 292, 443);
	panel.setLayout(null);
	panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	textArea.setBounds(312, 11, 463, 443);
	textArea.setEditable(false);

	btnUstawienia.setBounds(23, 11, 108, 30);
	btnUstawienia.setBackground(Color.WHITE);
	
	btnStart.setBounds(23, 90, 108, 30);
	btnStart.setBackground(Color.WHITE);
	
	btnStop.setBounds(162, 90, 108, 30);
	btnStop.setBackground(Color.WHITE);
	
	btnRanking.setBounds(162, 11, 108, 30);
	btnRanking.setBackground(Color.WHITE);

	textField.setBounds(100, 140, 170, 26);
	textField.setColumns(10);
	textField.setEditable(false);
	textField.setBackground(Color.WHITE);
	textField_1.setBounds(100, 180, 170, 26);
	textField_1.setColumns(10);

	playersPanel.setBounds(10, 248, 272, 184);

	separator_1.setBounds(0, 52, 292, 1);
	separator_1.setForeground(Color.BLACK);
	separator_2.setBounds(0, 217, 292, 1);
	separator_2.setForeground(Color.BLACK);

	lblKonfiguracja.setBounds(91, 55, 127, 27);
	lblIpSerwera.setBounds(23, 143, 69, 20);
	lblPort.setBounds(23, 183, 67, 20);
	lblPoczeniGracze.setBounds(110, 223, 108, 20);
    }
}
