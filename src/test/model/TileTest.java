package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the Tile class
public class TileTest {
    Tile testTile1;
    Tile testTile2;
    Tile testTile3;

    @BeforeEach
    public void setup() {
        testTile1 = new Tile(0);
        testTile2 = new Tile(17);
        testTile3 = new Tile(33);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testTile1.getID());
        assertEquals(17, testTile2.getID());
        assertEquals(33, testTile3.getID());
    }

    @Test
    public void testShowTileMultiple() {
        assertEquals("1 man", testTile1.showTile());
        assertEquals("9 pin", testTile2.showTile());
        assertEquals("3 honour", testTile3.showTile());
    }
}
