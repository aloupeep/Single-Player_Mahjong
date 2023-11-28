package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

// Represents the main frame of the GUI
// The design of this GUI took inspiration from the ListDemo project from the java tutorial series that can be found at
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ and the C3 Lecture Lab starter regarding traffic
// lights that can be found at https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.
public class MahjongAppFrameGUI extends JFrame implements WindowListener {
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
        addWindowListener(this);
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
                statusLabel.setVisible(true);
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

    // Specified by the interface WindowListener
    @Override
    public void windowOpened(WindowEvent e) {
    }

    // Effects: print the event log into the console upon closing the game window
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
    }

    // Specified by the interface WindowListener
    @Override
    public void windowClosed(WindowEvent e) {
    }

    // Specified by the interface WindowListener
    @Override
    public void windowIconified(WindowEvent e) {
    }

    // Specified by the interface WindowListener
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    // Specified by the interface WindowListener
    @Override
    public void windowActivated(WindowEvent e) {
    }

    // Specified by the interface WindowListener
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
