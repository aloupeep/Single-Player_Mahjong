package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Tile.MAX_ID;

// Represents the current hand (list of currently kept tiles) of the player
// Note: a hand is always between 13 and 14 tiles, so you never draw a new tile or discards
// a tile twice in a row no matter what the situation is.
public class Hand {
    List<Tile> currentHand;


    // Effects: creates a starting hand with 13 random tiles and sorts
    public Hand() {
        currentHand = new ArrayList<>();
        for (int count = 1; count <= 13; count++) {
            this.drawTile(new Tile(this.produceRandomID()));
            this.sortHandLastTile();
        }
    }

    // Effects: creates a hand with the given list of tiles
    public Hand(List<Tile> tileList) {
        currentHand = tileList;
    }

    // Modifies: this
    // Effects: Adds given tile to the current hand
    public void drawTile(Tile tile) {
        currentHand.add(tile);
    }

    // Modifies: this
    // Effects: Adds random tile to the current hand and returns it
    public Tile drawRandomTile() {
        Tile newTile = new Tile(produceRandomID());
        currentHand.add(newTile);
        return newTile;
    }

    // Modifies: this
    // Effects: adds a random tile to current hand, sorts the new hand, and returns the tile
    public Tile drawAndSort() {
        Tile newTile = drawRandomTile();
        sortHandLastTile();
        return newTile;
    }

    // THIS IS THE METHOD IF I'M ACTUALLY PLANNING TO BUILD A WALL!! BEWARE
    // Modifies: this
    // Effects: incorporates given tile into current hand
    // public void drawTileFromWall(Tile tile) {
    //  }

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
    // Effects: discards the tile at index of ind of the current hand and returns it
    public Tile discardTileIndex(int ind) {
        // EventLog.getInstance().logEvent(new Event("Tile Discarded (added to discards)"));
        return currentHand.remove(ind);
    }

    // Modifies: this
    // Effects: reorders the sequence of the tiles in the current hand to make it sorted
    // public void sortHand() {
    // }

    // Requires: the current hand is sorted (except for the last tile added)
    // Modifies: this
    // Effects: inserts the last tile of the current hand to the correct place
    public void sortHandLastTile() {
        int index = currentHand.size() - 1;
        Tile lastTile = currentHand.get(index);
        int lastID = lastTile.getID();
        boolean isInserted = true;
        List<Tile> newHand = new ArrayList<>();
        currentHand.remove(lastTile);
        for (Tile tile: currentHand) {
            if ((isInserted) && (lastID <= tile.getID())) {
                newHand.add(lastTile);
                isInserted = false;
            }
            newHand.add(tile);
        }
        if (isInserted) {
            newHand.add(lastTile);
        }
        currentHand = newHand;
    }

    public List<Tile> getHand() {
        return currentHand; // stub
    }

    // Effects: returns the list of tiles in current hand as a list of strings
    public List<String> getHandString() {
        List<String> stringList = new ArrayList<>();
        for (Tile t : currentHand) {
            stringList.add(t.showTile());
        }
        return stringList;
    }

    // Effects: returns the current length of the hand
    public int getHandLength() {
        return currentHand.size(); // stub
    }

    // Effects: returns true if hand is sorted; false otherwise
    public Boolean isSorted() {
        int lowID = 0;
        for (Tile tile: currentHand) {
            if (tile.getID() < lowID) {
                return false;
            }
            lowID = tile.getID();
        }
        return true;
    }

    // Effects: returns a random integer between 0 and MAX_ID
    public int produceRandomID() {
        Random randID = new Random();
        return randID.nextInt(MAX_ID + 1); // stub
    }

    // Effects: returns a JSONArray representation of the current hand
    public JSONArray toJson() {
        JSONArray handArray = new JSONArray();
        for (Tile tile: currentHand) {
            handArray.put(tile.toJson());
        }
        return handArray;
    }


}
