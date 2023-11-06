package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import model.*;

public class MahjongAppGUI extends JFrame implements ListSelectionListener {
    private static final String STATUS_OK = "";
    private Hand hand;
    private MahjongApp mahjongApp;
    private JLabel statusLabel;

    public MahjongAppGUI() {
        super("Single-player Mahjong");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
