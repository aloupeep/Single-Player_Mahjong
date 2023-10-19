package persistence;

import model.DiscardedTiles;
import model.Hand;
import model.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader of the JSON representation of a mahjong game in progress that is previously saved as a file
// Took inspiration from JsonSerializationDemo document found at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads hand from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Hand readHand() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHand(jsonObject);
    }

    // EFFECTS: reads discarded tiles from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DiscardedTiles readDiscards() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDiscards(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses hand from JSON object and returns it
    private Hand parseHand(JSONObject jsonObject) {
        JSONArray handArrayJson = jsonObject.getJSONArray("Hand");
        ArrayList<Tile> handArrayList = new ArrayList<>();
        for (Object tile: handArrayJson) {
            JSONObject nextTile = (JSONObject) tile;
            int tileID = nextTile.getInt("id");
            handArrayList.add(new Tile(tileID));
        }
        return new Hand(handArrayList);
    }

    // EFFECTS: parses discarded tiles from JSON object and returns it
    private DiscardedTiles parseDiscards(JSONObject jsonObject) {
        JSONArray discardsArrayJson = jsonObject.getJSONArray("Discards");
        DiscardedTiles discards = new DiscardedTiles();
        for (Object tile: discardsArrayJson) {
            JSONObject nextTile = (JSONObject) tile;
            int tileID = nextTile.getInt("id");
            discards.addTile(new Tile(tileID));
        }
        return discards;
    }

}
