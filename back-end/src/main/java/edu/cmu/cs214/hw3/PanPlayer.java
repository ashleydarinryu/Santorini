package edu.cmu.cs214.hw3;

public class PanPlayer extends Player {
    private static final int THREE = 3;

    public PanPlayer(int num) {
        super(num);
    }

    @Override
    public boolean move(int w, Square s, Board b) {
        Worker worker = this.getWorkers()[w - 1];
        int currentLevel = worker.getLevel();
        int squareLevel = s.getLevel();
        this.placeWorker(w, s);
        if (worker.getLevel() == THREE || currentLevel - squareLevel >= 2)
            this.setWin(true);
        this.setBuildDone(false);
        return true;
    }
}