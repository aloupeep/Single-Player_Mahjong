package ui;

import model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MahjongApp {
    List<String> handVisual;
    Scanner scanner;
    Hand hand;
    List<Tile> handList;
    List<Integer> idList;
    Boolean isPlaying = true;
    DiscardedTiles discards = new DiscardedTiles();
    String tileIdGuide = "1 man = 0, 1 pin = 9, 1 sou = 18, 1 wind = 27, 1 dragon = 31";

    public MahjongApp() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        hand = new Hand();
        while (isPlaying) {
            hand.drawTile(new Tile(hand.produceRandomID()));
            hand.sortHandLastTile();
            resetHandList();
            showHand(hand.getHand());
            System.out.println("Do you want to declare win? (true/false)");
            boolean win = scanner.nextBoolean();
            resetIdList();
            if (win) {
                if (declareWin()) {
                    System.out.println("You won!");
                    break;
                }
            }
            System.out.println("Discarded Tiles:");
            showHand(discards.getDiscardedTiles());
            showHand(handList);
            discardTileFromHand();
        }
    }

    private void resetIdList() {
        idList = new ArrayList<>();
        for (Tile tile: handList) {
            idList.add(tile.getID());
        }
    }

    // Requires: user inputs an integer within [0, MAX_ID (from tile class)]
    // Modifies: this
    // Effects: discard the tile corresponding to the ID that the user inputs if it is in the hand
    // if the tile is not in the hand, repeatedly ask for an ID until a valid ID is given
    private void discardTileFromHand() {
        resetIdList();
        System.out.println("Input the ID for the tile you wish to discard");
        System.out.println(tileIdGuide);
        int discardID = scanner.nextInt();
        if (idList.contains(discardID)) {
            discards.addTile(hand.discardTileIndex(idList.indexOf(discardID)));
        } else {
            System.out.println("There are no tiles with that ID in your hand! Please try again");
            discardTileFromHand();
        }
    }



    // Requires: user inputs a string
    // Modifies: this
    // Effects: returns true if the declaration of win is valid
    public Boolean declareWin() {
        int triples = 0;
        int pairs = 0;
        while (idList.size() > 0) {
            showHandInt(idList);
            System.out.println("Declare meld (three-tile group), pair, or exit?");
            String type = scanner.next();
            if (type.equals("meld")) {
                if (checkThree()) {
                    triples++;
                }
            } else if (type.equals("pair")) {
                if (checkPair()) {
                    pairs++;
                }
            } else if (type.equals("exit")) {
                break;
            } else {
                System.out.println("you did not input one of the keywords! (meld/pair/exit)");
            }
        }
        resetHandList();
        return ((triples == 4) && (pairs == 1));
    }

    // Requires: user inputs integers
    // Modifies: this
    // Effects: returns true if user inputs a valid triple that is in the handList
    private Boolean checkThree() {
        System.out.println("Do you wish to declare a sequence (in increasing order) or a triple?");
        String tripleType = scanner.next();
        System.out.println("Type first, second, and third tiles' ID in increasing order (separated by line break)");
        System.out.println(tileIdGuide);
        int tile1 = scanner.nextInt();
        int tile2 = scanner.nextInt();
        int tile3 = scanner.nextInt();
        if (tripleType.equals("sequence")) {
            return checkSequence(tile1,tile2,tile3);
        } else if (tripleType.equals("triple")) {
            return checkTriple(tile1,tile2,tile3);
        } else {
            System.out.println("You did not input sequence or triple! Try again");
            return false;
        }
    }


    // Requires: tileID1, tileID2, tileID3 are within [0,MAX_ID (from tile class)]
    // Modifies: this
    // Effects: return true if the triple is valid and is in the current hand
    private Boolean checkTriple(int tileID1, int tileID2, int tileID3) {
        boolean isSuccess = true;
        if (!((tileID2 == tileID1) && (tileID1 == tileID3))) {
            isSuccess = false;
        } else if (idList.contains(tileID1)) {
            idList.remove(Integer.valueOf(tileID1));
            if (idList.contains(tileID2)) {
                idList.remove(Integer.valueOf(tileID2));
                if (idList.contains(tileID3)) {
                    idList.remove(Integer.valueOf(tileID3));
                } else {
                    isSuccess = addTilesBack(tileID1,2);
                }
            } else {
                isSuccess = addTilesBack(tileID1,1);
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    private Boolean addTilesBack(int tile, int n) {
        for (int count = 1; count <= n; count++) {
            idList.add(tile);
        }
        return false;
    }


    // Requires: tileID1, tileID2, tileID3 are within [0,MAX_ID (from tile class)]
    // Modifies: this
    // Effects: return true if the sequence is valid and is in the current hand
    public Boolean checkSequence(int tileID1, int tileID2, int tileID3) {
        boolean isSuccess = true;
        if (!((tileID2 - tileID1 == 1) && (tileID3 - tileID2 == 1))) {
            isSuccess = false;
        } else if ((tileID1 >= 27) || (tileID2 >= 27) || (tileID3 >= 27)) {
            isSuccess = false;
        } else if (((idList.contains(tileID1)) && (idList.contains(tileID2))) && (idList.contains(tileID3))) {
            idList.remove(Integer.valueOf(tileID1));
            idList.remove(Integer.valueOf(tileID2));
            idList.remove(Integer.valueOf(tileID3));
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    private Boolean checkPair() {
        System.out.println("Type first tile in ID form; " + tileIdGuide);
        int tileID1 = scanner.nextInt();
        System.out.println("Type second tile in ID form; " + tileIdGuide);
        int tileID2 = scanner.nextInt();
        boolean isSuccess = true;
        if (!(tileID2 == tileID1)) {
            isSuccess = false;
        } else if (idList.contains(tileID1)) {
            idList.remove(Integer.valueOf(tileID1));
            if (idList.contains(tileID2)) {
                idList.remove(Integer.valueOf(tileID2));
            } else {
                isSuccess = addTilesBack(tileID1,1);
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    private void resetHandList() {
        handList = hand.getHand();
    }

    // Modifies: this
    // Effects: prints the current hand as a list of strings
    public void showHandInt(List<Integer> hand) {
        handVisual = new ArrayList<String>();
        for (int tileID: hand) {
            Tile tile = new Tile(tileID);
            handVisual.add(tile.showTile());
        }
        System.out.println(handVisual);
    }

    public void showHand(List<Tile> hand) {
        handVisual = new ArrayList<String>();
        for (Tile tile: hand) {
            handVisual.add(tile.showTile());
        }
        System.out.println(" ");
        System.out.println(handVisual);

    }



}
