package edu.cmu.cs214.hw3;

public class Player {
    private static final int THREE = 3;
    private final Worker[] workers;
    private int movedWorker;
    private boolean w1initialized;
    private boolean w2initialized;
    private boolean win;
    private boolean buildDone;
    private int num;

    /**
     * Creates a new {@link Player} instance.
     * 
     * @param n the player's number
     */
    public Player(int n) {
        this.workers = new Worker[2];
        this.workers[0] = new Worker(n);
        this.workers[1] = new Worker(n);
        this.movedWorker = 2;
        this.w1initialized = false;
        this.w2initialized = false;
        this.win = false;
        this.buildDone = false;
        this.num = n;
    }

    /**
     * @return the array containing both workers
     */
    public Worker[] getWorkers() {
        return this.workers;
    }

    public boolean getWin() {
        return this.win;
    }

    public int getNum() {
        return this.num;
    }

    /**
     * @return the worker that was most recently moved
     */
    public int getMovedWorker() {
        return this.movedWorker;
    }

    /**
     * Sets the worker that just moved to a different square
     * 
     * @param w the worker that just moved
     */
    public void setMovedWorker(int w) {
        this.movedWorker = w;
    }

    public void setWin(boolean b) {
        this.win = b;
    }

    /**
     * @return true if both workers' locations are initialized
     */
    public boolean w1initialized() {
        return this.w1initialized;
    }

    public boolean w2initialized() {
        return this.w2initialized;
    }

    /**
     * Places worker on a square
     * 
     * @param w  the worker to place
     * @param sq the square to move the worker to
     */
    public void placeWorker(int w, Square sq) {
        Worker worker = this.workers[w - 1];
        worker.place(sq);
        this.setMovedWorker(w);
        sq.setWorker(worker);
    }

    /**
     * initializes workers' locations
     * 
     * @param w the worker to initialize
     * @param s the square to place the worker
     */
    public void initializeWorker(int w, Square s) {
        placeWorker(w, s);
        if (w == 1)
            this.w1initialized = true;
        else
            this.w2initialized = true;
    }

    public boolean move(int w, Square s, Board b) {
        // if (!isValidMove(player, w, x, y))
        // return false;
        Worker worker = this.getWorkers()[w - 1];
        this.placeWorker(w, s);
        if (worker.getLevel() == THREE)
            this.win = true;
        this.buildDone = false;
        return true;
    }

    public boolean build(int w, Square s, String piece) {
        // if (!isValidBuild(player, w, x, y, piece))
        // return false;
        s.buildTower(piece);
        this.buildDone = true;
        return true;
    }

    public boolean getBuildDone() {
        return this.buildDone;
    }

    public void setBuildDone(boolean b) {
        this.buildDone = b;
    }

    public boolean isValidMove(int w, Square s, Board b) {
        Worker worker;
        if (w == 1)
            worker = this.getWorkers()[0];
        else if (w == 2)
            worker = this.getWorkers()[1];
        else {
            worker = null; // numbers outside 1, 2
            System.out.println("Not the correct worker\n");
            return false;
        }
        int workerLevel = worker.getLevel();
        int squareLevel = s.getLevel();
        int x = s.getX();
        int y = s.getY();
        if (squareLevel - workerLevel > 1) {
            System.out.println("Cannot move up more than 1 level.\n");
            return false; // cannot move up more than 1 level
        }
        if (!worker.isAdjacent(x, y)) {
            System.out.println("Not an adjacent square.\n");
            return false; // new square must be adjacent
        }
        if (s.isOccupied()) {
            System.out.println("The square is occupied.\n");
            return false; // new square must not be occupied
        }
        return true;
    }

    public boolean isValidBuild(int w, Square s, String piece) {
        int x = s.getX();
        int y = s.getY();
        if (w != this.getMovedWorker()) {
            System.out.println("Not the moved worker.\n");
            return false;
        }
        Worker movedWorker = this.getWorkers()[w - 1];
        if (s.isOccupied()) { // the square must not be occupied
            System.out.println("The square is occupied.\n");
            return false;
        }
        if (!movedWorker.isAdjacent(x, y)) { // (x, y) is not adjacent to moved worker
            System.out.println("Not adjacent to moved worker.\n");
            return false;
        }
        if (!piece.equals("dome") && !piece.equals("block")) { // adds wrong piece to the correct block
            System.out.println("Not a valid piece.\n");
            return false;
        } else if ((piece.equals("dome") && s.getLevel() != THREE) || (piece.equals("block") && s.getLevel() > THREE)) {
            System.out.println("Not the correct piece for the level.\n"); // selecting correct blocks will be
                                                                          // implemented later
            return false;
        }
        return true;
    }

    public boolean pass() {
        return false;
    }
}