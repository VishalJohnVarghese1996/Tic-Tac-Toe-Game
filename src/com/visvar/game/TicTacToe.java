package com.visvar.game;/* TicTacToe.java

This program displays a resizeable Tic Tac Toe game.  Play alternates
between the user, who plays O, and the computer, which plays X.
After the game ends the program displays a dialog box announcing the
winner and the total number of wins, losses, and draws by the user, and
asks if the user wants to play again.  If the user clicks YES then the
screen is cleared and a new game started.  Otherwise the program exits.
The computer moves first on alternate games.

At the top of the screen is a slider that allows the user to change
the line thickness of the O's, X's and the 3x3 grid.  There are also
2 buttons allowing the user to change the colors of the O's and X's.
Initially lines are 4 pixels thick, O's are blue and X's are red.

The computer's strategy is first to complete 3 X's in a row, or if that
is not possible, to block a row of 3 O's, or if that is not possible,
to move randomly.
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
