package com.company;

public class Main {
    public static void main(String[] args) {
        Controller app = new Controller(new MazeModel(), new ViewGame());
        app.startApp();
    }
}
