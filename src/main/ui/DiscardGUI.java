package ui;

import model.DiscardedTiles;
import model.Hand;
import model.Tile;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiscardGUI extends JPanel implements ListSelectionListener {
    private DiscardedTiles discards;
    private JList discardList;
    private DefaultListModel discardsModel;
    private JScrollPane discardListScrollPane;

    private JButton removeDiscardButton;

    public DiscardGUI() {
        super(new BorderLayout());

        // Create the GUI for discards
        createAppGUI();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(true);
    }

    public void loadDiscards(DiscardedTiles discards) {
        this.discards = discards;
        discardsModel = new DefaultListModel<>();
        discardsModel.addAll(discards.getDiscardedTilesString());
        setupVisuals(true);
        addButtons();
    }


    private void createAppGUI() {
        discards = new DiscardedTiles();
        discardsModel = new DefaultListModel<>();

        setupVisuals(false);
        addButtons();
    }

    private void setupVisuals(boolean shouldReset) {

        discardList = new JList(discardsModel);
        discardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discardList.setSelectedIndex(0);
        discardList.addListSelectionListener(this);
        discardList.setLayoutOrientation(2);
        discardList.setVisibleRowCount(1);
        discardListScrollPane = new JScrollPane(discardList);
        if (shouldReset) {
            this.removeAll();
        }
        add(discardListScrollPane, BorderLayout.CENTER);

    }

    private void addButtons() {
        removeDiscardButton = new JButton("Remove discard");
        removeDiscardButton.setActionCommand("remove discard");
        removeDiscardButton.addActionListener(new DiscardGUI.RemoveDiscardListener());
        removeDiscardButton.setEnabled(false);

        add(removeDiscardButton, BorderLayout.SOUTH);
    }

    public void addDiscard(Tile tile) {
        discardsModel.addElement(tile.showTile());
        discards.addTile(tile);
    }

    public DiscardedTiles getDiscards() {
        return this.discards;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (discardList.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeDiscardButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeDiscardButton.setEnabled(true);
            }
        }
    }

    class RemoveDiscardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = discardList.getSelectedIndex();
            if (discardsModel.remove(index) != null) {
                discards.removeDiscardIndex(index);

            }
            int size = discardsModel.getSize();

            if (index == discardsModel.getSize()) {
                //removed item in last position
                index--;
            }
            discardList.setSelectedIndex(index);
            discardList.ensureIndexIsVisible(index);
        }
    }
}

