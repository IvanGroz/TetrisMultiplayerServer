package main.java.com.tetrismultiplayer.server.gui.panel;

import main.java.com.tetrismultiplayer.server.gui.actionlistener.*;
import main.java.com.tetrismultiplayer.server.Main;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Server application main gui panel class.
 *
 * @author Marcin
 *
 */
public class MainPanel extends JPanel
{
    private static final long serialVersionUID = 7747975451919372923L;

    private EntityManagerFactory entityManagerFactory;
    private Main main;

    private JTextField ipTextField;
    private JTextField portTextField;
    private JPanel leftPanel;
    private JButton btnSettings;
    private JButton btnStart;
    private JButton btnStop;
    private JButton btnRanking;
    private JSeparator separator_1;
    private JLabel lblServerIp;
    private JLabel lblPort;
    private JLabel lblStatistics;
    private JSeparator separator_2;
    private JTextArea textArea;
    private JLabel lblActive;
    private JLabel lblAll;
    private JTextField activePlayersTextField;
    private JLabel lblActivePlayersNumber;
    private JLabel lblActiveGamesNumber;
    private JLabel lblAllPlayersNumber;
    private JLabel lblFinishedGames;
    private JTextField activeGamesTextField;
    private JTextField allPlayersTextField;
    private JTextField allGamesTextField;
    private JScrollPane scroll;
    private JButton btnConnections;
    private ReentrantLock textAreaLock;

    /**
     * Constructor of MainPanel class creating main panel and initializing all elements.
     */
    public MainPanel(Main main)
    {
        this.main = main;
        this.textAreaLock = new ReentrantLock();
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        leftPanel = new JPanel();

        btnSettings = new JButton("Ustawienia");
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnRanking = new JButton("Ranking");
        btnConnections = new JButton("Po\u0142\u0105czenia");

        textArea = new JTextArea();
        scroll = new JScrollPane(textArea);

        ipTextField = new JTextField();
        portTextField = new JTextField();
        activePlayersTextField = new JTextField();
        activeGamesTextField = new JTextField();
        allPlayersTextField = new JTextField();
        allGamesTextField = new JTextField();

        separator_1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator_2 = new JSeparator(SwingConstants.HORIZONTAL);
        lblServerIp = new JLabel("IP serwera:");
        lblPort = new JLabel("Port:");
        lblStatistics = new JLabel("Statystyki:");
        lblActive = new JLabel("Aktualne:");
        lblAll = new JLabel("Og\u00F3lne:");
        lblActivePlayersNumber = new JLabel("Ilo\u015B\u0107 graczy:");
        lblActiveGamesNumber = new JLabel("Trwaj\u0105ce gry:");
        lblAllPlayersNumber = new JLabel("Ilo\u015B\u0107 graczy:");
        lblFinishedGames = new JLabel("Zako\u0144czone gry:");

        setElementsSettings();
        addElements();
        setActionListeners();
    }

    /**
     * Method adding all elements to main panel.
     */
    private void addElements()
    {
        add(leftPanel);
        add(scroll);

        leftPanel.add(btnSettings);
        leftPanel.add(btnStart);
        leftPanel.add(btnStop);
        leftPanel.add(btnRanking);
        leftPanel.add(btnConnections);

        leftPanel.add(ipTextField);
        leftPanel.add(portTextField);
        leftPanel.add(activeGamesTextField);
        leftPanel.add(allPlayersTextField);
        leftPanel.add(allGamesTextField);
        leftPanel.add(activePlayersTextField);

        leftPanel.add(separator_2);
        leftPanel.add(separator_1);

        leftPanel.add(lblServerIp);
        leftPanel.add(lblPort);
        leftPanel.add(lblStatistics);
        leftPanel.add(lblActive);
        leftPanel.add(lblAll);
        leftPanel.add(lblActivePlayersNumber);
        leftPanel.add(lblActiveGamesNumber);
        leftPanel.add(lblAllPlayersNumber);
        leftPanel.add(lblFinishedGames);
    }

    /**
     * Method adding action listeners.
     */
    private void setActionListeners()
    {
        btnSettings.addActionListener(new SettingsActionListener());
        btnStart.addActionListener(new StartActionListener(main));
        btnStop.addActionListener(new StopActionListener(main));
        btnRanking.addActionListener(new RankingActionListener());
        btnConnections.addActionListener(new ConnectionsActionListener());
    }

