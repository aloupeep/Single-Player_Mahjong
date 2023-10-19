package model;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the Tile class
public class TileTest {
    Tile testTile1;
    Tile testTile2;
    Tile testTile3;
    Tile testTile4;
    Tile testTile5;

    @BeforeEach
    public void setup() {
        testTile1 = new Tile(0);
        testTile2 = new Tile(17);
        testTile3 = new Tile(23);
        testTile4 = new Tile(28);
        testTile5 = new Tile(33);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, testTile1.getNumber());
        assertEquals(9, testTile2.getNumber());
        assertEquals(6, testTile3.getNumber());
        assertEquals(2, testTile4.getNumber());
        assertEquals(3, testTile5.getNumber());

        assertEquals("man", testTile1.getSuit());
        assertEquals("pin", testTile2.getSuit());
        assertEquals("sou", testTile3.getSuit());
        assertEquals("wind", testTile4.getSuit());
        assertEquals("dragon", testTile5.getSuit());

        assertEquals(0, testTile1.getID());
        assertEquals(17, testTile2.getID());
        assertEquals(23, testTile3.getID());
        assertEquals(28, testTile4.getID());
        assertEquals(33, testTile5.getID());
    }

    @Test
    public void testShowTileMultiple() {
        assertEquals("1 man", testTile1.showTile());
        assertEquals("9 pin", testTile2.showTile());
        assertEquals("6 sou", testTile3.showTile());
        assertEquals("2 wind", testTile4.showTile());
        assertEquals("3 dragon", testTile5.showTile());
    }

    @Test
    public void testToJson() {
        JSONObject tile5Json = testTile5.toJson();
        int jsonID = tile5Json.getInt("id");
        assertEquals(33,jsonID);
    }

    @Test
    public void testToJsonMultiple() {
        int tile1JsonId = testTile1.toJson().getInt("id");
        int tile3JsonId = testTile3.toJson().getInt("id");
        int tile5JsonId = testTile5.toJson().getInt("id");

        assertEquals(testTile1.getID(), tile1JsonId);
        assertEquals(testTile3.getID(), tile3JsonId);
        assertEquals(testTile5.getID(), tile5JsonId);



    }

}
