package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MahjongAppFrameGUI extends JFrame {
    private static final String STATUS_OK = "Nothing wrong yet";
    private JLabel statusLabel;
    private MahjongAppGUI appGUI;
    private DiscardGUI discardGUI;
    private JButton checkWinButton;
    private PersistenceGUI persistenceButtonPane;

    public MahjongAppFrameGUI() {
        super("Single-player mahjong");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_OK);


        appGUI = new MahjongAppGUI();
        discardGUI = appGUI.getDiscardGUI();
        try {
            persistenceButtonPane = new PersistenceGUI(this, appGUI);
        } catch (IOException e) {
            System.out.println("something went wrong in the initialization of json reader + writer...");
        }
        initializeWin();
        // appGUI.setOpaque(true); //content panes must be opaque
        Container container = getContentPane();


        container.add(appGUI,BorderLayout.NORTH);
        container.add(statusLabel, BorderLayout.BEFORE_LINE_BEGINS);
        container.add(discardGUI,BorderLayout.CENTER);
        container.add(checkWinButton,BorderLayout.LINE_END);
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

    private void initializeWin() {
        HandChecker handChecker = new HandChecker();
        checkWinButton = new JButton("Declare Win");
        checkWinButton.setActionCommand("declare win");
        checkWinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (handChecker.declareWin(appGUI.getHand())) {
                    statusLabel.setText("You won!");
                } else {
                    statusLabel.setText("Unsuccessful declaration of win...");
                    System.out.println("label changed");
                }
                repaint();
            }
        });
    }
}
