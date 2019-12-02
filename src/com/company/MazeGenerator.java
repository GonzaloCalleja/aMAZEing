package com.company;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {

    private Cell[][] labyrinthCells;
    private int dimension;
    Random r;

    public Cell[][] generateLabyrinth(int dimension){
        this.dimension = dimension;
        this.labyrinthCells = new Cell[this.dimension][this.dimension];
        for(int i=0; i< this.dimension; i++){
            for(int j=0; j< this.dimension; j++){
                labyrinthCells[i][j] = new Cell(i, j);
            }
        }

        Stack<Cell> visitedCells = new Stack<>();
        visitedCells.push(labyrinthCells[0][0]);
        labyrinthCells[0][0].created = true;
        while(!visitedCells.empty()){
            Cell current = visitedCells.pop();
            if(hasUnvisitedNeighbours(current)){
                visitedCells.push(current);
                Cell neighbour = getRandomUnvisitedNeighbour(current);
                removeWall(current, neighbour);
                neighbour.created = true;
                visitedCells.push(neighbour);
            }
        }
        labyrinthCells[0][0].start = true;
        labyrinthCells[this.dimension-1][this.dimension-1].finish = true;

        return labyrinthCells;
    }

    public Cell[][] generateRecursiveLabyrinth(int dimension){
        r = new Random();
        this.dimension = dimension;
        this.labyrinthCells = new Cell[this.dimension][this.dimension];
        for(int col=0; col< this.dimension; col++){
            for(int row=0; row< this.dimension; row++){
                labyrinthCells[col][row] = new Cell(col, row);
                labyrinthCells[col][row].leftWall = false;
                labyrinthCells[col][row].rightWall = false;
                labyrinthCells[col][row].bottomWall = false;
                labyrinthCells[col][row].topWall = false;

                if(col==0) labyrinthCells[col][row].leftWall=true;
                if(col==this.dimension-1) labyrinthCells[col][row].rightWall=true;
                if(row==0) labyrinthCells[col][row].topWall=true;
                if(row==this.dimension-1) labyrinthCells[col][row].bottomWall=true;
            }
        }

        algorithm(0, 0, dimension, dimension);

        return labyrinthCells;
    }

    public void algorithm(int horStart, int vertStart, int horEnd, int vertEnd){

        int width = horEnd-horStart;
        int height = vertEnd-vertStart;

        if((width==1 && height == 1)){
            return;
        }

        int cut = 0;

        if(width>=height){
            cut = r.nextInt(width-1) + 1;

            for(int i= horStart; i<horEnd;i++){
                labyrinthCells[i][cut].topWall = true;
            }
            //algorithm(0,0, cut, vertEnd);
            //algorithm(horStart+cut, 0, horEnd, vertEnd);
        }else{
            cut = r.nextInt(height-1) + 1;
            System.out.println("hh");

            for(int i= vertStart; i<vertEnd;i++){
                labyrinthCells[cut][i].leftWall = true;
            }
            //algorithm(0, 0, horEnd, cut);
            //algorithm(0, vertStart+cut, horEnd, vertEnd);
        }
        // divide chamber with randomly possitioned wall - horizontal if vertical vertical if horizontal
        // if square
        // call method for two halves
//
//        int horizontal = 0;
//        int vertical = 0;
//
//        while((horizontal==0 || horizontal==hor-horStart)){
//            System.out.print(" hor: "+hor+" horizontal: " + horizontal);
//            horizontal = r.nextInt(hor-horStart);
//            if(hor-horStart==1) break;
//        }
//        while((vertical==0 || vertical==vert-vertStart)){
//            System.out.println("vert: " +vert);
//            vertical = r.nextInt(vert-vertStart);
//            if(vert-vertStart==1) break;
//        }
//
//        for(int i=horStart; i<hor; i++){
//            labyrinthCells[vertStart+vertical][i].leftWall= true;
//        }
//        for(int i=vertStart; i<vert; i++){
//            labyrinthCells[i][horStart+horizontal].topWall= true;
//        }
//
//        algorithm(0, 0, vertical, horizontal);
        //algorithm(0, vert, dimension , horizontal);
        //algorithm(hor, 0, vertical, dimension);
        //algorithm(horizontal, vertical, dimension, dimension);
    }

    private Cell getRandomUnvisitedNeighbour(Cell cell) {
        Cell[] neighbours = getNeighbours(cell);
        Random r = new Random();
        Cell result = null;
        while(result == null || result.created){
            result = neighbours[r.nextInt(4)];
        }
        return result;
    }

    private boolean hasUnvisitedNeighbours(Cell cell) {
        Cell[] neighbours = getNeighbours(cell);
        for (Cell n : neighbours){
            if(n != null && !n.created) return true;
        }
        return false;
    }

    private void removeWall(Cell cell1, Cell cell2) {
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

    public Cell getCell(int row, int column){
        if(row < 0 || column < 0 || row >= dimension || column >= dimension)
            return null;
        return labyrinthCells[row][column];
    }

    public Cell[] getNeighbours(Cell cell){
        return new Cell[]{
                getCell(cell.row -1, cell.column),
                getCell(cell.row +1, cell.column),
                getCell(cell.row, cell.column+1),
                getCell(cell.row , cell.column-1)
        };
    }
}

//public class MazeGenerator {
//
//    public Labyrinth generateLabyrinth(int dimension){
//
//        Labyrinth labyrinthCells = new Labyrinth(dimension);
//
//        Stack<Cell> visitedCellModels = new Stack<>();
//        visitedCellModels.push(labyrinthCells.labyrinth[0][0]);
//        labyrinthCells.labyrinth[0][0].created = true;
//        while(!visitedCellModels.empty()){
//            Cell current = visitedCellModels.pop();
//            if(labyrinthCells.hasUnvisitedNeighbours(current)){
//                visitedCellModels.push(current);
//                Cell neighbour = labyrinthCells.getRandomUnvisitedNeighbour(current);
//                labyrinthCells.removeWall(current, neighbour);
//                neighbour.created = true;
//                visitedCellModels.push(neighbour);
//            }
//        }
//        labyrinthCells.labyrinth[0][0].start = true;
//        labyrinthCells.labyrinth[dimension-1][dimension-1].finish = true;
//
//        return labyrinthCells;
//    }
//}