package ui;


import java.io.FileNotFoundException;
import java.util.ArrayList;

// Main program; start of execution
public class Main {
    public static void main(String[] args) {
        new MahjongAppFrameGUI();
        /*try {
            new MahjongAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Uh oh, no files found... unable to play game");
        }*/
    }
}
