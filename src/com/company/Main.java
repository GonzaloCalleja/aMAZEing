package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    public static void main(String[] args) {
        Controller app = new Controller(new Model(), new GamePanel());
        app.startApp();
    }
}

class Player{
    static final int SPEED = 5;
    static final int DEFAULT_STARTING_ROW = 0;
    static final int DEFAULT_STARTING_COLUMN = 0;
    int row, col;
    boolean moving;
    public Player(int row, int col){this.row = row; this.col = col;moving=false;}

    public void startingPosition() {
        row = DEFAULT_STARTING_ROW;
        col = DEFAULT_STARTING_COLUMN;
    }
}

class Cell{
    boolean created, start, finish;
    int row, column;
    boolean topWall, bottomWall, rightWall, leftWall;
    public Cell(int row, int col){this.row = row; this.column = col;topWall= true;bottomWall=true;rightWall=true;leftWall=true;created=false;start=false;finish=false;}
}

class Model{

    static final int EASY_DIMENSION = 10;
    static final int MEDIUM_DIMENSION = 20;
    static final int DIFFICULT_DIMENSION = 30;
    static final int EXTREME_DIMENSION = 40;

    static final String[] DIFICULTY_LIST = new String[]{"Easy", "Medium", "Dificult", "Extreme"};
    static final int[] DIFICULTY_LIST_NUMBERS = new int[]{EASY_DIMENSION, MEDIUM_DIMENSION, DIFFICULT_DIMENSION, EXTREME_DIMENSION};

    static final int TIME_LIMIT = 300000;
    static final long LOSING_SCORE = 0;

    static final boolean SHADOW_DEFAULT = false;

    Player player;
    Cell[][] labyrinth;
    boolean playing;
    boolean shadow;
    int chosenDificulty;

    Long score;

    public Model(){
        playing = false;
        shadow=SHADOW_DEFAULT;
        chosenDificulty = EASY_DIMENSION;
        score = (long) 0;
    }

    public void newGame(Cell[][] labyrinth) {
        playing = true;
        score = System.currentTimeMillis();
        if(player==null) player = new Player(Player.DEFAULT_STARTING_ROW, Player.DEFAULT_STARTING_COLUMN);
        else player.startingPosition();
        this.labyrinth = labyrinth;
    }
}

class Controller implements KeyListener, ActionListener {

    Model model;
    GamePanel view;
    MazeGenerator generator;
    Thread mainThread;

    public Controller(Model model, GamePanel view){
        this.model=model;
        this.view=view;
        this.mainThread = Thread.currentThread();
        view.addKeyListener(this);
        view.newGame.addActionListener(this);
        view.dificultyList.addActionListener(this);
        view.returnButton.addActionListener(this);
        view.scoreMenu.setText(model.score+"");
        generator = new MazeGenerator();
    }

    public void startApp(){
        view.createAndShowGUI();
    }

    public void updateNewGame(){
        model.score = System.currentTimeMillis();
        view.updateNewGame(model);
    }
    public void updatePlayer(){
        view.updatePlayer(model.player, model.labyrinth.length, model.shadow);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(!model.playing){
        }
        else if( model.player.moving){
        }
        else{
            model.player.moving = true;
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_DOWN) {
                if(!model.labyrinth[model.player.row][model.player.col].bottomWall) model.player.row++;
            }
            if (key == KeyEvent.VK_UP) {
                if(!model.labyrinth[model.player.row][model.player.col].topWall) model.player.row--;
            }
            if (key == KeyEvent.VK_LEFT) {
                if(!model.labyrinth[model.player.row][model.player.col].leftWall) model.player.col--;
            }
            if (key == KeyEvent.VK_RIGHT) {
                if(!model.labyrinth[model.player.row][model.player.col].rightWall) model.player.col++;
            }

            updatePlayer();
            try {
                synchronized (view){
                    while(!view.viewPlayer.player.moving){
                        mainThread.wait();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if(model.labyrinth[model.player.row][model.player.col].finish){
                model.score = System.currentTimeMillis() - model.score;
                if(model.score > Model.TIME_LIMIT){
                    model.score = Model.LOSING_SCORE;
                }
                view.scoreMenu.setText(model.score+"");
                view.congratulationsMessage(model.score);
                view.repaint();
                System.out.println(model.score);
                model.playing= false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(GamePanel.comboBoxName)){
            JComboBox cb = (JComboBox) e.getSource();
            model.chosenDificulty = Model.DIFICULTY_LIST_NUMBERS[cb.getSelectedIndex()];
        }else if(e.getActionCommand().equals(GamePanel.newGameButtonName)){
            model.newGame(generator.generateLabyrinth(model.chosenDificulty));
            updateNewGame();
        }else if(e.getActionCommand().equals(GamePanel.returnButtonName)){
            model.score = System.currentTimeMillis() - model.score;
            if(model.score > Model.TIME_LIMIT){
                model.score = Model.LOSING_SCORE;
            }
            view.scoreMenu.setText(model.score+"");
            view.backToMenu();
        }
    }
}