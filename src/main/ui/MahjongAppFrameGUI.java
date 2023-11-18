package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// The design of this GUI took inspiration from the ListDemo project from the java tutorial series that can be found at
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ and the C3 Lecture Lab starter regarding traffic
// lights that can be found at https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.
public class MahjongAppFrameGUI extends JFrame {
    private static final String STATUS_OK = "Nothing wrong yet";
    private JLabel statusLabel;
    private MahjongAppGUI appGUI;
    private DiscardGUI discardGUI;
    private JButton checkWinButton;
    private PersistenceGUI persistenceButtonPane;
    private ImageIcon winImage;
    private ImageIcon failImage;

    // Effects: initializes the frame for the GUI
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
        initializeImage();

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

    // Modifies: this
    // Effects: resets the current hand and discarded tile
    public void reload(Hand hand, DiscardedTiles discards) {
        appGUI.loadGameGUI(hand, discards);

        //Display the window.
        pack();
        setVisible(true);
    }

    // Modifies: this
    // Effects: initializes the declare win button's appearance and effects
    private void initializeWin() {
        HandChecker handChecker = new HandChecker();
        checkWinButton = new JButton("Declare Win");
        checkWinButton.setActionCommand("declare win");
        checkWinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (handChecker.declareWin(appGUI.getHand())) {
                    remove(statusLabel);
                    statusLabel = new JLabel(winImage);
                    add(statusLabel, BorderLayout.BEFORE_LINE_BEGINS);

                } else {
                    remove(statusLabel);
                    statusLabel = new JLabel(failImage);
                    add(statusLabel, BorderLayout.BEFORE_LINE_BEGINS);
                }
                repaint();
            }
        });
    }

    // Modifies: this
    // Effects: initializes the image fields with the correct images
    private void initializeImage() {
        String sep = System.getProperty("file.separator");
        winImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Win Image.png");
        failImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Fail Image.png");
    }
}
