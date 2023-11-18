package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// The design of this GUI took inspiration from the ListDemo project from the java tutorial series that can be found at
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ and the C3 Lecture Lab starter regarding traffic
// lights that can be found at https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.
public class PersistenceGUI extends JPanel {
    protected static final String JSON_STORE = "./data/mahjongGame.json";

    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    private MahjongAppGUI currentGUI;
    private MahjongAppFrameGUI currentFrameGUI;
    private JButton saveButton;
    private JButton loadButton;

    // Effects: initializes the GUI for functionality relating to persistence
    public PersistenceGUI(MahjongAppFrameGUI currentFrameGUI, MahjongAppGUI appGUI) throws FileNotFoundException {
        super(new BorderLayout());
        this.currentFrameGUI = currentFrameGUI;
        this.currentGUI = appGUI;

        saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        setupVisuals();
        jsonInitialize();
        setVisible(true);
    }

    private void setupVisuals() {

        add(saveButton, BorderLayout.LINE_START);
        add(loadButton, BorderLayout.LINE_END);
    }

    // EFFECTS: saves the current game to file
    public void saveGame() {
        try {
            Hand currentHand = currentGUI.getHand();
            DiscardedTiles currentDiscard = currentGUI.getDiscardGUI().getDiscards();
            jsonWriter.open();
            jsonWriter.write(currentHand, currentDiscard);
            jsonWriter.close();
            System.out.println("Saved current game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    public void loadGame() {
        try {
            Hand newHand = jsonReader.readHand();
            DiscardedTiles newDiscards = jsonReader.readDiscards();

            currentFrameGUI.reload(newHand, newDiscards);
            System.out.println("Loaded previous game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Modifies: this
    // Effects: initializes Json reader and writer
    void jsonInitialize() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }
}
