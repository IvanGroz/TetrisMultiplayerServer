package main.java.com.tetrismultiplayer.server.gui.panel;

import main.java.com.tetrismultiplayer.server.Main;
import main.java.com.tetrismultiplayer.server.engine.user.RemoteUser;

import javax.swing.*;
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
}
