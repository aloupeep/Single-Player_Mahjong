package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Represents an ongoing single-player mahjong game
// The use of json objects took inspiration from JsonSerializationDemo document found at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class MahjongApp {
    private static final String JSON_STORE = "./data/mahjongGame.json";
    List<String> handVisual;
    Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    Hand hand;
    List<Tile> handList;
    List<Integer> idList;
    Boolean isPlaying = true;
    DiscardedTiles discards = new DiscardedTiles();
    String tileIdGuide = "1 man = 0, 1 pin = 9, 1 sou = 18, 1 wind = 27, 1 dragon = 31";

    // Requires: User inputs a Boolean
    // Effects: Creates MahjongApp instance and begins new mahjong game
    public MahjongApp() throws FileNotFoundException {
        jsonInitialize();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        hand = new Hand();
        while (isPlaying) {
            handDrawTileAndSort();
            promptInput();
        }
    }

    // Modifies: this
    // Effects: initializes Json reader and writer
    private void jsonInitialize() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // Modifies: this
    // Effects: draws a random tile, sorts the resulting hand, and shows the hand
    private void handDrawTileAndSort() {
        hand.drawTile(new Tile(hand.produceRandomID()));
        hand.sortHandLastTile();
        resetHandList();
        showHand(hand.getHand());
    }

    // Modifies: this
    // Effects: prompts user to input a valid command
    private void promptInput() {
        displayCommands();
        String command = scanner.next();
        resetIdList();
        handleInput(command);
        if (!(isPlaying) || (hand.getHandLength() == 13)) {
            return;
        }
        promptInput();
    }

    // Modifies: this
    // Effects: handles the user's request based on the input
    private void handleInput(String command) {
        if (command.equals("w")) {
            if (declareWin()) {
                System.out.println("Congrats! You completed a hand!");
                isPlaying = false;
            }
        } else if (command.equals("sd")) {
            System.out.println("Discarded Tiles:");
            showHand(discards.getDiscardedTiles());
        } else if (command.equals("h")) {
            System.out.println("Current Hand:");
            showHand(handList);
        } else if (command.equals("d")) {
            discardTileFromHand();
        } else if (command.equals("l")) {
            loadGame();
        } else if (command.equals("e")) {
            exitGame();
        } else {
            System.out.println("Invalid command! Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadGame() {
        try {
            hand = jsonReader.readHand();
            discards = jsonReader.readDiscards();
            resetHandList();
            resetIdList();
            System.out.println("Loaded previous game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the current game or exits the game or continues the game based on input
    private void exitGame() {
        System.out.println("press s to save the current game, r to return to the game, and e to exit");
        String command = scanner.next();
        if (command.equals("s")) {
            saveGame();
        } else if (command.equals("e")) {
            isPlaying = false;
            return;
        } else if (command.equals("r")) {
            return;
        } else {
            System.out.println("Invalid command! Please try again.");
        }
        exitGame();
    }

    // EFFECTS: saves the current game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(hand, discards);
            jsonWriter.close();
            System.out.println("Saved current game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // Effects: displays a list of different valid user commands
    private void displayCommands() {
        System.out.println("\nValid commands are:");
        System.out.println("\tl -> loads the most recently saved game");
        System.out.println("\tw -> declares win");
        System.out.println("\tsd -> shows a list of previously discarded tiles");
        System.out.println("\th -> shows your current hand");
        System.out.println("\td -> discards a tile in your hand and end the turn");
        System.out.println("\te -> exits the game (and can choose to save it)");
    }

    // Requires: user inputs an integer within [0, MAX_ID (from tile class)]
    // Modifies: this
    // Effects: discard the tile corresponding to the ID that the user inputs if it is in the hand
    // if the tile is not in the hand, repeatedly ask for an ID until a valid ID is given
    private void discardTileFromHand() {
        resetIdList();
        System.out.println("Input the ID for the tile you wish to discard");
        System.out.println(tileIdGuide);
        int discardID = scanner.nextInt();
        if (idList.contains(discardID)) {
            discards.addTile(hand.discardTileIndex(idList.indexOf(discardID)));
        } else {
            System.out.println("There are no tiles with that ID in your hand! Please try again");
            discardTileFromHand();
        }
    }



    // Requires: user inputs a string
    // Modifies: this
    // Effects: returns true if the declaration of win is valid
    public Boolean declareWin() {
        int melds = 0;
        int pairs = 0;
        while (idList.size() > 0) {
            showHandInt(idList);
            System.out.println("Declare sequence, triple, pair, or exit?");
            String type = scanner.next();
            if (type.equals("sequence") || type.equals("triple")) {
                if (checkThree(type)) {
                    melds++;
                }
            } else if (type.equals("pair")) {
                if (checkPair()) {
                    pairs++;
                }
            } else if (type.equals("exit")) {
                break;
            } else {
                System.out.println("you did not input one of the keywords! (sequence/triple/pair/exit)");
            }
            System.out.println(melds + " melds and " + pairs + " pairs declared.");
        }
        resetHandList();
        return ((melds == 4) && (pairs == 1));
    }

    // Requires: user inputs integer in the range [0,MAX_ID (from tile class)] for tileID1
    // Modifies: this
    // Effects: returns true if user inputs a valid triple that is in the handList
    private Boolean checkThree(String type) {
        System.out.println("Type the first tile of your sequence/triple in ID form (type the lowest ID of your meld)");
        System.out.println(tileIdGuide);
        if (type.equals("sequence")) {
            int tileID1 = scanner.nextInt();
            return checkSequence(tileID1);
        } else if (type.equals("triple")) {
            int tileID1 = scanner.nextInt();
            return checkTriple(tileID1);
        } else {
            System.out.println("You did not input sequence or triple! Try again");
            return false;
        }
    }


    // Requires: tileID1 is within [0,MAX_ID (from tile class)]
    // Modifies: this
    // Effects: return true if the triple is valid and is in the current hand
    private Boolean checkTriple(int tileID1) {
        boolean isSuccess = true;
        if (Collections.frequency(idList, tileID1) >= 3) {
            idList.remove(Integer.valueOf(tileID1));
            idList.remove(Integer.valueOf(tileID1));
            idList.remove(Integer.valueOf(tileID1));
        } else {
            isSuccess = false;
            System.out.println("Declaration of triple failed");
        }
        return isSuccess;
    }

    // Requires: tileID1 is within [0,MAX_ID (from tile class)]
    // Modifies: this
    // Effects: return true if the sequence is valid and is in the current hand
    private Boolean checkSequence(int tileID1) {
        boolean isSuccess = true;
        if (tileID1 >= 25) {
            isSuccess = false;
        } else if ((idList.contains(tileID1)) && (idList.contains(tileID1 + 1)) && (idList.contains(tileID1 + 2))) {
            idList.remove(Integer.valueOf(tileID1));
            idList.remove(Integer.valueOf(tileID1 + 1));
            idList.remove(Integer.valueOf(tileID1 + 2));
        } else {
            isSuccess = false;
            System.out.println("Declaration of sequence failed");
        }
        return isSuccess;
    }

    // Requires: user inputs integer within [0,MAX_ID (from tile class)] when prompted
    // Modifies: this
    // Effects: return true if the sequence is valid and is in the current hand
    private Boolean checkPair() {
        System.out.println("Type your pair tile in ID form; " + tileIdGuide);
        int tileID1 = scanner.nextInt();
        boolean isSuccess = true;
        if (Collections.frequency(idList, tileID1) >= 2) {
            idList.remove(Integer.valueOf(tileID1));
            idList.remove(Integer.valueOf(tileID1));
        } else {
            isSuccess = false;
            System.out.println("Declaration of pair failed");
        }
        return isSuccess;
    }

    // Requires: tile is not null
    // Modifies: this
    // Effects: restores the original state of the idList by repeatedly adding tile
    private Boolean addTilesBack(int tile, int n) {
        for (int count = 1; count <= n; count++) {
            idList.add(tile);
        }
        return false;
    }

    // Modifies: this
    // Effects: prints the current hand (in ID form) as a list of strings
    public void showHandInt(List<Integer> hand) {
        handVisual = new ArrayList<String>();
        for (int tileID: hand) {
            Tile tile = new Tile(tileID);
            handVisual.add(tile.showTile());
        }
        System.out.println(handVisual);
    }

    // Modifies: this
    // Effects: prints the current hand (in tile form) as a list of strings
    public void showHand(List<Tile> hand) {
        handVisual = new ArrayList<String>();
        for (Tile tile: hand) {
            handVisual.add(tile.showTile());
        }
        System.out.println(" ");
        System.out.println(handVisual);
    }

    // Modifies: this
    // Effects: resets the idList to match with the handList
    private void resetIdList() {
        idList = new ArrayList<>();
        for (Tile tile: handList) {
            idList.add(tile.getID());
        }
    }

    // Modifies: this
    // Effects: rest the handList to match with the current hand
    private void resetHandList() {
        handList = hand.getHand();
    }



}
