package model;

import org.json.JSONArray;

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
    // Effects: adds given tile to the end of the list of previously discarded tiles
    public void addTile(Tile tile) {
        this.discardedList.add(tile);
    }

    // Effects: returns the list of previously discarded tiles in
    // the same order as that they are added
    public List<Tile> getDiscardedTiles() {
        return this.discardedList; // stub
    }

    // Requires: the index provided (i) must be in the range of the discardedList
    // Modifies: this
    // Effects: removes the tile at index i of the discardedList and returns it
    public Tile removeDiscardIndex(int i) {
        return discardedList.remove(i);
    }

    // Effects: returns the list of tiles in current discards as a list of strings
    public List<String> getDiscardedTilesString() {
        List<String> stringList = new ArrayList<>();
        for (Tile t : discardedList) {
            stringList.add(t.showTile());
        }
        return stringList;
    }

    // Effects: returns a JSONArray representation of the discarded tiles
    public JSONArray toJson() {
        JSONArray discards = new JSONArray();
        for (Tile tile: discardedList) {
            discards.put(tile.toJson());
        }
        return discards;
    }
}
