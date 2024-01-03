package edu.cmu.cs214.hw3;

public class Worker {
    private Square square;
    private final int playerNum;

    /**
     * Creates a new {@link Worker} instance.
     * 
     * @param player player of the worker
     */
    public Worker(int player) {
        this.square = null; // not placed on board yet
        this.playerNum = player;
    }

    public int getPlayerNum() {
        return this.playerNum;
    }

    /**
     * @return the level of the tower in the worker's square
     */
    public int getLevel() {
        return this.square.getLevel();
    }

    /**
     * @return the square that the worker is on
     */
    public Square getSquare() {
        return this.square;
    }

    public int getX() {
        Square s = this.square;
        return s.getX();
    }

    public int getY() {
        Square s = this.square;
        return s.getY();
    }

    /**
     * Places this worker on a square
     * 
     * @param sq the square to place the worker on
     */
    public void place(Square sq) {
        if (this.square != null)
            this.square.setWorker(null);
        this.square = sq;
    }

    /**
     * Checks if the worker is adjacent to a square
     * 
     * @param x x of square to check if adjacent
     * @param y y of square to check if adjacent
     * @return true if the worker's square is adjacent to sq
     */
    public boolean isAdjacent(int x, int y) {
        return this.square.isAdjacent(x, y);
    }

}