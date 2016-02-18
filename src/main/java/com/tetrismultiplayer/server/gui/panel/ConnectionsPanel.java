package main.java.com.tetrismultiplayer.server.gui.panel;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class ConnectionsPanel extends JPanel
{
    private JTable connectionsTable;
    private LinkedList<RemoteUser> usersList;

    public ConnectionsPanel(Main main)
    {
        this.usersList = main.getMainServerThread().getUsersList();

        setLayout(new BorderLayout());
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("Nick");
        columnNames.add("Ip");
        columnNames.add("Port");
        columnNames.add("Status");

        Vector<Object> data = new Vector<>();
        for (int i = 0; i < usersList.size(); i++)
        {
            Vector<Object> row = new Vector<>();
            row.add(usersList.get(i).getNick());
            row.addAll(Arrays.asList(usersList.get(i).getIp().split(":")));
            row.add(usersList.get(i).getStatus());
            data.add(row);
        }

        connectionsTable = new JTable(data, columnNames)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(connectionsTable);
        connectionsTable.setFillsViewportHeight(true);
        add(scrollPane);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer()
        {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column)
        {
            if (isSelected)
            {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            }
            else
            {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
}
