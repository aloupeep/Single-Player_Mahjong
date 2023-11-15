package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

public class MahjongAppGUI extends JPanel implements ListSelectionListener {
    private static final String STATUS_OK = "Nothing wrong yet";
    private Hand hand;

    private JList handList;
    private DefaultListModel handModel;

    private DiscardGUI discardGUI;

    private JButton discardButton;
    private MahjongApp mahjongApp;
    private JLabel statusLabel;
    private JLabel discardsVisual;
    private JScrollPane handListScrollPane;

    public MahjongAppGUI() {
        super(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        discardGUI = new DiscardGUI();

        // setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);

        // Create the intersection and GUI for intersection
        createAppGUI();
        // pack();
        setVisible(true);
    }

    private void createAppGUI() {
        hand = new Hand();
        hand.drawAndSort();
        handModel = new DefaultListModel<>();
        handModel.addAll(hand.getHandString());
        /*for (Tile t : hand.getHand()) {
            String tileString = t.showTile();
            handModel.addElement(tileString);
        }*/
        System.out.println(handModel);

        setupVisuals(false);
        addButtons();
    }

    private void setupVisuals(boolean shouldReset) {
        handList = new JList(handModel);
        handList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        handList.setSelectedIndex(0);
        handList.addListSelectionListener(this);
        handList.setLayoutOrientation(2);
        handList.setVisibleRowCount(1);
        handListScrollPane = new JScrollPane(handList);

        if (shouldReset) {
            this.removeAll();

        }
        add(handListScrollPane, BorderLayout.CENTER);

    }

    private void addButtons() {
        discardButton = new JButton("Discard");
        discardButton.setActionCommand("discard");
        discardButton.addActionListener(new MahjongAppGUI.DiscardListener());

        add(discardButton, BorderLayout.SOUTH);
    }



    public void loadGameGUI(Hand hand, DiscardedTiles discards) {
        System.out.println("Inside APPGUI" + this.hand.getHandString());
        this.hand = hand;
        this.handModel = new DefaultListModel<>();
        this.handList = new JList<>(handModel);
        handModel.addAll(this.hand.getHandString());
        discardGUI.loadDiscards(discards);
        System.out.println("Inside APPGUI" + this.handModel.toString());
        setupVisuals(true);
        addButtons();
        repaint();
    }

    public Hand getHand() {
        return this.hand;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (handList.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                discardButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                discardButton.setEnabled(true);
            }
        }
    }

    public DiscardGUI getDiscardGUI() {
        return discardGUI;
    }

    class DiscardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = handList.getSelectedIndex();
            if (handModel.remove(index) != null) {
                Tile discard = hand.discardTileIndex(index);
                discardGUI.addDiscard(discard);
            }
            int size = handModel.getSize();

            if (index == handModel.getSize()) {
                //removed item in last position
                index--;
            }
            hand.drawAndSort();
            handModel.removeAllElements();
            handModel.addAll(hand.getHandString());

            handList.setSelectedIndex(index);
            handList.ensureIndexIsVisible(index);
        }
    }
}

