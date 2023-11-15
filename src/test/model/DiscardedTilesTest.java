package model;

import org.json.JSONArray;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the class DiscardedTiles
public class DiscardedTilesTest {
    DiscardedTiles testDiscardedTiles;
    List<Tile> testDiscardsList;
    Tile testTile1;
    Tile testTile2;
    Tile testTile3;
    Tile testTile4;

    @BeforeEach
    public void setup() {
        testDiscardedTiles = new DiscardedTiles();
        testDiscardsList = new ArrayList<Tile>();
        testTile1 = new Tile(20);
        testTile2 = new Tile(5);
        testTile3 = new Tile(27);
        testTile4 = new Tile(33);
    }

    @Test
    public void testConstructor() {
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();
        assertEquals(0, testDiscardsList.size());

    }

    @Test
    public void testAddTileOnce() {
        testDiscardedTiles.addTile(testTile1);
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();
        assertEquals(1, testDiscardsList.size());
        assertTrue(testDiscardsList.contains(testTile1));
    }

    @Test
    public void testRemoveTileIndexOnce() {
        testDiscardedTiles.addTile(testTile2);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile3);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile1);
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();

        assertEquals(testTile2, testDiscardedTiles.removeDiscardIndex(0));
        assertEquals(4, testDiscardsList.size());
    }

    @Test
    public void testRemoveTileIndexMultiple() {
        testDiscardedTiles.addTile(testTile2);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile3);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile1);
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();

        assertEquals(testTile4, testDiscardedTiles.removeDiscardIndex(1));
        assertEquals(4, testDiscardsList.size());
        assertEquals(testTile3, testDiscardedTiles.removeDiscardIndex(1));
        assertEquals(testTile1, testDiscardedTiles.removeDiscardIndex(2));
        assertEquals(testTile2, testDiscardedTiles.removeDiscardIndex(0));
        assertEquals(testTile4, testDiscardedTiles.removeDiscardIndex(0));
        assertEquals(0, testDiscardsList.size());
    }

    @Test
    public void testAddTileMultiple() {
        testDiscardedTiles.addTile(testTile2);
        testDiscardedTiles.addTile(testTile3);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile1);
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();

        assertEquals(4, testDiscardsList.size());
        assertEquals(testTile2, testDiscardsList.get(0));
        assertEquals(testTile3, testDiscardsList.get(1));
        assertEquals(testTile4, testDiscardsList.get(2));
        assertEquals(testTile1, testDiscardsList.get(3));
    }

    @Test
    public void testAddTileMultipleReplicate() {
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile3);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile1);
        testDiscardsList = testDiscardedTiles.getDiscardedTiles();

        assertEquals(5, testDiscardsList.size());
        assertEquals(testTile4, testDiscardsList.get(0));
        assertEquals(testTile3, testDiscardsList.get(1));
        assertEquals(testTile4, testDiscardsList.get(2));
        assertEquals(testTile4, testDiscardsList.get(3));
        assertEquals(testTile1, testDiscardsList.get(4));
        assertFalse(testDiscardsList.contains(testTile2));
    }

    @Test
    public void testToJsonEmpty() {
        JSONArray discardsJsonArray = testDiscardedTiles.toJson();
        assertEquals(0, discardsJsonArray.length());
    }

    @Test
    public void testToJsonMultiple() {
        testDiscardedTiles.addTile(testTile2);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile4);
        testDiscardedTiles.addTile(testTile1);
        JSONArray discardsJsonArray = testDiscardedTiles.toJson();

        assertEquals(4, discardsJsonArray.length());
        assertEquals(testTile2.getID(), discardsJsonArray.getJSONObject(0).getInt("id"));
        assertEquals(testTile4.getID(), discardsJsonArray.getJSONObject(1).getInt("id"));
        assertEquals(testTile4.getID(), discardsJsonArray.getJSONObject(2).getInt("id"));
        assertEquals(testTile1.getID(), discardsJsonArray.getJSONObject(3).getInt("id"));
    }


}
