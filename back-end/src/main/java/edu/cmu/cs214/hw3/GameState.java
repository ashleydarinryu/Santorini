package edu.cmu.cs214.hw3;

import java.util.Arrays;

public final class GameState {

    private final Square[] squares;
    private final int winner;
    private final int turn;
    private final boolean moveDone;
    private final int workerSelected;
    private final boolean p1w1initialized;
    private final boolean p1w2initialized;
    private final boolean p2w1initialized;
    private final boolean p2w2initialized;

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    private static final int BOARD_SIZE = 5;

    private GameState(Square[] squares, int turn, int winner, boolean md, int ws, boolean p1w1, boolean p1w2,
            boolean p2w1, boolean p2w2) {
        this.squares = squares;
        this.winner = winner;
        this.turn = turn;
        this.moveDone = md;
        this.workerSelected = ws;
        this.p1w1initialized = p1w1;
        this.p1w2initialized = p1w2;
        this.p2w1initialized = p2w1;
        this.p2w2initialized = p2w2;
    }

    public static GameState forGame(SantoriniGame game) {
        Square[] squares = getSquares(game);
        return new GameState(squares, game.getCurrentPlayer(), game.getWinner(), game.isMoveDone(),
                game.getWorkerSelected(),
                game.isp1w1Initialized(), game.isp1w2Initialized(), game.isp2w1Initialized(), game.isp2w2Initialized());
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return """
                {
                    "board": %s,
                    "turn": %d,
                    "winner": %d,
                    "moveDone": %b,
                    "workerSelected": %d,
                    "p1w1initialized": %b,
                    "p1w2initialized": %b,
                    "p2w1initialized": %b,
                    "p2w2initialized": %b
                }
                """.formatted(Arrays.toString(this.squares), this.turn, this.winner, this.moveDone, this.workerSelected,
                this.p1w1initialized, this.p1w2initialized, this.p2w1initialized, this.p2w2initialized);
    }

    private static Square[] getSquares(SantoriniGame game) {
        Square[] squares = new Square[BOARD_SIZE * BOARD_SIZE];
        Board board = game.getBoard();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                String text;
                Square sq = board.getSquare(x, y);
                int level = sq.getLevel();
                if (level == ZERO)

                    text = " ";
                else if (level == ONE)
                    text = "[ ]";
                else if (level == TWO)
                    text = "[[ ]]";
                else if (level == THREE)
                    text = "[[[ ]]]";
                else
                    text = "[[[O]]]";

                Worker w = sq.getWorker();
                if (w != null)
                    if (w.getPlayerNum() == 1)
                        text = text.replace(" ", "1");
                    else
                        text = text.replace(" ", "2");
                sq.setText(text);

                squares[BOARD_SIZE * y + x] = sq;
            }
        }
        return squares;
    }
}

/*
 * / \
 * _______ [ ]
 * | | [ ]
 * | 1 | [ 1 ]
 * |_______|
 * 
 */