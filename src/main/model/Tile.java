package model;

// Represents a mahjong tile having an ID,
// a number, and a suit (type)
public class Tile {
    private int id;
    private int number;
    private String suit;
    public static final int MAX_ID = 33;


    // Requires: id is inside range of [0,33]
    // Effects: makes a new tile with number and suit initialized based on id
    public Tile(int id) {
        this.id = id;
        if (id >= 31) {
            this.number = id % 3 + 1;
            this.suit = "honour";
        } else {
            this.number = id % 9 + 1;
        }
    }

    // Effects: returns a representation of the tile as a string
    public String showTile() {
        return Integer.toString(this.number) + " " + this.suit; // stub
    }

    // Effects: returns the id of the tile [0,33] based on its number and suit
    public int getID() {
        return this.id; // stub
    }

    public int getNumber() {
        return this.number; // stub}
    }

    public String getSuit() {
        return this.suit; // stub
    }
}
