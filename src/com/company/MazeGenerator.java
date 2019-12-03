package com.company;

import java.util.Stack;

public class MazeGenerator {

    public Labyrinth generateLabyrinth(int dimension){

        Labyrinth labyrinth = new Labyrinth(dimension);

        Stack<CellModel> visitedCells = new Stack<>();
        visitedCells.push(labyrinth.getCell(0, 0));
        labyrinth.getCell(0, 0).created = true;
        while(!visitedCells.empty()){
            CellModel current = visitedCells.pop();
            if(labyrinth.hasUnvisitedNeighbours(current)){
                visitedCells.push(current);
                CellModel neighbour = labyrinth.getRandomUnvisitedNeighbour(current);
                removeWall(current, neighbour);
                neighbour.created = true;
                visitedCells.push(neighbour);
            }
        }
        labyrinth.getCell(0, 0).start = true;
        labyrinth.getCell(dimension-1, dimension-1).finish = true;

        return labyrinth;
    }

    private void removeWall(CellModel cell1, CellModel cell2) {
        if(cell1.row == cell2.row+1 && cell1.column == cell2.column) {
            cell1.topWall = false;
            cell2.bottomWall = false;
        }
        else if(cell1.row+1 == cell2.row && cell1.column == cell2.column) {
            cell1.bottomWall = false;
            cell2.topWall = false;
        }
        else if(cell1.row == cell2.row && cell1.column == cell2.column+1) {
            cell1.leftWall = false;
            cell2.rightWall = false;
        }
        else if(cell1.row == cell2.row && cell1.column+1 == cell2.column) {
            cell1.rightWall = false;
            cell2.leftWall = false;
        }
    }
}
