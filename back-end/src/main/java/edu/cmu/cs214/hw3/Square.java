package edu.cmu.cs214.hw3;

public class Square {
    private final Tower tower;
    private Worker worker;
    private final int x;
    private final int y;
    private String text;

    /**
     * Creates a new {@link Square} instance.
     *
     * @param x The x coordinate of the board
     * @param y The y coordinate of the board
     */
    public Square(int x, int y) {
        this.tower = new Tower();
        this.worker = null;
        this.x = x;
        this.y = y;
        this.text = "";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Worker getWorker() {
        return this.worker;
    }

    /**
     * @return the level of the tower at this square
     */
    public int getLevel() {
        return this.tower.getLevel();
    }

    /**
     * Checks if two squares are neighboring each other,
     * including diagonal neighbors
     * 
     * @param x the x of square to check
     * @param y the y of square to check
     * @return true if squares are adjacent
     */
    public boolean isAdjacent(int x, int y) {
        int otherX = x;
        int otherY = y;

        if (this.x == otherX && this.y == otherY)
            return false;
        if (this.x - otherX > 1 || this.x - otherX < -1)
            return false;
        if (this.y - otherY > 1 || this.y - otherY < -1)
            return false;
        return true;
    }

    public void setText(String str) {
        this.text = str;
    }

    /**
     * Sets if this square has a worker on it or not
     * 
     * @param w the worker on the square
     */
    public void setWorker(Worker w) {
        this.worker = w;
    }

    /**
     * @return true if the square has a worker on it
     *         or if there is a tower with a dome on it.
     */
    public boolean isOccupied() {
        return (tower.hasDome() || this.worker != null);
    }

    public boolean isComplete() {
        return tower.hasDome();
    }

    /**
     * Adds a piece to the tower on this square
     * 
     * @param p the type of piece
     */
    public void buildTower(String p) {
        this.tower.addPiece(p);
    }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",
                    "x": %d,
                    "y": %d,
                    "occupied": %b,
                    "isComplete": %b
                }
                """.formatted(this.text, this.x, this.y, this.isOccupied(), this.isComplete());
    }
}