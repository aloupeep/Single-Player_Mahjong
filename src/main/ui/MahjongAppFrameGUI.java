package ui;

import javax.swing.*;

public class MahjongAppFrameGUI extends JFrame {
    private MahjongAppGUI appGUI;

    public MahjongAppFrameGUI() {
        super("Single-player mahjong");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        appGUI = new MahjongAppGUI();
        appGUI.setOpaque(true); //content panes must be opaque
        setContentPane(appGUI);

        //Display the window.
        pack();
        setVisible(true);
    }
}
