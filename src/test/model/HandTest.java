package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand testHand1;
    Hand testHandComplete1;
    Hand testHand2;
    Hand testHandComplete2;
    ArrayList<Tile> handList1;
    ArrayList<Tile> handList2;
    Tile testTile1;
    Tile testTile2;

    @BeforeEach
    public void setup() {
        testTile1 = new Tile(0);
        testTile2 = new Tile(33);
        testHand1 = new Hand();
        testHand2 = new Hand();
        testHandComplete1 = new Hand();
        testHandComplete2 = new Hand();
        handList1 = new ArrayList<>();
        handList2 = new ArrayList<>();
        testHandComplete1.drawTile(testTile1);
        testHandComplete2.drawTile(testTile2);
        handList1 = testHandComplete1.getHand();
        handList2 = testHandComplete2.getHand();
    }

    @Test
    public void testConstructor() {
        assertEquals(13, testHand1.getHandLength());
        assertEquals(13, testHand1.getHandLength());
    }

    @Test
    public void testDrawTileOnce() {
        assertEquals(14, handList1.size());
        assertEquals(testTile1, handList1.get(13));
        assertEquals(14, handList2.size());
        assertEquals(testTile2, handList2.get(13));
    }

    @Test
    public void testDiscardTileOnce() {
        testHand1.discardTile(testTile1);
        testHand2.discardTile(testTile2);
        assertEquals(13, testHand1.getHandLength());
        assertEquals(13, testHand2.getHandLength());
    }

    @Test
    public void testDrawDiscardMultiple() {
        testHand1.discardTile(testTile1);
        testHand2.discardTile(testTile2);
        testHandComplete1.drawTile(testTile2);
        testHandComplete2.drawTile(testTile1);
        testHand1.discardTile(testTile2);
        testHand2.discardTile(testTile1);
        assertEquals(13, testHand1.getHandLength());
        assertEquals(13, testHand2.getHandLength());
    }

    @Test
    public void testSortHandMultiple() {
        testHand1.sortHand();
        testHand2.sortHand();
        testHandComplete1.sortHand();
        testHandComplete2.sortHand();

        assertTrue(testHand1.isSorted());
        assertTrue(testHand2.isSorted());
        assertTrue(testHandComplete1.isSorted());
        assertTrue(testHandComplete2.isSorted());
    }

}