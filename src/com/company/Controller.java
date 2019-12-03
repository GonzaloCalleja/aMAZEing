package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener, ActionListener, ChangeListener {

    private MazeModel mazeModel;
    private ViewGame view;
    private MazeGenerator generator;
    private Thread mainThread;

    public Controller(MazeModel mazeModel, ViewGame view){
        this.mazeModel = mazeModel;
        this.view=view;
        this.mainThread = Thread.currentThread();
        view.addKeyListener(this);
        view.newGame.addActionListener(this);
        view.difficultyList.addActionListener(this);
        view.returnButton.addActionListener(this);
        view.shadowCheck.addChangeListener(this);
        generator = new MazeGenerator();
    }

    public void startApp(){
        view.createAndShowGUI();
    }

    public void updateNewGame(){
        mazeModel.score = System.currentTimeMillis();
        view.updateNewGame(mazeModel);
    }

    public void updatePlayer(){
        view.updatePlayer(mazeModel.player, mazeModel.labyrinth.dimension, mazeModel.shadow);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(!mazeModel.playing){
        }
        else if( mazeModel.player.moving){
        }
        else{
            mazeModel.player.moving = true;
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_DOWN) {
                if(!mazeModel.labyrinth.getCell(mazeModel.player.row, mazeModel.player.col).bottomWall) mazeModel.player.row++;
            }
            if (key == KeyEvent.VK_UP) {
                if(!mazeModel.labyrinth.getCell(mazeModel.player.row, mazeModel.player.col).topWall) mazeModel.player.row--;
            }
            if (key == KeyEvent.VK_LEFT) {
                if(!mazeModel.labyrinth.getCell(mazeModel.player.row, mazeModel.player.col).leftWall) mazeModel.player.col--;
            }
            if (key == KeyEvent.VK_RIGHT) {
                if(!mazeModel.labyrinth.getCell(mazeModel.player.row, mazeModel.player.col).rightWall) mazeModel.player.col++;
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

            if(mazeModel.labyrinth.getCell(mazeModel.player.row, mazeModel.player.col).finish){
                mazeModel.score = System.currentTimeMillis() - mazeModel.score;
                if(mazeModel.score > MazeModel.TIME_LIMIT){
                    mazeModel.score = MazeModel.LOSING_SCORE;
                }
                view.congratulationsMessage(mazeModel.score);
                mazeModel.playing= false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ViewGame.COMBO_BOX_ACTION)){
            JComboBox cb = (JComboBox) e.getSource();
            mazeModel.chosenDificulty = MazeModel.DIFICULTY_LIST_NUMBERS[cb.getSelectedIndex()];
        }else if(e.getActionCommand().equals(ViewGame.NEW_GAME_BUTTON_ACTION)){
            mazeModel.newGame(generator.generateLabyrinth(mazeModel.chosenDificulty));
            updateNewGame();
        }else if(e.getActionCommand().equals(ViewGame.RETURN_TO_MENU_ACTION)){
            mazeModel.score = System.currentTimeMillis() - mazeModel.score;
            if(mazeModel.score > MazeModel.TIME_LIMIT){
                mazeModel.score = MazeModel.LOSING_SCORE;
            }
            view.backToMenu();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JCheckBox cb = (JCheckBox) e.getSource();
        if(cb.isSelected()){
            mazeModel.shadow = true;
        }else{
            mazeModel.shadow = false;
        }
    }
}
