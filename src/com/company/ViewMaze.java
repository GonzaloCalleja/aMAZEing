package com.company;

import javax.swing.*;
import java.awt.*;

public class ViewMaze extends JPanel {

    int cellWidth, cellHeight;
    Dimension expectedDimension;
    Labyrinth labyrinth;
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
        setBackground(ViewGame.BACKGROUND_COLOR);

        playing = false;

    }

    public void update(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
        playing = true;
        repaint();
    }

    public void paintComponent(Graphics g1){
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D)g1;

            cellWidth = expectedDimension.width/labyrinth.dimension;
            cellHeight = expectedDimension.height/labyrinth.dimension;

            g.setColor(ViewGame.WALL_COLOR);
            g.setStroke(new BasicStroke(ViewGame.MAZE_STROKE));

            for (int i=0;i<labyrinth.dimension;i++) {
                if(i==0){
                    g.setStroke(new BasicStroke(ViewGame.BORDER_STROKE));
                    g.drawLine(0, 0, expectedDimension.width, 0);
                    g.setStroke(new BasicStroke(ViewGame.MAZE_STROKE));
                }
                else if(i==labyrinth.dimension-1){
                    g.setStroke(new BasicStroke(ViewGame.BORDER_STROKE));
                    g.drawLine(0, expectedDimension.height, expectedDimension.width, expectedDimension.height);
                    g.setStroke(new BasicStroke(ViewGame.MAZE_STROKE));
                }
                for(int j=0; j<labyrinth.dimension;j++) {
                    int topX = labyrinth.getCell(i, j).column * cellWidth;
                    int topY = labyrinth.getCell(i, j).row * cellHeight;

                    if (labyrinth.getCell(i, j).topWall) g.drawLine(topX, topY, topX + cellWidth, topY);
                    if (labyrinth.getCell(i, j).bottomWall) g.drawLine(topX, topY + cellHeight, topX + cellWidth, topY + cellHeight);
                    if (labyrinth.getCell(i, j).rightWall) g.drawLine(topX + cellWidth, topY, topX + cellWidth, topY + cellHeight);
                    if (labyrinth.getCell(i, j).leftWall) g.drawLine(topX, topY, topX, topY + cellHeight);

                    if(j==0){
                        g.setStroke(new BasicStroke(ViewGame.BORDER_STROKE));
                        g.drawLine(0, 0, 0, expectedDimension.height);
                        g.setStroke(new BasicStroke(ViewGame.MAZE_STROKE));
                    }
                    else if(j==labyrinth.dimension-1){
                        g.setStroke(new BasicStroke(ViewGame.BORDER_STROKE));
                        g.drawLine(expectedDimension.width, 0, expectedDimension.width, expectedDimension.height);
                        g.setStroke(new BasicStroke(ViewGame.MAZE_STROKE));
                    }
                }
            }

    }
}
