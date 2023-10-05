package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Tile.MAX_ID;

// Represents the current hand (list of currently kept tiles) of the player
// Note: a hand never exceeds 14 tiles, so you never draws a new tile or discards
// a tile twice in a row no matter what the situation is.
public class Hand {
    List<Tile> currentHand;


    // Effects: creates a starting hand with 13 tiles
    public Hand() {
        currentHand = new ArrayList<>();
        for (int count = 1; count <= 13; count++) {
            this.drawTile(new Tile(this.produceRandomID()));
        }
    }

    // Modifies: this
    // Effects: Adds given tile to the current hand
    public void drawTile(Tile tile) {
        currentHand.add(tile);
    }


    // THIS IS THE METHOD IF I'M ACTUALLY PLANNING TO BUILD A WALL!! BEWARE
    // Modifies: this
    // Effects: incorporates given tile into current hand
    public void drawTileFromWall(Tile tile) {
    }

    // Modifies: this
    // Effects: If the given tile is in the hand, remove the discarded tile from the hand and produce true.
    //          Otherwise, do nothing to the hand and produce false
    public Boolean discardTile(Tile tile) {
        if (currentHand.contains(tile)) {
            currentHand.remove(tile);
            return true;
        } else {
            return false;
        }
    }

    // Modifies: this
    // Effects: reorder the sequence of the tiles in the hand to make it sorted
    public void sortHand() {

    }

    public List<Tile> getHand() {
        return currentHand; // stub
    }

    public int getHandLength() {
        return currentHand.size(); // stub
    }

    // Effects: returns true if hand is sorted; false otherwise
    public Boolean isSorted() {
        return true; // stub
    }

    // Effects: returns a random integer between 0 and MAX_ID
    public int produceRandomID() {
        Random randid = new Random();
        return randid.nextInt(MAX_ID + 1); // stub
    }
}
