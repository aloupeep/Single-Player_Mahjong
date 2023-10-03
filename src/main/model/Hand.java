package model;

import java.util.ArrayList;

// Represents the current hand (list of currently kept tiles) of the player
public class Hand {


    // Effects: creates a starting hand with 13 tiles
    public Hand() {

    }

    // Modifies: this
    // Effects: Adds given tile to the current hand
    public void drawTile(Tile tile){

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
        return false; // stub
    }

    // Modifies: this
    // Effects: reorder the sequence of the tiles in the hand to make it sorted
    public void sortHand() {

    }

    // Requires: APPROPRIATE USER INPUTS (expand on that later!)
    // Effects: returns true if the declaration of win is valid
    public Boolean declareWin() {
        return false; // stub
    }

    public ArrayList<Tile> getHand() {
        return null; // stub
    }

    public int getHandLength() {
        return -1; // stub
    }

    // Effects: returns a random tile
    private Tile produceRandomTile() {
        return null; // stub
    }


    // delete or rename this class!
}
