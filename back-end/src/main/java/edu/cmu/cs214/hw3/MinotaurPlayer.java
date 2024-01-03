package edu.cmu.cs214.hw3;

public class MinotaurPlayer extends Player {
    private static final int FIVE = 5;

    public MinotaurPlayer(int playerNum) {
        super(playerNum);
    }

    private boolean validGrid(int x, int y) {
        return x < FIVE && x >= 0 && y < FIVE && y >= 0;
    }

    @Override
    public boolean isValidMove(int w, Square s, Board b) {
        Worker worker;
        int x = s.getX();
        int y = s.getY();
        if (w == 1)
            worker = this.getWorkers()[0];
        else if (w == 2)
            worker = this.getWorkers()[1];
        else {
            worker = null; // numbers outside 1, 2
            System.out.println("Not the correct worker\n");
            return false;
        }
        if (!worker.isAdjacent(x, y)) {
            System.out.println("Not an adjacent square.\n");
            return false; // new square must be adjacent
        }
        Worker workerAtSquare = s.getWorker();
        if (workerAtSquare != null && workerAtSquare.getPlayerNum() != this.getNum()) { // square has opponent's worker
            int xDiff = x - worker.getX();
            int yDiff = y - worker.getY();
            int newX = x + xDiff;
            int newY = y + yDiff;
            if (validGrid(newX, newY)) {
                Square newS = b.getSquare(newX, newY);
                if (!newS.isOccupied())
                    return true;
            }
            return false;
        }
        int workerLevel = worker.getLevel();
        int squareLevel = s.getLevel();
        if (squareLevel - workerLevel > 1) {
            System.out.println("Cannot move up more than 1 level.\n");
            return false; // cannot move up more than 1 level
        }
        if (s.isComplete()) {
            System.out.println("The square is complete.\n");
            return false; // new square must not be occupied
        }
        return true;
    }

    @Override
    public boolean move(int w, Square s, Board b) {
        Worker workerAtSquare = s.getWorker();
        Worker currentWorker = this.getWorkers()[w - 1];
        if (workerAtSquare != null) {
            int xDiff = s.getX() - currentWorker.getX();
            int yDiff = s.getY() - currentWorker.getY();
            int newX = s.getX() + xDiff;
            int newY = s.getY() + yDiff;
            Square newS = b.getSquare(newX, newY);
            workerAtSquare.place(newS);
            newS.setWorker(workerAtSquare);
            this.placeWorker(w, s);
            return true;
        }
        return super.move(w, s, b);
    }
}