package edu.cmu.cs214.hw3;

public class SantoriniGame {
    private int currentPlayer;
    private final Player[] players;
    private final Board board;
    private int winner;
    private boolean gameInProgress;
    private boolean moveDone;
    private int workerSelected;

    /**
     * Creates a new {@link SantoriniGame} instance(New round).
     */
    public SantoriniGame() {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = new Player(1);
        this.players[1] = new Player(2);
        this.currentPlayer = 1;
        this.winner = 0;
        this.gameInProgress = true;
        this.moveDone = false;
        this.workerSelected = 0;
    }

    public Player getGodCard(String s, int playerNum) {
        if (s.equals("Demeter"))
            return new DemeterPlayer(playerNum);
        else if (s.equals("Minotaur"))
            return new MinotaurPlayer(playerNum);
        else if (s.equals("Pan"))
            return new PanPlayer(playerNum);
        else
            return new Player(playerNum);
    }

    public SantoriniGame(String p1, String p2) {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = getGodCard(p1, 1);
        this.players[1] = getGodCard(p2, 2);
        this.currentPlayer = 1;
        this.winner = 0;
        this.gameInProgress = true;
        this.moveDone = false;
        this.workerSelected = 0;
    }

    /**
     * Initializes a player's workers' positions.
     * 
     * @param player the player that initializes the worker
     * @param worker the worker to initialize
     * @param x      the x coord.
     * @param y      the y coord.
     */
    public void initialize(int player, int worker, int x, int y) {
        if (player != 1 && player != 2) { // correct player should be passed in
            System.out.println("Not a player.\n");
            return;
        }
        Player p = this.players[player - 1];
        Square s = this.board.getSquare(x, y);
        if (s.isOccupied())
            return;
        p.initializeWorker(worker, s);
    }

    public boolean isp1w1Initialized() {
        return this.players[0].w1initialized();
    }

    public boolean isp1w2Initialized() {
        return this.players[0].w2initialized();
    }

    public boolean isp2w1Initialized() {
        return this.players[1].w1initialized();
    }

    public boolean isp2w2Initialized() {
        return this.players[1].w2initialized();
    }

    private boolean isInitialized() {
        return isp1w1Initialized() && isp1w2Initialized() && isp2w1Initialized() && isp2w2Initialized();
    }

    private boolean validGrid(int x, int y) {
        return x < this.board.getCols() && x >= 0 && y < this.board.getRows() && y >= 0;
    }

    /**
     * Checks if the attempted move is valid
     * 
     * @param player the player that wants to make a move
     * @param w      the worker that player wants to move
     * @param x      the x coord. that player wants to move the worker to
     * @param y      the y coord. that player wants to move the worker to
     * @return true if the move is valid
     */
    public boolean isValidMove(int player, int w, int x, int y) {
        if (!isInitialized()) {
            System.out.println("Workers' locations are not initialized yet.\n");
            return false;
        }
        if (!this.gameInProgress) {
            System.out.println("Game not in progress.\n");
            return false;
        }
        if (!validGrid(x, y)) {// (x, y) should be in range
            System.out.println("Invalid square.\n");
            return false;
        }
        if (player != this.currentPlayer) {// correct player is passed in
            System.out.println("Not this player's turn\n");
            return false;
        }
        if (this.moveDone) { // already moved worker
            System.out.println("Moved worker already, can't move again.\n");
            return false;
        }
        Player p = this.players[player - 1];
        Square s = this.board.getSquare(x, y);
        return p.isValidMove(w, s, board);
    }

    /**
     * Checks if the attempted build is valid
     * 
     * @param player the player that wants to build
     * @param w      worker that wants build
     * @param x      the x coord. of square that the player wants to build on
     * @param y      the y coord. of square that the player wants to build on
     * @param piece  the type of piece that the player wants to build
     * @return true if the attempted build is valid
     */
    public boolean isValidBuild(int player, int w, int x, int y, String piece) {
        if (!isInitialized()) {
            System.out.println("Workers' locations are not initialized yet.\n");
            return false;
        }
        if (!this.gameInProgress) {
            System.out.println("Game not in progress.\n");
            return false;
        }
        if (!validGrid(x, y)) {// (x, y) is out of range
            System.out.println("Invalid square.\n");
            return false;
        }
        if (player != this.currentPlayer) {// not the current player
            System.out.println("Not the current player.\n");
            return false;
        }
        if (!this.moveDone) {
            System.out.println("Worker not moved yet.\n");
            return false;
        }
        Player p = players[player - 1];
        Square s = this.board.getSquare(x, y);
        return p.isValidBuild(w, s, piece);
    }

    /**
     * A player moves a worker to (x, y) if valid
     * 
     * @param player the player that wants to make a move
     * @param w      the worker that player wants to move
     * @param x      the x coord. that player wants to move the worker to
     * @param y      the y coord. that player wants to move the worker to
     * @return true if the move was made successfully
     */
    public boolean move(int player, int w, int x, int y) {
        if (!isValidMove(player, w, x, y))
            return false;
        Player p = this.players[player - 1];
        Square s = this.board.getSquare(x, y);
        p.move(w, s, board);
        if (p.getWin()) {
            this.winner = this.currentPlayer;
            this.gameInProgress = false;
        }
        this.moveDone = true; // keep
        return true;
    }

    public void changeTurn() {
        Player p = this.players[this.currentPlayer - 1];
        if (p.getBuildDone()) {
            this.moveDone = false;
            this.workerSelected = 0;
            if (this.currentPlayer == 1) // change turn (keep)
                this.currentPlayer = 2;
            else
                this.currentPlayer = 1;
        }
    }

    /**
     * A player builds a block or a dome to (x, y) if valid
     * 
     * @param player the player that wants to build
     * @param w      the worker that wants to build
     * @param x      the x coord. that the player wants to build on
     * @param y      the y coord. that the player wants to build on
     * @param piece  the type of piece (block/dome)
     * @return true if the move was made successfully
     */
    public boolean build(int player, int w, int x, int y, String piece) {
        if (!isValidBuild(player, w, x, y, piece))
            return false;
        Square s = this.board.getSquare(x, y);
        Player p = this.players[player - 1];
        p.build(w, s, piece);
        this.changeTurn();
        return true;
    }

    public void pass() {
        Player p = this.players[this.currentPlayer - 1];
        if (p.pass())
            this.changeTurn();
    }

    /**
     * @return the winner if exists, 0 if no winner
     */
    public int getWinner() {
        return this.winner;
    }

    public boolean isMoveDone() {
        return this.moveDone;
    }

    public void selectWorker(int x, int y) {
        Square sq = this.board.getSquare(x, y);
        Player curr = this.players[this.getCurrentPlayer() - 1];
        Worker w = sq.getWorker();
        if (w == null) {
            return;
        } else if (w == curr.getWorkers()[0]) {
            this.workerSelected = 1;
        } else if (w == curr.getWorkers()[1]) {
            this.workerSelected = 2;
        }
    }

    public Board getBoard() {
        return this.board;
    }

    /**
     * For testing purposes, get the level at (x, y)
     * 
     * @param x the x coord.
     * @param y the y coord.
     * @return the level at (x, y) in board
     */
    public int getLevelAt(int x, int y) {
        Square s = this.board.getSquare(x, y);
        return s.getLevel();
    }

    public int getWorkerSelected() {
        return this.workerSelected;
    }

    /**
     * @return the player that has the current turn
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * @return true if the game is in progress,
     *         false if over
     */
    public boolean isGameInProgress() {
        return this.gameInProgress;
    }
}