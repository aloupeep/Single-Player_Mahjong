package persistence;

import model.DiscardedTiles;
import model.Hand;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of the current mahjong game to file
// Took inspiration from JsonSerializationDemo document found at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of current game to file
    public void write(Hand hand, DiscardedTiles discards) {
        JSONObject gameJson = new JSONObject();
        gameJson.put("Hand", hand.toJson());
        gameJson.put("Discards", discards.toJson());
        saveToFile(gameJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String gameString) {
        writer.print(gameString);
    }
}

