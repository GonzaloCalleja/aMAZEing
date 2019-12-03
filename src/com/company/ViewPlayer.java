package com.company;

import javax.swing.*;
import java.awt.*;

class ViewPlayer extends JPanel {

    PlayerModel player;
    boolean playing;
    int currentPlayerViewX, currentPlayerViewY;
    int paintX, paintY, cellWidth, cellHeight;
    int objectiveX, objectiveY;
    Dimension expectedDimension;
    int dimension;
    boolean shadow;

    public ViewPlayer(Dimension expectedDimension) {

        this.expectedDimension = expectedDimension;

        setOpaque(false);
        setPreferredSize(expectedDimension);
        setSize(expectedDimension);

        playing = false;
        shadow = MazeModel.SHADOW_DEFAULT;

        currentPlayerViewX= PlayerModel.DEFAULT_STARTING_COLUMN;
        currentPlayerViewY= PlayerModel.DEFAULT_STARTING_ROW;

        paintX= PlayerModel.DEFAULT_STARTING_COLUMN;
        paintY= PlayerModel.DEFAULT_STARTING_ROW;
    }

    void tick() {
        Thread thread = new Thread("ehllo") {
            @Override
            public void run() {
                try {
                    move();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void move() throws InterruptedException {
        while(objectiveX!=paintX){
            if(paintX<objectiveX)paintX++;
            else paintX--;
            repaint();
            if(objectiveX!=paintX) Thread.sleep(PlayerModel.SPEED);
        }

        while(objectiveY!=paintY){
            if(paintY<objectiveY)paintY++;
            else paintY--;
            repaint();
            if(objectiveY!=paintY) Thread.sleep(PlayerModel.SPEED);
        }

        synchronized (this){
            player.moving = false;
            this.notify();
        }
    }

    public void update(PlayerModel player, int dimension, boolean shadow) {
        this.player = player;
        this.dimension = dimension;
        cellWidth = expectedDimension.width/dimension;
        cellHeight = expectedDimension.height/dimension;

        objectiveX = player.col*cellWidth;
        objectiveY = player.row*cellHeight;

        this.shadow = shadow;
        playing = true;

        tick();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(shadow && !(paintX==(dimension-1)*cellWidth && paintY==(dimension-1)*cellHeight)){

            g.setColor(ViewGame.SHADE_COLOR);
            g.fillRect(ViewGame.BORDER_THICKNESS, ViewGame.BORDER_THICKNESS, expectedDimension.width-2* ViewGame.BORDER_THICKNESS, paintY-cellHeight);
            g.fillRect(ViewGame.BORDER_THICKNESS, ViewGame.BORDER_THICKNESS, paintX-cellWidth-2* ViewGame.BORDER_THICKNESS, expectedDimension.height-2* ViewGame.BORDER_THICKNESS );
            g.fillRect(paintX+2*cellWidth, ViewGame.BORDER_THICKNESS, expectedDimension.width-(paintX+2*cellWidth)-2* ViewGame.BORDER_THICKNESS, expectedDimension.height-2* ViewGame.BORDER_THICKNESS);
            g.fillRect(ViewGame.BORDER_THICKNESS, paintY+2*cellHeight, expectedDimension.width- ViewGame.BORDER_THICKNESS*2, expectedDimension.height-(paintY+2*cellHeight)-2* ViewGame.BORDER_THICKNESS);
        }

        g.setColor(ViewGame.ENTRY_COLOR);
        g.fillRect(cellWidth/4, cellHeight/4, cellWidth/2, cellHeight/2);
        g.setColor(ViewGame.EXIT_COLOR);
        g.fillRect(cellWidth*(dimension-1)+cellWidth/4, cellHeight*(dimension-1)+cellWidth/4, cellWidth/2, cellHeight/2);

        g.setColor(ViewGame.PLAYER_COLOR);
        g.fillOval(paintX+cellWidth/4, paintY+cellHeight/4, cellWidth/2, cellHeight/2);
    }

    public void jumpUpdate(PlayerModel player, int length, boolean shadow) {
        this.player = player;
        this.dimension = length;
        this.shadow = shadow;
        playing = true;
        paintX=player.row;
        paintY=player.col;
        cellWidth = expectedDimension.width/dimension;
        cellHeight = expectedDimension.height/dimension;
        repaint();
    }
}
