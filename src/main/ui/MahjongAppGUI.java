package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.*;

public class MahjongAppGUI extends JPanel implements ListSelectionListener {
    private static final String STATUS_OK = "Nothing wrong yet";
    private Hand hand;
    private DiscardedTiles discards;
    private JList handList;
    private DefaultListModel handModel;
    private JList discardList;
    private DefaultListModel discardsModel;

    private JButton discardButton;

    private MahjongApp mahjongApp;
    private JLabel statusLabel;
    private JLabel discardsVisual;

    public MahjongAppGUI() {
        super(new BorderLayout());

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

        discards = new DiscardedTiles();
        discardsModel = new DefaultListModel<>();

        setupVisuals();
        addButtons();
    }

    private void setupVisuals() {
        handList = new JList(handModel);
        handList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        handList.setSelectedIndex(0);
        handList.addListSelectionListener(this);
        handList.setLayoutOrientation(2);
        handList.setVisibleRowCount(1);
        JScrollPane handListScrollPane = new JScrollPane(handList);

        discardList = new JList(discardsModel);
        discardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discardList.setSelectedIndex(0);
        discardList.addListSelectionListener(this);
        discardList.setLayoutOrientation(2);
        discardList.setVisibleRowCount(1);
        JScrollPane discardListScrollPane = new JScrollPane(discardList);
        discardsVisual = new JLabel(discards.getDiscardedTilesString());

        add(handListScrollPane, BorderLayout.CENTER);
        // add(discardsVisual);
        // DOES NOT WORK IF UNCOMMENT NEXT LINE FOR SOME REASONS
        // add(discardListScrollPane, BorderLayout.CENTER);
    }

    private void addButtons() {
        discardButton = new JButton("Discard");
        discardButton.setActionCommand("discard");
        discardButton.addActionListener(new MahjongAppGUI.DiscardListener());

        add(discardButton, BorderLayout.SOUTH);
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

    class DiscardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = handList.getSelectedIndex();
            if (handModel.remove(index) != null) {
                Tile discard = hand.discardTileIndex(index);
                discards.addTile(discard);
                discardsVisual = new JLabel(discards.getDiscardedTilesString());
                discardsModel.addElement(discard.showTile());
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

