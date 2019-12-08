package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ViewGame extends JPanel {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int MARGIN = 120;

    static final Color BACKGROUND_COLOR = new Color(67,67,67);
    static final Color TRANSPARENT_COLOR = new Color(67, 67, 67, 80);
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
    static final int ICON_SIZE = 10;

    static final String APPLICATION_NAME = "aMAZEing";
    static final String NEW_GAME_BUTTON_ACTION = "New Game";
    static final String COMBO_BOX_ACTION = "Difficulty List";
    static final String RETURN_TO_MENU_ACTION = "Return to Menu";
    static final String LOSS_MESSAGE = "You took too long!";
    static final String VICTORY_MESSAGE = "Score: ";
    static final String CONGRATULATIONS = "CONGRATULATIONS";
    static final String PATH_TO_ICON = "/resources/icon.png";
    static final String PATH_TO_BACKGROUND_IMAGE = "/resources/LabyrinthBackground.png";

    static final Font MENU_FONT = new Font("Consolas", Font.BOLD, 50);
    static final Font SMALL_MENU_ITEM = new Font("Verdana", Font.BOLD, 20);
    static final Font MEDIUM_MENU_ITEM = new Font("Verdana", Font.BOLD, 30);


    JFrame gameFrame;
    JButton newGame, returnButton;
    JLabel title, score, congratulations, scoreMenu;
    ViewMaze viewMaze;
    ViewPlayer viewPlayer;
    JPanel endGame, mazeAndPlayerGUIContainer, menu, gameMargin, background;
    JComboBox difficultyList;
    JCheckBox shadowCheck;

    public ViewGame(){

        Dimension expectedDimension;
        if(WINDOW_HEIGHT-MARGIN <= WINDOW_WIDTH-MARGIN){
            expectedDimension = new Dimension(WINDOW_HEIGHT-MARGIN, WINDOW_HEIGHT-MARGIN);
        }else{
            expectedDimension = new Dimension(WINDOW_WIDTH-MARGIN, WINDOW_WIDTH-MARGIN);
        }

        mainPanelSettings();

        JLayeredPane userInterfaceLayers = new JLayeredPane();
        userInterfaceLayers.setPreferredSize(this.getPreferredSize());

        setUpBackgroundImage();
        userInterfaceLayers.add(background, JLayeredPane.DEFAULT_LAYER);

        setUpMazeAndPlayerContainerAndView(expectedDimension);
        userInterfaceLayers.add(mazeAndPlayerGUIContainer, JLayeredPane.PALETTE_LAYER);

        setUpMainMenu();
        userInterfaceLayers.add(menu, JLayeredPane.MODAL_LAYER);

        setUpMarginSeenWhenPlaying();
        userInterfaceLayers.add(gameMargin, JLayeredPane.POPUP_LAYER);

        setUpEndGameScreen(expectedDimension);
        userInterfaceLayers.add(endGame, JLayeredPane.DRAG_LAYER);

        add(userInterfaceLayers, BorderLayout.CENTER);
    }

    public void createAndShowGUI(){

        gameFrame = new JFrame(APPLICATION_NAME);
        gameFrame.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        gameFrame.getContentPane().add(this);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource(PATH_TO_ICON)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameFrame.setIconImage(icon);
        gameFrame.setVisible(true);

    }

    public void updateNewGame(MazeModel mazeModel){
        mazeAndPlayerGUIContainer.setVisible(true);
        gameMargin.setVisible(true);
        menu.setVisible(false);
        endGame.setVisible(true);
        score.setText("0");
        scoreMenu.setText("0");
        repaint();
        viewMaze.update(mazeModel.labyrinth);
        viewPlayer.jumpUpdate(mazeModel.player, mazeModel.labyrinth.dimension, mazeModel.shadow);
    }

    public void updatePlayer(PlayerModel player, int dimension, boolean shadow){
        viewPlayer.update(player,  dimension, shadow);
    }

    public void backToMenu(){
        endGame.setVisible(false);
        mazeAndPlayerGUIContainer.setVisible(false);
        gameMargin.setVisible(false);
        menu.setVisible(true);
        congratulations.setVisible(false);
        repaint();
    }

    public void congratulationsMessage(Long score){
        endGame.setVisible(true);
        mazeAndPlayerGUIContainer.setVisible(true);
        congratulations.setVisible(true);
        viewPlayer.playing = false;
        viewMaze.playing = false;

        if(score == MazeModel.LOSING_SCORE){
            this.congratulations.setText(LOSS_MESSAGE);
        }

        this.score.setText(VICTORY_MESSAGE + score);
        scoreMenu.setText(score.toString());

        repaint();
    }

    private void mainPanelSettings() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        requestFocus();
    }

    private void setUpBackgroundImage() {
        Image backgroundPicture = null;
        try {
            backgroundPicture = ImageIO.read(getClass().getResource(PATH_TO_BACKGROUND_IMAGE)).getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel pictureHolderLabel = new JLabel(new ImageIcon(backgroundPicture));
        background = new JPanel();
        background.setSize(this.getPreferredSize());
        background.add(pictureHolderLabel);
    }

    private void setUpMazeAndPlayerContainerAndView(Dimension expectedDimension) {
        mazeAndPlayerGUIContainer = new JPanel(new GridBagLayout());
        mazeAndPlayerGUIContainer.setPreferredSize(this.getPreferredSize());
        mazeAndPlayerGUIContainer.setSize(this.getPreferredSize());
        mazeAndPlayerGUIContainer.setOpaque(true);
        mazeAndPlayerGUIContainer.setBackground(BACKGROUND_COLOR);
        mazeAndPlayerGUIContainer.setVisible(false);

        JLayeredPane layeredContainer = new JLayeredPane();
        layeredContainer.setPreferredSize(expectedDimension);
        layeredContainer.setSize(expectedDimension);

        viewMaze = new ViewMaze(expectedDimension);
        viewPlayer = new ViewPlayer(expectedDimension);

        layeredContainer.add(viewMaze, JLayeredPane.PALETTE_LAYER);
        layeredContainer.add(viewPlayer, JLayeredPane.MODAL_LAYER);
        mazeAndPlayerGUIContainer.add(layeredContainer);
    }

    private void setUpMainMenu() {
        menu = new JPanel();
        menu.setSize(this.getPreferredSize());
        menu.setVisible(true);
        menu.setOpaque(false);
        menu.setLayout(null);

        difficultyList = new JComboBox(MazeModel.DIFICULTY_LIST);
        difficultyList.setActionCommand(COMBO_BOX_ACTION);
        difficultyList.setSelectedIndex(0);
        menu.add(difficultyList);

        scoreMenu = new JLabel();
        scoreMenu.setFont(MENU_FONT);
        scoreMenu.setHorizontalAlignment(JLabel.LEFT);
        scoreMenu.setForeground(TEXT_COLOR);
        scoreMenu.setText("0");
        menu.add(scoreMenu);

        newGame = new JButton();
        newGame.setActionCommand(NEW_GAME_BUTTON_ACTION);
        newGame.setFocusable(false);
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);
//        newGame.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                newGame.setContentAreaFilled(true);
//                newGame.setBackground(TRANSPARENT_COLOR);
//                repaint();
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                newGame.setOpaque(false);
//                newGame.setContentAreaFilled(false);
//                newGame.setBorderPainted(false);
//            }
//        });
        menu.add(newGame);

        shadowCheck = new JCheckBox();
        shadowCheck.setOpaque(false);
        menu.add(shadowCheck);

        difficultyList.setBounds(770, 333, 100, 25);
        scoreMenu.setBounds(770, 250, 300, 42);
        newGame.setBounds(500, 480, 200, 40);
        shadowCheck.setBounds(770, 400, 100, 25);
    }

    private void setUpMarginSeenWhenPlaying() {
        gameMargin = new JPanel();
        gameMargin.setSize(this.getPreferredSize());
        gameMargin.setVisible(false);
        gameMargin.setOpaque(false);
        gameMargin.setLayout(null);

        title = new JLabel();
        title.setText(APPLICATION_NAME);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setFont(MENU_FONT);
        title.setForeground(TEXT_COLOR);
        gameMargin.add(title);

        returnButton = new JButton();
        returnButton.setActionCommand(RETURN_TO_MENU_ACTION);
        returnButton.setFocusable(false);
        returnButton.setText("Return");
        returnButton.setForeground(TEXT_COLOR);
        returnButton.setFont(MENU_FONT);
        returnButton.setContentAreaFilled(false);
        returnButton.setBorderPainted(false);
        gameMargin.add(returnButton);

        title.setBounds(330, 0, 500, 60);
        returnButton.setBounds(880, 450, 200, 50);
    }

    private void setUpEndGameScreen(Dimension expectedDimension) {

        endGame = new JPanel(new BorderLayout());
        endGame.setSize(this.getPreferredSize());
        endGame.setPreferredSize(this.getPreferredSize());
        endGame.setOpaque(false);
        endGame.setVisible(false);
        endGame.setLayout(null);

        congratulations = new JLabel();
        congratulations.setText(CONGRATULATIONS);
        congratulations.setSize(expectedDimension);
        congratulations.setHorizontalAlignment(JLabel.LEFT);
        congratulations.setVerticalAlignment(JLabel.CENTER);
        congratulations.setFont(SMALL_MENU_ITEM);
        congratulations.setForeground(Color.white);
        congratulations.setVisible(false);
        endGame.add(congratulations);

        score = new JLabel();
        score.setText(VICTORY_MESSAGE + "0");
        score.setSize(expectedDimension);
        score.setHorizontalAlignment(JLabel.LEFT);
        score.setVerticalAlignment(JLabel.BOTTOM);
        score.setFont(MEDIUM_MENU_ITEM);
        score.setForeground(Color.white);
        endGame.add(score);

        congratulations.setBounds(913, 200, 300, 50);
        score.setBounds(913, 100, 400, 200);
    }

}
