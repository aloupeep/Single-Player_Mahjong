package model;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the class DiscardedTiles
public class DiscardedTilesTest {
    DiscardedTiles testDiscardedTiles;
    List<Tile> testDiscards;
    Tile testTile1;
    Tile testTile2;
    Tile testTile3;
    Tile testTile4;

    @BeforeEach
    public void setup() {
        testDiscardedTiles = new DiscardedTiles();
        testTile1 = new Tile(20);
        testTile2 = new Tile(5);
        testTile3 = new Tile(27);
        testTile4 = new Tile(33);
    }

    @Test
    public void testConstructor() {
        testDiscards = testDiscardedTiles.getDiscardedTiles();
        assertEquals(0, testDiscards.size());

    }

    @Test
    public void testAddTileOnce() {
        testDiscardedTiles.addTile(testTile1);
        testDiscards = testDiscardedTiles.getDiscardedTiles();
        assertEquals(1,testDiscards);
        assertTrue(testDiscards.contains(testTile1));
    }

}
