package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MahjongAppFrameGUI extends JFrame {
    private static final String STATUS_OK = "Nothing wrong yet";
    private JLabel statusLabel;
    private MahjongAppGUI appGUI;
    private DiscardGUI discardGUI;
    private PersistenceGUI persistenceButtonPane;

    public MahjongAppFrameGUI() {
        super("Single-player mahjong");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);

        appGUI = new MahjongAppGUI();
        discardGUI = appGUI.getDiscardGUI();
        try {
            persistenceButtonPane = new PersistenceGUI(this, appGUI);
        } catch (IOException e) {
            System.out.println("something went wrong in the initialization of json reader + writer...");
        }
        // appGUI.setOpaque(true); //content panes must be opaque
        Container container = getContentPane();

        container.add(appGUI,BorderLayout.PAGE_START);
        container.add(discardGUI,BorderLayout.CENTER);
        container.add(persistenceButtonPane,BorderLayout.PAGE_END);


        //Display the window.
        pack();
        setVisible(true);
    }

    public void reload(Hand hand, DiscardedTiles discards) {
        appGUI.loadGameGUI(hand, discards);

        //Display the window.
        pack();
        setVisible(true);
    }
}
