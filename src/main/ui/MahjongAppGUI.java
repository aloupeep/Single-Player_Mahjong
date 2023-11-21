package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// Represents the panel for the hand display and discard portion of the GUI
// The design of this GUI took inspiration from the ListDemo project from the java tutorial series that can be found at
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ and the C3 Lecture Lab starter regarding traffic
// lights that can be found at https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.
public class MahjongAppGUI extends JPanel implements ListSelectionListener {
    private Hand hand;
    private JList handList;
    private DefaultListModel handModel;

    private DiscardGUI discardGUI;

    private JButton discardButton;
    private JScrollPane handListScrollPane;

    // Effects: initializes the panel for displaying the current hand
    public MahjongAppGUI() {
        super(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        discardGUI = new DiscardGUI();

        createAppGUI();
        setVisible(true);
    }

    // Modifies: this
    // Effects: initializes the hand for display
    private void createAppGUI() {
        hand = new Hand();
        hand.drawAndSort();
        handModel = new DefaultListModel<>();
        handModel.addAll(hand.getHandString());

        setupVisuals(false);
        addButtons();
    }

    // Modifies: this
    // Effects: sets up handList and the scroll pane for selection of tile in hand
    //          resets the current scroll pane if shouldReset is true; does not reset otherwise
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

    // Modifies: this
    // Effects: initializes and adds buttons the to the panel
    private void addButtons() {
        discardButton = new JButton("Discard");
        discardButton.setActionCommand("discard");
        discardButton.addActionListener(new MahjongAppGUI.DiscardListener());

        add(discardButton, BorderLayout.SOUTH);
    }

    // Modifies: this
    // Effects: loads the given hand and discard into the current GUI;
    //          in other words, replace the current hand and discards with the new ones given
    public void loadGameGUI(Hand hand, DiscardedTiles discards) {
        this.hand = hand;
        this.handModel = new DefaultListModel<>();
        this.handList = new JList<>(handModel);
        handModel.addAll(this.hand.getHandString());
        discardGUI.loadDiscards(discards);
        setupVisuals(true);
        addButtons();
        repaint();
    }

    // Modifies: this
    // Effects: enables the discard button if value is changed to a valid index (not -1)
    //          disables discard button otherwise
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (handList.getSelectedIndex() == -1) {
                //No selection, disable discard button.
                discardButton.setEnabled(false);

            } else {
                //Selection, enable the discard button.
                discardButton.setEnabled(true);
            }
        }
    }

    public DiscardGUI getDiscardGUI() {
        return discardGUI;
    }

    public Hand getHand() {
        return this.hand;
    }

    // Represents ActionListener for the discard button
    class DiscardListener implements ActionListener {

        // Requires: handList.getSelectedIndex() is not -1
        // Modifies: this
        // Effects: removes the selected tile from hand and adjusts the new selected index
        //          if necessary; then draws a new tile to simulate a new turn beginning;
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

            if (index == size) {
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

