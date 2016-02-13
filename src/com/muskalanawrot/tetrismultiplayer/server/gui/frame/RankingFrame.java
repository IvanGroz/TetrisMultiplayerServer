package com.muskalanawrot.tetrismultiplayer.server.gui.frame;

import javax.swing.JFrame;

import com.muskalanawrot.tetrismultiplayer.server.gui.panel.ranking.RankingTabbedPanel;

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
