package edu.cmu.cs214.hw3;

public class Board {
    private final Square[][] squares;
    private final int rows = 5;
    private final int cols = 5;

    /**
     * Creates a new {@link Board} instance.
     */
    public Board() {
        this.squares = new Square[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++)
                this.squares[i][j] = new Square(i, j);
        }
    }

    /**
     * @param x the x coord.
     * @param y the y coord.
     * @return the square at (x, y)
     */
    public Square getSquare(int x, int y) {
        return this.squares[x][y];
    }

    /**
     * @return the number of rows
     */
    public int getRows() {
        return this.rows;
    }

    public int getLevelOf(int x, int y) {
        Square s = getSquare(x, y);
        return s.getLevel();
    }

    public boolean isOccupied(int x, int y) {
        Square s = getSquare(x, y);
        return s.isOccupied();
    }

    /**
     * @return the number of cols
     */
    public int getCols() {
        return this.cols;
    }

}
