package persistence;

import model.DiscardedTiles;
import model.Hand;
import model.Tile;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Took inspiration from JsonSerializationDemo document found at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Hand testHand = new Hand();
            DiscardedTiles testDiscards = new DiscardedTiles();
            JsonWriter writer = new JsonWriter("./data/myWrit/\135[145[Sff.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            List<Tile> handList = new LinkedList<>();
            Hand testHand = new Hand(handList);
            DiscardedTiles testDiscards = new DiscardedTiles();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(testHand, testDiscards);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            Hand checkHand = reader.readHand();
            DiscardedTiles checkDiscards = reader.readDiscards();
            assertEquals(0, checkHand.getHandLength());
            assertEquals(0, checkDiscards.getDiscardedTiles().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {

        List<Tile> testHandList = new LinkedList<>();
        for (int i = 0; i < 14; i++) {
            testHandList.add(new Tile(i + 3));
        }
        Hand testHand = new Hand(testHandList);

        DiscardedTiles testDiscards = new DiscardedTiles();
        for (int i = 0; i < 9; i++) {
            testDiscards.addTile(new Tile(i + 25));
        }
        List<Tile> testDiscardsList = testDiscards.getDiscardedTiles();

        JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
        try {
            writer.open();
            writer.write(testHand, testDiscards);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("can't write that!!!!");
        }

        JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
        try {
            Hand checkHand = reader.readHand();
            DiscardedTiles checkDiscards = reader.readDiscards();

            List<Tile> checkHandList = checkHand.getHand();
            assertEquals(testHandList.size(), checkHandList.size());
            int index = 0;
            for (Tile tile : testHandList) {
                assertEquals(tile.getID(), checkHandList.get(index).getID());
                index++;
            }

            List<Tile> checkDiscardsList = checkDiscards.getDiscardedTiles();
            assertEquals(testDiscardsList.size(), checkDiscardsList.size());
            index = 0;
            for (Tile tile : testDiscardsList) {
                assertEquals(tile.getID(), checkDiscardsList.get(index).getID());
                index++;
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
