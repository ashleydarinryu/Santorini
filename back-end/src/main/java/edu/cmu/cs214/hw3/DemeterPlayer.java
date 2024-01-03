package edu.cmu.cs214.hw3;

public class DemeterPlayer extends Player {

    private int moveNum;
    private Square firstSquare;

    public DemeterPlayer(int playerNum) {
        super(playerNum);
        this.moveNum = 1;
        this.firstSquare = null;
    }

    @Override
    public boolean isValidBuild(int w, Square s, String piece) {
        int x = s.getX();
        int y = s.getY();
        if (super.getBuildDone()) {
            return false;
        }
        if (moveNum == 2) {
            if (firstSquare.getX() == x && firstSquare.getY() == y) {
                System.out.println("Already built to this square.");
                return false; // can't place on the same square
            }
        }
        return super.isValidBuild(w, s, piece); // rest of the logic is the same
    }

    @Override
    public boolean build(int w, Square s, String piece) {
        s.buildTower(piece);
        if (this.moveNum == 1) {
            this.moveNum = 2;
            this.firstSquare = s;
        } else {
            this.moveNum = 1;
            super.setBuildDone(true);
        }
        return true;
    }

    @Override
    public boolean pass() {
        if (this.moveNum == 2) {
            this.moveNum = 1;
            super.setBuildDone(true);
            return true;
        }
        return false;
    }
}