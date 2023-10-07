package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import static model.Tile.MAX_ID;
import static org.junit.jupiter.api.Assertions.*;

// NEED TO ADD ONE MORE TEST WHERE THE isSORTED FAILS!!!
class HandTest {
    Hand testHand1;
    Hand testHandComplete1;
    Hand testHand2;
    Hand testHandComplete2;
    List<Tile> handList1;
    List<Tile> handList2;
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
        testHandComplete1.drawTile(testTile1);
        testHandComplete2.drawTile(testTile2);
        handList1 = testHandComplete1.getHand();
        handList2 = testHandComplete2.getHand();
    }

    @Test
    public void testConstructor() {
        assertEquals(13, testHand1.getHandLength());
        assertEquals(13, testHand2.getHandLength());

        assertTrue(testHand1.isSorted());
        assertTrue(testHand2.isSorted());

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
        assertTrue(testHandComplete1.discardTile(testTile1));
        assertTrue(testHandComplete2.discardTile(testTile2));
        assertEquals(13, testHandComplete1.getHandLength());
        assertEquals(13, testHandComplete2.getHandLength());
    }
    @Test
    public void testDiscardTileFailed() {
        if (!(handList1.contains(new Tile(7)))) {
            assertFalse(testHandComplete1.discardTile(new Tile(7)));
        } else {
            assertTrue(testHandComplete1.discardTile(new Tile(7)));
        }

        if (!(handList2.contains(testTile1))) {
            assertFalse(testHandComplete1.discardTile(testTile2));
        } else {
            assertTrue(testHandComplete2.discardTile(testTile1));
        }

    }

    @Test
    public void testDrawDiscardMultiple() {

        assertTrue(testHandComplete1.discardTile(testTile1));
        assertTrue(testHandComplete2.discardTile(testTile2));
        testHandComplete1.drawTile(testTile2);
        testHandComplete2.drawTile(testTile1);
        assertTrue(testHandComplete1.discardTile(testTile2));
        assertTrue(testHandComplete2.discardTile(testTile1));
        assertEquals(13, testHandComplete1.getHandLength());
        assertEquals(13, testHandComplete2.getHandLength());
    }

    @Test
    public void testDrawDiscardIndexMultiple() {

        testHandComplete1.discardTileIndex(13);
        testHandComplete2.discardTileIndex(13);
        testHandComplete1.drawTile(testTile2);
        testHandComplete2.drawTile(testTile1);
        testHandComplete1.discardTileIndex(0);
        testHandComplete2.discardTileIndex(0);
        testHandComplete1.drawTile(testTile2);
        testHandComplete2.drawTile(testTile1);
        testHandComplete1.discardTileIndex(5);
        testHandComplete2.discardTileIndex(5);
        assertEquals(13, testHandComplete1.getHandLength());
        assertEquals(13, testHandComplete2.getHandLength());
    }


    @Test
    public void testSortHandLastTileMultiple() {
        testHandComplete1.sortHandLastTile();
        testHandComplete2.sortHandLastTile();

        assertTrue(testHandComplete1.isSorted());
        assertTrue(testHandComplete2.isSorted());
        assertEquals(14,testHandComplete1.getHandLength());
        assertEquals(14,testHandComplete2.getHandLength());
    }

    @Test
    public void testProduceRandomTileMultiple() {
        HashSet<Integer> testSet = new HashSet<>();
        for (int count = 1; count <= 100; count++) {
            int randomID = testHand1.produceRandomID();
            testSet.add(randomID);
            if ((randomID < 0) || (randomID > MAX_ID)) {
                fail();
                }
            }
        assertTrue((testSet.size() > 3));
    }

    @Test
    public void testIsSortedFail() {
        testHand1.drawTile(testTile1);
        testHand2.drawTile(new Tile(1));
        assertFalse(testHand1.isSorted());
        // note: not considering super small events like all 13 tiles being
        // 0 or 1
    }


}