package com.visvar.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

// Board is what actually plays and displays the game
public class Board extends JPanel implements MouseListener {
    private int wins = 0, losses = 0, draws = 0;  // game count by user
    private Random random = new Random();
    private char position[] = {  // Board position (BLANK, O, or X)
            BLANK, BLANK, BLANK,
            BLANK, BLANK, BLANK,
            BLANK, BLANK, BLANK};
    private int lineThickness = 10;
    private Player user = new Player(Color.BLUE);
    private Player comp = new Player(Color.RED);
    static final char BLANK = ' ', O = 'O', X = 'X';

    // Endpoints of the 8 rows in position[] (across, down, diagonally)
    private int rows[][] = {{0, 2}, {3, 5}, {6, 8}, {0, 6}, {1, 7}, {2, 8}, {0, 8}, {2, 6}};


    public Board() {
        startTheGame();
        addMouseListener(this);
    }

    public void startTheGame() {
        if (JOptionPane.showConfirmDialog(null,
                "Welcome to Tic Tac Toe game. You will be playing against computer.\n Let's start the game?", null, JOptionPane.YES_NO_OPTION)
                != JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        inputUserName();
    }

    public void inputUserName() {
        int flag = 0;
        String name;
        do {
            name = JOptionPane.showInputDialog("Enter your name", "");
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a name");
            } else {
                flag = 1;
            }
        } while (flag == 0);
        user.setName(name);
    }

    // Redraw the board
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        Graphics2D g2d = (Graphics2D) g;

        // Draw the grid
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Double(0, 0, w, h));
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(lineThickness));
        g2d.draw(new Line2D.Double(0, h / 3, w, h / 3));
        g2d.draw(new Line2D.Double(0, h * 2 / 3, w, h * 2 / 3));
        g2d.draw(new Line2D.Double(w / 3, 0, w / 3, h));
        g2d.draw(new Line2D.Double(w * 2 / 3, 0, w * 2 / 3, h));

        // Draw the Os and Xs
        for (int i = 0; i < 9; ++i) {
            double xpos = (i % 3 + 0.5) * w / 3.0;
            double ypos = (i / 3 + 0.5) * h / 3.0;
            double xr = w / 8.0;
            double yr = h / 8.0;
            if (position[i] == O) {
                g2d.setPaint(user.getColor());
                g2d.draw(new Ellipse2D.Double(xpos - xr, ypos - yr, xr * 2, yr * 2));
            } else if (position[i] == X) {
                g2d.setPaint(comp.getColor());
                g2d.draw(new Line2D.Double(xpos - xr, ypos - yr, xpos + xr, ypos + yr));
                g2d.draw(new Line2D.Double(xpos - xr, ypos + yr, xpos + xr, ypos - yr));
            }
        }
    }

    // Return true if the player has won
    boolean won(char player) {
        for (int i = 0; i < 8; ++i)
            if (testRow(player, rows[i][0], rows[i][1]))
                return true;
        return false;
    }

    // Has player won in the row from position[a] to position[b]?
    boolean testRow(char player, int a, int b) {
        return position[a] == player && position[b] == player
                && position[(a + b) / 2] == player;
    }

    // Return 0-8 for the position of a blank spot in a row if the
    // other 2 spots are occupied by player, or -1 if no spot exists
    int findRow(char player) {
        for (int i = 0; i < 8; ++i) {
            int result = find1Row(player, rows[i][0], rows[i][1]);
            if (result >= 0)
                return result;
        }
        return -1;
    }

    // If 2 of 3 spots in the row from position[a] to position[b]
    // are occupied by player and the third is blank, then return the
    // index of the blank spot, else return -1.
    int find1Row(char player, int a, int b) {
        int c = (a + b) / 2;  // middle spot
        if (position[a] == player && position[b] == player && position[c] == BLANK)
            return c;
        if (position[a] == player && position[c] == player && position[b] == BLANK)
            return b;
        if (position[b] == player && position[c] == player && position[a] == BLANK)
            return a;
        return -1;
    }

    //Check if all the spots are filled with values
    boolean isDraw() {
        for (int i = 0; i < 9; ++i)
            if (position[i] == BLANK)
                return false;
        return true;
    }

    // Start a new game
    void newGame(char winner) {

        // repaint the board
        repaint();

        // Announce result of last game.  Ask user if he wants to play again play again.
        if (winner == O) {
            wins++;
        } else if (winner == X) {
            ++losses;
        } else {
            ++draws;
        }
        if (JOptionPane.showConfirmDialog(null,
                user.getName() + " has " + wins + " wins, " + losses + " losses, " + draws + " draws\n"
                        + "Play again?", "Result", JOptionPane.YES_NO_OPTION)
                != JOptionPane.YES_OPTION) {
            System.exit(0);
        }

        // Clear the board to start a new game
        for (int j = 0; j < 9; ++j)
            position[j] = BLANK;

        // Computer starts first every other game
        if ((wins + losses + draws) % 2 == 1)
            nextMove();
    }

    // Computer plays X
    void playX() {

        // Check if game is over
        if (won(O))
            newGame(O);
        else if (isDraw()) {
            newGame(BLANK);
        }

        // Play X
        else {
            nextMove();
            if (won(X)) {
                newGame(X);
            } else if (isDraw()) {
                newGame(BLANK);
            }
        }
    }

    // Play X in the best spot
    void nextMove() {
        int r = findRow(X);  // complete any row of X and win if possible
        if (r < 0)
            r = findRow(O);  // Try to block O from winning
        if (r < 0) {  // otherwise move randomly
            do
                r = random.nextInt(9);
            while (position[r] != BLANK);
        }
        position[r] = X;
    }


    // Draw a O where the mouse is clicked
    @Override
    public void mouseClicked(MouseEvent e) {
        int xpos = e.getX() * 3 / getWidth();
        int ypos = e.getY() * 3 / getHeight();
        int pos = xpos + 3 * ypos;
        if (pos >= 0 && pos < 9 && position[pos] == BLANK) {
            position[pos] = O;
            repaint();
            playX();  // computer plays
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}