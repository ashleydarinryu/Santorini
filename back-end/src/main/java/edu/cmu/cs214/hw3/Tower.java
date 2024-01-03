package edu.cmu.cs214.hw3;

import java.util.List;
import java.util.ArrayList;

public class Tower {
    private int level;
    private final ArrayList<String> pieces;
    public static final int FOUR = 4;

    /**
     * Creates a new {@link Tower} instance.
     */
    public Tower() {
        this.level = 0;
        this.pieces = new ArrayList<>();
    }

    /**
     * @return the level of tower
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Adds a block or a dome to tower
     * 
     * @param piece the type of piece
     */
    public void addPiece(String piece) {
        this.pieces.add(piece);
        this.level++;
    }

    /**
     * @return an array of pieces (block/dome)
     *         from bottom to top
     */
    public ArrayList<String> getPieces() {
        return this.pieces;
    }

    /**
     * @return true if the tower has a dome
     *         (is complete)
     */
    public boolean hasDome() {
        return this.level == FOUR;
    }
}