package ui;


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            new MahjongApp();
        } catch (FileNotFoundException e) {
            System.out.println("Uh oh, no files found... unable to play game");
        }
    }
}
