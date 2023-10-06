package ui;

import model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MahjongApp {
    List<String> handVisual;
    Scanner scanner;
    List<Tile> handList;

    public MahjongApp() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");



    }


    // Requires: user inputs a string
    // Modifies: this
    // Effects: returns true if the declaration of win is valid
    public Boolean declareWin() {
        int triples = 0;
        int pairs = 0;
        while (handList.size() > 0) {
            String type = scanner.next();
            System.out.println("Declare meld, pair, or exit?");
            if (type.equals("meld")) {
                if (this.checkThree()) {
                    triples++;
                }
            } else if (type.equals("pair")) {
                if (this.checkPair()) {
                    pairs++;
                }
            } else if (type.equals("exit")) {
                break;
            }
        }
        if ((triples == 4) && (pairs == 1)) {
            return true;
        }
        return false;
    }

    // Requires: user inputs integers
    // Modifies: this
    // Effects: returns true if user inputs a valid triple that is in the handList
    private Boolean checkThree() {
        System.out.println("Do you wish to declare a sequence (in increasing order) or a triple?");
        String tripleType = scanner.next();
        System.out.println("Type first tile in ID form; 1 man = 0, 1 pin = 9, 1 sou = 18, 1 wind = 27, 1 dragon = 31");
        int tileID1 = scanner.nextInt();
        System.out.println("Type second tile in ID form; 1 man = 0, 1 pin = 9, 1 sou = 18, 1 wind = 27, 1 dragon = 31");
        int tileID2 = scanner.nextInt();
        System.out.println("Type third tile in ID form; 1 man = 0, 1 pin = 9, 1 sou = 18, 1 wind = 27, 1 dragon = 31");
        int tileID3 = scanner.nextInt();
        if (tripleType.equals("sequence")) {
            return checkSequence(tileID1,tileID2,tileID3);
        } else if (tripleType.equals("triple")) {
            return checkTriple(tileID1,tileID2,tileID3);
        } else {
            System.out.println("you did not input sequence or triple");
            return false;
        }
    }

    public Boolean checkTriple(int ID1, int ID2, int ID3) {
        Tile tile1 = new Tile(ID1);
        Tile tile2 = new Tile(ID2);
        Tile tile3 = new Tile(ID3);
        Boolean isSuccess = true;
        if (!((tile2.getNumber() == tile1.getNumber()) && (tile1.getNumber() == tile3.getNumber()))) {
            isSuccess = false;
        } else if (handList.contains(tile1)) {
            handList.remove(tile1);
            if (handList.contains(tile2)) {
                handList.remove(tile2);
                if (handList.contains(tile3)) {
                    handList.remove(tile3);
                } else {
                    isSuccess = false;
                    handList.add(tile1);
                    handList.add(tile2);
                }
            } else {
                isSuccess = false;
                handList.add(tile2);
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }


    // Requires: ID1, ID2, and ID3 are in [0, MAX_ID (from tile class)]
    // Modifies: this
    // Effects: return true if the sequence is valid and is in the current hand
    public Boolean checkSequence(int ID1, int ID2, int ID3) {
        Tile tile1 = new Tile(ID1);
        Tile tile2 = new Tile(ID2);
        Tile tile3 = new Tile(ID3);
        Boolean isSuccess = true;
        if (!((tile2.getNumber() - tile1.getNumber() == 1) && (tile3.getNumber() - tile2.getNumber() == 1))) {
            isSuccess = false;
        } else if ((handList.contains(tile1)) && (handList.contains(tile2)) && (handList.contains(tile3))) {
            handList.remove(tile1);
            handList.remove(tile2);
            handList.remove(tile3);
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }



    // Modifies: this
    // Effects: prints the current hand as a list of strings
    public void showHand(List<Tile> hand) {
        handVisual = new ArrayList<String>();
        for (Tile tile: hand) {
            handVisual.add(tile.showTile());
        }
        System.out.println(handVisual);
    }

}
