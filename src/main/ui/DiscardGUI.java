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

// Represents the panel showing the current discards and deals with removing discards
// The design of this GUI took inspiration from the ListDemo project from the java tutorial series that can be found at
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ and the C3 Lecture Lab starter regarding traffic
// lights that can be found at https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.
public class DiscardGUI extends JPanel implements ListSelectionListener {
    private DiscardedTiles discards;
    private JList discardList;
    private DefaultListModel discardsModel;
    private JScrollPane discardListScrollPane;

    private JButton removeDiscardButton;

    // Effects: Initializes a new discard GUI with no discards
    public DiscardGUI() {
        super(new BorderLayout());

        // Create the GUI for discards
        createAppGUI();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(true);
    }

    // Modifies: this
    // Effects: replaces the current discarded tiles displayed with the ones given
    public void loadDiscards(DiscardedTiles discards) {
        this.discards = discards;
        discardsModel = new DefaultListModel<>();
        discardsModel.addAll(discards.getDiscardedTilesString());
        setupVisuals(true);
        addButtons();
    }

    // Modifies: this
    // Effects: initializes the discards and adds the buttons
    private void createAppGUI() {
        discards = new DiscardedTiles();
        discardsModel = new DefaultListModel<>();

        setupVisuals(false);
        addButtons();
    }

    // Modifies: this
    // Effects: if shouldReset, remove the current display and replace it with an updated display
    // otherwise, adds the new scroll pane to the display
    private void setupVisuals(boolean shouldReset) {

        discardList = new JList(discardsModel);
        discardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discardList.setSelectedIndex(0);
        discardList.addListSelectionListener(this);
        discardList.setLayoutOrientation(2);
        discardListScrollPane = new JScrollPane(discardList);
        if (shouldReset) {
            this.removeAll();
        }
        add(discardListScrollPane, BorderLayout.CENTER);

    }

    // Modifies: this
    // Effects: initializes and adds the remove discard button
    private void addButtons() {
        removeDiscardButton = new JButton("Remove discard");
        removeDiscardButton.setActionCommand("remove discard");
        removeDiscardButton.addActionListener(new DiscardGUI.RemoveDiscardListener());
        removeDiscardButton.setEnabled(false);

        add(removeDiscardButton, BorderLayout.SOUTH);
    }

    // Modifies: this
    // Effects: adds the given tile to the current discards; updates display accordingly
    public void addDiscard(Tile tile) {
        discardsModel.addElement(tile.showTile());
        discards.addTile(tile);
    }

    public DiscardedTiles getDiscards() {
        return this.discards;
    }

    // Modifies: this
    // Effects: enables the remove discard button if value is changed to a valid index (not -1)
    //          disables remove discard button otherwise
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

    // Represents ActionListener for the remove discard button
    class RemoveDiscardListener implements ActionListener {

        // Requires: discardList.getSelectedIndex() is not -1
        // Modifies: this
        // Effects: removes the selected tile from discards and adjusts the new selected index
        //          if necessary
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = discardList.getSelectedIndex();
            if (discardsModel.remove(index) != null) {
                discards.removeDiscardIndex(index);

            }
            int size = discardsModel.getSize();

            if (index == size) {
                //removed item in last position
                index--;
            }
            discardList.setSelectedIndex(index);
            discardList.ensureIndexIsVisible(index);
        }
    }
}

