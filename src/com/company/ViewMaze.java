package com.company;

import javax.swing.*;
import java.awt.*;

public class ViewMaze extends JPanel {

    //Controller controller;
    int cellWidth, cellHeight;
    Dimension expectedDimension;
    Cell[][] labyrinth;
    boolean playing;

    public ViewMaze(Dimension expectedDimension){

        if(expectedDimension.height <= expectedDimension.width){
            this.expectedDimension = new Dimension(expectedDimension.height, expectedDimension.height);
        }else{
            this.expectedDimension = new Dimension(expectedDimension.width, expectedDimension.width);
        }
        setOpaque(true);
        setPreferredSize(expectedDimension);
        setSize(expectedDimension);
        setBackground(GamePanel.BACKGROUND_COLOR);

        playing = false;

    }

    public void update(Cell[][] labyrinth) {
        this.labyrinth = labyrinth;
        playing = true;
        repaint();
    }

    public void paintComponent(Graphics g1){
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D)g1;

        if(playing) {
            cellWidth = expectedDimension.width/labyrinth.length;
            cellHeight = expectedDimension.height/labyrinth[0].length;

            g.setColor(GamePanel.WALL_COLOR);
            g.setStroke(new BasicStroke(GamePanel.MAZE_STROKE));

            for (int i=0;i<labyrinth.length;i++) {
                if(i==0){
                    g.setStroke(new BasicStroke(GamePanel.BORDER_STROKE));
                    g.drawLine(0, 0, expectedDimension.width, 0);
                    g.setStroke(new BasicStroke(GamePanel.MAZE_STROKE));
                }
                else if(i==labyrinth.length-1){
                    g.setStroke(new BasicStroke(GamePanel.BORDER_STROKE));
                    g.drawLine(0, expectedDimension.height, expectedDimension.width, expectedDimension.height);
                    g.setStroke(new BasicStroke(GamePanel.MAZE_STROKE));
                }
                for(int j=0; j<labyrinth[i].length;j++) {
                    int topX = labyrinth[i][j].column * cellWidth;
                    int topY = labyrinth[i][j].row * cellHeight;

                    if (labyrinth[i][j].topWall) g.drawLine(topX, topY, topX + cellWidth, topY);
                    if (labyrinth[i][j].bottomWall) g.drawLine(topX, topY + cellHeight, topX + cellWidth, topY + cellHeight);
                    if (labyrinth[i][j].rightWall) g.drawLine(topX + cellWidth, topY, topX + cellWidth, topY + cellHeight);
                    if (labyrinth[i][j].leftWall) g.drawLine(topX, topY, topX, topY + cellHeight);

                    if(labyrinth[i][j].start) {
                        g.setColor(GamePanel.ENTRY_COLOR);
                        g.fillRect(topX, topY, cellWidth, cellHeight);
                        g.setColor(GamePanel.WALL_COLOR);
                    } else if(labyrinth[i][j].finish) {
                        g.setColor(GamePanel.EXIT_COLOR);
                        g.fillRect(topX, topY, cellWidth, cellHeight);
                        g.setColor(GamePanel.WALL_COLOR);
                    }
                    if(j==0){
                        g.setStroke(new BasicStroke(GamePanel.BORDER_STROKE));
                        g.drawLine(0, 0, 0, expectedDimension.height);
                        g.setStroke(new BasicStroke(GamePanel.MAZE_STROKE));
                    }
                    else if(j==labyrinth[i].length-1){
                        g.setStroke(new BasicStroke(GamePanel.BORDER_STROKE));
                        g.drawLine(expectedDimension.width, 0, expectedDimension.width, expectedDimension.height);
                        g.setStroke(new BasicStroke(GamePanel.MAZE_STROKE));
                    }
                }
            }

        }
    }
}

class ViewPlayer extends JPanel{

    Player player;
    boolean playing;
    int currentPlayerViewX, currentPlayerViewY;
    int paintX, paintY, cellWidth, cellHeight;
    int objectiveX, objectiveY;
    Dimension expectedDimension;
    int dimension;
    boolean shadow;
    Timer time;


    public ViewPlayer(Dimension expectedDimension) {

        this.expectedDimension = expectedDimension;

        setOpaque(false);
        setPreferredSize(expectedDimension);
        setSize(expectedDimension);

        playing = false;
        shadow = false;

        currentPlayerViewX=Player.DEFAULT_STARTING_COLUMN;
        currentPlayerViewY=Player.DEFAULT_STARTING_ROW;

        paintX=Player.DEFAULT_STARTING_COLUMN;
        paintY=Player.DEFAULT_STARTING_ROW;
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
            if(objectiveX!=paintX) Thread.sleep(Player.SPEED);
        }

        while(objectiveY!=paintY){
            if(paintY<objectiveY)paintY++;
            else paintY--;
            repaint();
            if(objectiveY!=paintY) Thread.sleep(Player.SPEED);
        }

        synchronized (this){
            player.moving = false;
            this.notify();
        }
    }

    public void update(Player player, int dimension, boolean shadow) {
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

        if(playing) {

            if(shadow){

                g.setColor(GamePanel.SHADE_COLOR);
                g.fillRect(GamePanel.BORDER_THICKNESS, GamePanel.BORDER_THICKNESS, expectedDimension.width-2*GamePanel.BORDER_THICKNESS, paintY-cellHeight);
                g.fillRect(GamePanel.BORDER_THICKNESS, GamePanel.BORDER_THICKNESS, paintX-cellWidth-2*GamePanel.BORDER_THICKNESS, expectedDimension.height-2*GamePanel.BORDER_THICKNESS );
                g.fillRect(paintX+2*cellWidth, GamePanel.BORDER_THICKNESS, expectedDimension.width-(paintX+2*cellWidth)-2*GamePanel.BORDER_THICKNESS, expectedDimension.height-2*GamePanel.BORDER_THICKNESS);
                g.fillRect(GamePanel.BORDER_THICKNESS, paintY+2*cellHeight, expectedDimension.width-GamePanel.BORDER_THICKNESS*2, expectedDimension.height-(paintY+2*cellHeight)-2*GamePanel.BORDER_THICKNESS);

                g.setColor(GamePanel.ENTRY_COLOR);
                g.fillRect(0, 0, cellWidth, cellHeight);
                g.setColor(GamePanel.EXIT_COLOR);
                g.fillRect((dimension-1)*cellWidth, (dimension-1)*cellHeight, cellWidth, cellHeight);
            }

            g.setColor(GamePanel.PLAYER_COLOR);
            g.fillOval(paintX+cellWidth/4, paintY+cellHeight/4, cellWidth/2, cellHeight/2);
        }
    }

    public void jumpUpdate(Player player, int length, boolean shadow) {
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
