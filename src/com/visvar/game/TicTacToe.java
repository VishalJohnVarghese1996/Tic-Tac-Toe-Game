package com.visvar.game;

/* TicTacToe.java
This program displays a resizeable Tic Tac Toe game.  Play alternates
between the user, who plays O, and the computer, which plays X.
After the game ends the program displays a dialog box announcing the
winner and the total number of wins, losses, and draws by the user, and
asks if the user wants to play again.  If the user clicks YES then the
screen is cleared and a new game started.  Otherwise the program exits.
The computer moves first on alternate games.

*/

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {

    private Board board;

    // Start the game
    public static void main(String args[]) {
        new TicTacToe();
    }

    // Initialize
    public TicTacToe() {
        super("Tic Tac Toe Game");
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        add(topPanel, BorderLayout.NORTH);
        add(board = new Board(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }
}
