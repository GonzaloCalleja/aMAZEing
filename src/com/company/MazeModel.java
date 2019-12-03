package com.company;

class MazeModel {

    static final int EASY_DIMENSION = 10;
    static final int MEDIUM_DIMENSION = 20;
    static final int DIFFICULT_DIMENSION = 30;
    static final int EXTREME_DIMENSION = 40;

    static final String[] DIFICULTY_LIST = new String[]{"Easy", "Medium", "Dificult", "Extreme"};
    static final int[] DIFICULTY_LIST_NUMBERS = new int[]{EASY_DIMENSION, MEDIUM_DIMENSION, DIFFICULT_DIMENSION, EXTREME_DIMENSION};

    static final int TIME_LIMIT = 30000;
    static final long LOSING_SCORE = 0;

    static final boolean SHADOW_DEFAULT = false;

    PlayerModel player;
    Labyrinth labyrinth;
    boolean playing;
    boolean shadow;
    int chosenDificulty;

    Long score;

    public MazeModel(){
        playing = false;
        shadow=SHADOW_DEFAULT;
        chosenDificulty = EASY_DIMENSION;
        score = (long) 0;
    }

    public void newGame(Labyrinth labyrinth) {
        playing = true;
        score = System.currentTimeMillis();
        if(player==null) player = new PlayerModel(PlayerModel.DEFAULT_STARTING_ROW, PlayerModel.DEFAULT_STARTING_COLUMN);
        else player.startingPosition();
        this.labyrinth = labyrinth;
    }
}