package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int MARGIN = 120;

    static final Color BACKGROUND_COLOR = new Color(67,67,67);
    static final Color SHADE_COLOR = BACKGROUND_COLOR;
    static final Color TEXT_COLOR = Color.white;
    static final Color WALL_COLOR = Color.white;
    static final Color ENTRY_COLOR = Color.gray;
    static final Color EXIT_COLOR = Color.gray;
    static final Color BORDER_WALL_COLOR = Color.white;
    static final Color PLAYER_COLOR = Color.white;

    static final int MAZE_STROKE = 2;
    static final int BORDER_STROKE = 4;
    static final int BORDER_THICKNESS = 2;

    static final String newGameButtonName = "New Game";
    static final String comboBoxName = "Dificulty List";
    static final String returnButtonName = "Return to Menu";

    JFrame gameFrame;
    JButton newGame, returnButton;
    JLabel title, score, congratulations, scoreMenu;
    ViewMaze viewMaze;
    ViewPlayer viewPlayer;
    JPanel endGame, container, menu, gameMargin, background;
    JComboBox dificultyList;


    public void updateNewGame(Model model){
        remove(endGame);
        container.setVisible(true);
        gameMargin.setVisible(true);
        menu.setVisible(false);
        repaint();
        viewMaze.update(model.labyrinth);
        viewPlayer.jumpUpdate(model.player, model.labyrinth.length, model.shadow);
    }

    public void backToMenu(){
        endGame.setVisible(false);
        container.setVisible(false);
        gameMargin.setVisible(false);
        menu.setVisible(true);
        repaint();
    }

    public void updatePlayer(Player player, int dimension, boolean shadow){
        viewPlayer.update(player,  dimension, shadow);
    }

    public void congratulationsMessage(Long score){
//        container.setVisible(false);
//        gameMargin.setVisible(false);
//        menu.setVisible(true);
        endGame.setVisible(true);
        viewPlayer.playing = false;
        viewMaze.playing = false;

        if(score == Model.LOSING_SCORE){
            this.congratulations.setText("You took too long!");
        }

        this.score.setText("Score: " + score);
        scoreMenu.setText(score+"");
        scoreMenu.setText(score+"");

        repaint();
    }

    public void createAndShowGUI(){

        Dimension expectedDimension = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

        gameFrame = new JFrame("aMAZEing");
        gameFrame.setSize(expectedDimension);

        gameFrame.getContentPane().add(this);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

    }

    public GamePanel(){
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        requestFocus();

        //setLayout(new BorderLayout(1, 1));

        JLayeredPane firstLayers = new JLayeredPane();
        firstLayers.setPreferredSize(this.getPreferredSize());

        gameMargin = new JPanel();
        gameMargin.setSize(this.getPreferredSize());
        gameMargin.setVisible(false);
        gameMargin.setOpaque(false);
        gameMargin.setLayout(null);
        firstLayers.add(gameMargin, JLayeredPane.DRAG_LAYER);

        menu = new JPanel();
        menu.setSize(this.getPreferredSize());
        menu.setVisible(true);
        menu.setOpaque(false);
        menu.setLayout(null);
        firstLayers.add(menu, JLayeredPane.MODAL_LAYER);

        dificultyList = new JComboBox(Model.DIFICULTY_LIST);
        dificultyList.setActionCommand(comboBoxName);
        dificultyList.setSelectedIndex(0);
        menu.add(dificultyList);
        dificultyList.setBounds(770, 333, 100, 25);


        scoreMenu = new JLabel();
        menu.add(scoreMenu);
        scoreMenu.setBounds(840, 250, 300, 42);
        scoreMenu.setFont(new Font("Consolas", Font.BOLD, 50));
        scoreMenu.setForeground(Color.white);

        container = new JPanel(new GridBagLayout());
        container.setPreferredSize(this.getPreferredSize());
        container.setSize(this.getPreferredSize());
        container.setOpaque(true);
        container.setBackground(BACKGROUND_COLOR);
        container.setVisible(false);

        JLayeredPane layeredContainer = new JLayeredPane();
        Dimension expectedDimension = null;
        if(WINDOW_HEIGHT-MARGIN <= WINDOW_WIDTH-MARGIN){
            expectedDimension = new Dimension(WINDOW_HEIGHT-MARGIN, WINDOW_HEIGHT-MARGIN);
        }else{
            expectedDimension = new Dimension(WINDOW_WIDTH-MARGIN, WINDOW_WIDTH-MARGIN);
        }
        layeredContainer.setPreferredSize(expectedDimension);
        layeredContainer.setSize(expectedDimension);

        viewMaze = new ViewMaze(expectedDimension);
        viewPlayer = new ViewPlayer(expectedDimension);

        layeredContainer.add(viewMaze, JLayeredPane.PALETTE_LAYER);
        layeredContainer.add(viewPlayer, JLayeredPane.MODAL_LAYER);
        container.add(layeredContainer);

        firstLayers.add(container, JLayeredPane.PALETTE_LAYER);

        Image myPicture = null;
        try {
            myPicture = ImageIO.read(getClass().getResource("/resources/LabyrinthBackground.png")).getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        background = new JPanel();
        background.setSize(this.getPreferredSize());
        background.add(picLabel);
        firstLayers.add(background, JLayeredPane.DEFAULT_LAYER);

        title = new JLabel();
        title.setText("aMAZEing");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setFont(new Font("Consolas", Font.BOLD, 50));
        title.setForeground(Color.white);
        gameMargin.add(title);
        title.setBounds(330, 0, 500, 60);

        returnButton = new JButton();
        returnButton.setActionCommand(returnButtonName);
        gameMargin.add(returnButton);
        returnButton.setFocusable(false);
        returnButton.setText("Return");
        returnButton.setForeground(Color.white);
        returnButton.setFont(new Font("Consolas", Font.BOLD, 40));
        returnButton.setBounds(900, 450, 200, 50);
        returnButton.setContentAreaFilled(false);
        returnButton.setBorderPainted(false);

        newGame = new JButton();
        newGame.setActionCommand(newGameButtonName);
        menu.add(newGame, BorderLayout.SOUTH);
        newGame.setFocusable(false);
        newGame.setBounds(500, 480, 200, 40);
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);

        endGame = new JPanel(new BorderLayout());
        endGame.setSize(this.getPreferredSize());
        endGame.setOpaque(false);
        endGame.setVisible(false);
        endGame.setLayout(null);

        congratulations = new JLabel();
        congratulations.setText("CONGRATULATIONS");
        congratulations.setSize(expectedDimension);
        congratulations.setHorizontalAlignment(JLabel.RIGHT);
        congratulations.setVerticalAlignment(JLabel.CENTER);
        congratulations.setFont(new Font("Verdana", Font.BOLD, 30));
        congratulations.setForeground(Color.white);
        endGame.add(congratulations);
        congratulations.setBounds(900, 200, 200, 50);

        score = new JLabel();
        score.setText("SCORE : 0");
        score.setSize(expectedDimension);
        score.setHorizontalAlignment(JLabel.RIGHT);
        score.setVerticalAlignment(JLabel.BOTTOM);
        score.setFont(new Font("Verdana", Font.BOLD, 30));
        score.setForeground(Color.white);
        endGame.add(score);
        endGame.setBounds(900, 300, 200, 50);;
        firstLayers.add(endGame, new Integer(500));

        add(firstLayers, BorderLayout.CENTER);
    }

}
