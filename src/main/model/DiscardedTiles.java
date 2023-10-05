package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of previously discarded tiles; order of adding tiles
// is maintained
public class DiscardedTiles {
    List<Tile> discardedList;

    // Effects: creates an empty list of previously discarded tiles
    public DiscardedTiles() {
        this.discardedList = new ArrayList<Tile>();
    }

    // Modifies: this
    // Effects: adds given tile to list of previously discarded tiles
    public void addTile(Tile tile) {
        this.discardedList.add(tile);
    }

    // Effects: returns the list of previously discarded tiles in
    // the same order as that they are added
    public List<Tile> getDiscardedTiles() {
        return this.discardedList; // stub
    }

}
