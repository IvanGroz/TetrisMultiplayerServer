package main.java.com.tetrismultiplayer.server.gui.panel.ranking;

import javax.swing.JTabbedPane;

/**
 * Class for ranking panel with tabs.
 */
public class RankingTabbedPanel extends JTabbedPane
{
    private static final long serialVersionUID = 1327792069279300334L;

    /**
     * Create ranking tabbed panel.
     */
    public RankingTabbedPanel()
    {
	super();

	addTab("Gra pojedyńcza", new SinglePlayerRankingPanel());

	addTab("Gra wspólna", new CooperationRankingPanel());

	addTab("Gra przeciwko", new CompetitionRankingPanel());
    }
}
