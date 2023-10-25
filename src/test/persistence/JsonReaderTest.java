package persistence;

import model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the JsonReader class
// Took inspiration from JsonSerializationDemo document found at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/727WYSI.json");
        try {
            Hand testHand = reader.readHand();
            DiscardedTiles testDiscards = reader.readDiscards();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            Hand hand = reader.readHand();
            DiscardedTiles discards = reader.readDiscards();
            assertEquals(0, hand.getHandLength());
            assertEquals(0, discards.getDiscardedTiles().size());
        } catch (IOException e) {
            fail("Oh nyooo!!!! couldn't read file!!");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Hand hand = reader.readHand();
            DiscardedTiles discards = reader.readDiscards();

            List<Tile> handList = hand.getHand();
            assertEquals(14, handList.size());
            for (int index = 0; index < 14; index++) {
                assertEquals(index,handList.get(index).getID());
            }

            List<Tile> discardsList = discards.getDiscardedTiles();
            assertEquals(14, discardsList.size());
            for (int index = 0; index < 14; index++) {
                assertEquals(2*index,handList.get(index).getID());
            }

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
