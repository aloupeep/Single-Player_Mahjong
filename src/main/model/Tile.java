package model;

import org.json.JSONObject;

// Represents a mahjong tile having an ID,
// a number, and a suit (type)
public class Tile {
    private final int id;
    private final int number;
    private final String suit;
    public static final int MAX_ID = 33;


    // Requires: id is inside range of [0,33]
    // Effects: makes a new tile with number and suit initialized based on id
    public Tile(int id) {
        this.id = id;
        if (id >= 31) {
            this.number = id % 10;
            this.suit = "dragon";
        } else {
            this.number = id % 9 + 1;
            if (id / 9 == 0) {
                this.suit = "man";
            } else if (id / 9 == 1) {
                this.suit = "pin";
            } else if (id / 9 == 2) {
                this.suit = "sou";
            } else {
                this.suit = "wind";
            }
        }
    }

    // Effects: returns a representation of the tile as a string
    public String showTile() {
        return Integer.toString(this.number) + " " + this.suit; // stub
    }

    public int getID() {
        return this.id; // stub
    }

    public int getNumber() {
        return this.number; // stub}
    }

    public String getSuit() {
        return this.suit; // stub
    }

    // Effects: returns a Json representation of the tile in id form
    public JSONObject toJson() {
        JSONObject tileJson = new JSONObject();
        tileJson.put("id", id);
        return tileJson;
    }
}
