package com.company;

public class CellModel {
    boolean created, start, finish;
    int row, column;
    boolean topWall, bottomWall, rightWall, leftWall;
    public CellModel(int row, int col){
        this.row = row;
        this.column = col;
        topWall= true;
        bottomWall=true;
        rightWall=true;
        leftWall=true;
        created=false;
        start=false;
        finish=false;}
}
