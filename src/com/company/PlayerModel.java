package com.company;

class PlayerModel {
    static final int SPEED = 3;
    static final int DEFAULT_STARTING_ROW = 0;
    static final int DEFAULT_STARTING_COLUMN = 0;
    int row, col;
    boolean moving;
    public PlayerModel(int row, int col){
        this.row = row;
        this.col = col;
        moving=false;
    }

    public void startingPosition() {
        row = DEFAULT_STARTING_ROW;
        col = DEFAULT_STARTING_COLUMN;
    }
}