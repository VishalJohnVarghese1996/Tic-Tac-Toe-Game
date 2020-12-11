package com.visvar.game;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.visvar.game.Board.*;

public class Game {
    Board board;


    public Game(Board board){
        this.board = board;
    }

    // Ignore other mouse events
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}




}
