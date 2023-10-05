package model;

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

}