    /**
     * Method maintaining settings of all elements.
     */
    private void setElementsSettings()
    {
        leftPanel.setBounds(10, 11, 292, 415);
        leftPanel.setLayout(null);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        textArea.setBounds(312, 11, 405, 415);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(312, 11, 415, 415);

        btnSettings.setBounds(23, 11, 97, 30);
        btnSettings.setBackground(Color.WHITE);

        btnStart.setBounds(23, 99, 108, 30);
        btnStart.setBackground(Color.WHITE);
        btnStart.setEnabled(false);

        btnStop.setBounds(162, 99, 108, 30);
        btnStop.setBackground(Color.WHITE);
        btnStop.setEnabled(false);

        btnRanking.setBounds(173, 11, 97, 30);
        btnRanking.setBackground(Color.WHITE);
        btnRanking.setEnabled(false);

        btnConnections.setBounds(100, 64, 97, 30);
        btnConnections.setBackground(Color.WHITE);
        btnConnections.setEnabled(false);

        ipTextField.setBounds(100, 140, 170, 26);
        ipTextField.setColumns(10);
        ipTextField.setEditable(false);
        ipTextField.setBackground(Color.WHITE);
        ipTextField.setHorizontalAlignment(JTextField.CENTER);

        portTextField.setBounds(100, 180, 170, 26);
        portTextField.setColumns(10);
        portTextField.setText("65534");
        portTextField.setHorizontalAlignment(JTextField.CENTER);

        activePlayersTextField.setBounds(184, 272, 86, 20);
        activePlayersTextField.setColumns(10);
        activePlayersTextField.setEditable(false);
        activePlayersTextField.setBackground(Color.WHITE);
        activePlayersTextField.setText("0");
        activePlayersTextField.setHorizontalAlignment(JTextField.CENTER);

        activeGamesTextField.setBounds(184, 303, 86, 20);
        activeGamesTextField.setColumns(10);
        activeGamesTextField.setEditable(false);
        activeGamesTextField.setBackground(Color.WHITE);
        activeGamesTextField.setText("0");
        activeGamesTextField.setHorizontalAlignment(JTextField.CENTER);

        allPlayersTextField.setBounds(184, 352, 86, 20);
        allPlayersTextField.setColumns(10);
        allPlayersTextField.setEditable(false);
        allPlayersTextField.setBackground(Color.WHITE);
        allPlayersTextField.setText("0");
        allPlayersTextField.setHorizontalAlignment(JTextField.CENTER);

        allGamesTextField.setBounds(184, 383, 86, 20);
        allGamesTextField.setColumns(10);
        allGamesTextField.setEditable(false);
        allGamesTextField.setBackground(Color.WHITE);
        allGamesTextField.setText("0");
        allGamesTextField.setHorizontalAlignment(JTextField.CENTER);

        separator_1.setBounds(0, 52, 292, 1);
        separator_1.setForeground(Color.BLACK);

        separator_2.setBounds(0, 217, 292, 1);
        separator_2.setForeground(Color.BLACK);

        lblFinishedGames.setBounds(76, 380, 98, 26);
        lblAllPlayersNumber.setBounds(76, 349, 78, 26);
        lblAll.setBounds(23, 330, 54, 26);
        lblActiveGamesNumber.setBounds(76, 298, 78, 30);
        lblActive.setBounds(23, 250, 69, 20);
        lblActivePlayersNumber.setBounds(76, 269, 86, 26);
        lblServerIp.setBounds(23, 143, 69, 20);
        lblPort.setBounds(23, 183, 67, 20);
        lblStatistics.setBounds(122, 225, 108, 20);
    }

    /**
     * Writes new line of text in text area.
     *
     * @param newLine text to be written
     */
    public void writeLineInTextArea(String newLine)
    {
        try
        {
            textAreaLock.tryLock(3, TimeUnit.SECONDS);
            if (!textArea.getText().isEmpty())
            {
                newLine = "\n" + newLine;
            }
            textArea.append(newLine);
            textAreaLock.unlock();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sets active players number text field value.
     *
     * @param activePlayersNumber
     */
    public void setActivePlayersNumber(Integer activePlayersNumber)
    {
        synchronized(activePlayersTextField)
        {
            activePlayersTextField.setText(activePlayersNumber.toString());
        }
    }

    /**
     * Sets active games number text field value.
     *
     * @param activeGamesNumber
     */
    public void setActiveGamesNumber(Integer activeGamesNumber)
    {
        synchronized (activeGamesTextField)
        {
            activeGamesTextField.setText(activeGamesNumber.toString());
        }
    }

    /**
     * Sets all players number text field value.
     *
     * @param allPlayersNumber
     */
    public void setAllPlayersNumber(String allPlayersNumber)
    {
        allPlayersTextField.setText(allPlayersNumber);
    }

    /**
     * Sets all games number text field value.
     *
     * @param allGamesNumber
     */
    public void setAllGamesNumber(String allGamesNumber)
    {
        allGamesTextField.setText(allGamesNumber);
    }

    /**
     * Sets start button enabled value to selected state.
     *
     * @param isActive true if button should be enabled.
     */
    public void setStartBtnStatus(boolean isActive)
    {
        btnStart.setEnabled(isActive);
    }

    /**
     * Sets stop button enabled value to selected state.
     *
     * @param isActive true if button should be enabled.
     */
    public void setStopBtnStatus(boolean isActive)
    {
        btnStop.setEnabled(isActive);
    }

    /**
     * Sets ranking button enabled value to selected state.
     *
     * @param isActive true if button should be enabled.
     */
    public void setRankingBtnStatus(boolean isActive)
    {
        btnRanking.setEnabled(isActive);
    }

    /**
     * Sets settings button enabled value to selected state.
     *
     * @param isActive true if button should be enabled.
     */
    public void setSettingsBtnStatus(boolean isActive)
    {
        btnSettings.setEnabled(isActive);
    }

    /**
     * Sets connections button enabled value to selected state.
     *
     * @param isActive true if button should be enabled.
     */
    public void setConnectionsBtnStatus(boolean isActive)
    {
        btnConnections.setEnabled(isActive);
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }

    public JTextField getIpTextField()
    {
        return ipTextField;
    }

    public JTextField getPortTextField()
    {
        return portTextField;
    }

    public JTextField getActivePlayersTextField()
    {
        return activePlayersTextField;
    }

    public JTextField getActiveGamesTextField()
    {
        return activeGamesTextField;
    }

    public JTextField getAllPlayersTextField()
    {
        return allPlayersTextField;
    }

    public JTextField getAllGamesTextField()
    {
        return allGamesTextField;
    }
}