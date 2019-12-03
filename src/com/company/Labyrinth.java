package com.company;

import java.util.Random;

class Labyrinth{
    private CellModel[] labyrinth;
    int dimension;

    public Labyrinth(int dimension){
        this.dimension = dimension;
        labyrinth = new CellModel[dimension*dimension];
        for(int row=0; row<dimension; row++){
            for(int col=0; col< dimension; col++){
                labyrinth[row*dimension + col] = new CellModel(row, col);
            }
        }
    }

    public CellModel getRandomUnvisitedNeighbour(CellModel cellModel) {
        CellModel[] neighbours = getNeighbours(cellModel);
        Random r = new Random();
        CellModel result = null;
        while(result == null || result.created){
            result = neighbours[r.nextInt(4)];
        }
        return result;
    }

    boolean hasUnvisitedNeighbours(CellModel cellModel) {
        CellModel[] neighbours = getNeighbours(cellModel);
        for (CellModel n : neighbours){
            if(n != null && !n.created) return true;
        }
        return false;
    }

    public CellModel getCell(int row, int column){
        if(row < 0 || column < 0 || row >= dimension || column >= dimension)
            return null;
        return labyrinth[row*dimension + column];
    }

    public CellModel[] getNeighbours(CellModel cellModel){
        return new CellModel[]{
                getCell(cellModel.row -1, cellModel.column),
                getCell(cellModel.row +1, cellModel.column),
                getCell(cellModel.row, cellModel.column+1),
                getCell(cellModel.row , cellModel.column-1)
        };
    }
}