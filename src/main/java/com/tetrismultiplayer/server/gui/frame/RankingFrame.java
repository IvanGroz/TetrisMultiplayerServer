package main.java.com.tetrismultiplayer.server.gui.frame;

import main.java.com.tetrismultiplayer.server.gui.panel.ranking.RankingTabbedPanel;

import javax.swing.*;

/**
 * Ranking rame class.
 */
public class RankingFrame extends JFrame
{
    private static final long serialVersionUID = -8209794248344578503L;

    private RankingTabbedPanel rankingPanel;

    /**
     * Create the frame.
     */
    public RankingFrame()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Ranking graczy");
        rankingPanel = new RankingTabbedPanel();
        setContentPane(rankingPanel);
        setSize(500,400);
        setLocationRelativeTo(null);
    }

}
