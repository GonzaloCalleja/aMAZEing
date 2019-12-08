# SOFTWARE DEVELOPMENT PROJECT
## aMAZEing

_PREPARED FOR_

José Luis Martin Romera

joseluism@faculty.ie.edu 

_PREPARED BY_

Gonzalo Calleja Palacios, Clara Dubois Ribó, and Marlene Lantz

gcalleja.ieu2017@student.ie.edu
cdubois.ieu2017@student.ie.edu
mlantz.ieu2016@student.ie.edu  

# Executive Summary
This project undertaken by our group aims to test our knowledge and application abilities of not only Programming concepts seen in but also programming itself. It tests to see if we can think and figure out problems in new ways. From a technical point of view, many challenges were faced such as developing of the algorithm, decoupling of the code, and getting the interactions among classes to function properly. 

# 1. Project Overview
Project “aMAZEing” consists of a game to show our understanding of Java and more generally Programming concepts. Our objective is to create a simple, fun and addictive video game in which a labyrinth is generated and end goal is to get out of the cave. 
Due to a tight time constraints, we needed to ensure integrity and consistency throughout the project so as to provide and ensure a solid outcome. 
# 2. Requirements
In this section we provide a detailed table of all tasks associated with the creation of the game and how important they are. We then proceed to prioritise them based on what we feel is most essential in the event that we do not have time to code everything and need to leave out functionality.

![](https://user-images.githubusercontent.com/42964691/70002094-54fa7d00-155f-11ea-929a-dbcd60a57a50.png)
![](https://user-images.githubusercontent.com/42964691/70002086-52982300-155f-11ea-9261-b34d27d53537.png)


** Note: The priority scale is from 1 to 5, where 1 is the most important to have and 5 is the least important and should only be added if time permits.

# 3. Software Development Iterations:
We decided that, as this project is complex in terms of architecture, design and features, we will phase it through the use of  iterations thereby employing short term goals that can be achieved one by one. These iterations are placed in order where 1 refers to the first iteration and 10, the last.
![](https://user-images.githubusercontent.com/42964691/70002226-aacf2500-155f-11ea-9110-3d644ef4989e.png)

# 4. Scope
## In Scope
The scope of the project consists of creating a labyrinth game in which a player must find the start and end of the maze. Moreover the maze settings are generated randomly and a difficulty level can be chosen. The player cannot go through walls and has a “fog of war” lighting mode so as to make things more game-like in terms of objective and play style. 

## Out Scope
A 2 player option was considered, however due to time constraints was established to be outside of the scope. Moreover the online option of the video game is also outside the scope of this project. Lastly some additional functionalities that we did consider, however again, due to time constraints, are placed outside of the scope. These include: choosing of game themes, choosing of game “sprites”, saving and sharing of scores, etc. 

# 5. Obstacles
* Time Constraints
* Using new libraries (and learning how they work)
* Interaction between Classes
* Decoupling the Code
* Key Binding Activities
  * Key Binding vs. Key Listeners
* Graphics
* Game Options

The above are a list of technical obstacles that we either overcome through the sheer power of thinking and of course (some) google. Moreover some of the obstacles stated are challenges that we were not able to overcome, but given more time we would have been able to figure out a solution. In the future we think that a better mitigation strategy should have been delineated so as to ensure the success of all the features we wanted within the game. 

# 6. Software
_For further detail, please see our document_

## 6.1. IntelliJ and GitHub
Throughout this project, IntelliJ was used as our IDE (Intelligent Environment), and GitHub as our VCS (Version Control). This helped us keep the code neat, and allowed us to keep track of all changes to the code. Moreover, it facilitate collaboration as we were able to fork and clone projects to work on it from different locations. 

## 6.2. Libraries
For this project, we used the AWT and Swing libraries to design the GUI (Graphical User Interface), as for the rest of the programme, standard java was used without the need to import any additional libraries.

## 6.3. Classes
### 1. Main: 
Instantiates a Controller Object and passes a new MazeModel object and a new ViewMaze object through the constructor. Then it executes the method “startApp” for that instance of the controller.

### 2. Controller: 
It is the class that holds the logic for the game, and that listens to all the user actions (it is the only listener class in the program). As for instance variables, it holds the model, the view, the maze generator, and the programs’s main thread. Its methods are the key listeners (which hold the logic to moving the player on the screen and detecting when a game has finished), action listeners (which hold the logic for the “new game” and “return” buttons and for the difficulty list combo box), and stateChanged listener (which hold the logic for implementing the fog of war) that allow the user to interact with the program. On top of that, it holds the methods to update the player, and the maze, which is called in order for the program to update.

### 3. MazeModel: 
It is the class that holds the necessary variables for the game: the PlayerModel, the Labyrinth, the difficulty level chosen, whether there is fog of war and whether the game is currently playing or not. It's only method is to create a new Game.

### 4. PlayerModel: 
It holds the information for the player’s row, column and for whether the player is moving or not. It also holds some constants such as the player’s speed and the player’s default starting position. It's only method is to go back to the starting position.

### 5. Labyrinth: 
It holds an array of CellModel objects along with the labyrinths dimension (how many rows and columns it has) its methods serve to treat the single dimensional array as if it were a two dimensional one. (Which the Maze generator class uses).

### 6. CellModel: 
It holds all the necessary values for each individual cell in the labyrinth. It’s row and column, whether it is the start or finish of the labyrinth, four boolean values for the four walls, and whether the cell has been “created” by the MazeGenerator algorithm or simply instantiated.

### 7. ViewGame: 
This class is a subclass of JPanel which holds all GUI classes in the program (most of them as instance variables), and many GUI constants the program needs such as colors, fonts, stroke thicknesses, window width and height, the paths for the files used as icons and as background images, the messages the user sees on the screen, and the action command names of the buttons in the program. Its constructor is where all the GUI components are instantiated, and its structure is essentially a set of layered panes with each layer being one part of the GUI. First the background, which is the initial image. Secondly, the panel that contains the game itself. This panel uses the GridBagLayout to contain another set of Layered panes and place them always at the center of the screen, in the first layer of the container is the ViewMaze JPane, and in the second layer is the ViewPlayer JPane. Thirdly, the main menu pane, which holds all the elements seen in the main menu, and because of the layout properties of Swing, the elements position is hardcoded, the same happens with the fourth (margin seen while playing) and fifth (end screen seen when game finishes). A great advantage of using this layer system is that the panes that form the layout itself are transparent, so the layers can be stacked to form better effect.

The only methods in this class are those to change how the new layers are displayed, which also serve to update the content in the score labels for each of these changes. Those that serve to make the code readable ( the ones that are to setup the content in each of the layers ). And the createGUI method, which instantiates a JFrame and places the ViewGame JPane into it. The reason this class is a JPane subclass and does this extra step, and is not a JFrame directly is so that the game can easily be placed into a Java Applet or in other words, be easily displayed in a browser.

### 8. ViewPlayer: 
This is an extension of a JPane class that is in charge of drawing the player. The way this is done is by drawing the player one pixel closer to where it should be every 3 milliseconds. To be able to do this, ViewPlayer starts a new thread in its update method that sleeps for the right amount and then notifies the main thread (which has been waiting) when the player has reached the desired position. This is necessary so that the Main thread does not continue accepting player input while the player has not reached a new position.

### 9. ViewMaze: 
This is an extension of a JPane class that is in charge of drawing the maze. It essentially paints a set of four white lines for each cell in the Labyrinth, and thicker lines for the border.

### 10. MazeGenerator: 
This class contains the necessary logic to form a new labyrinth from a set of new Cells. The logic has been placed into a method that returns the modified labyrinth, and other maze generating algorithms can be added to this class by adding more methods to it. This means that the maze generating logic can be modified without changing the rest of the program.


## 6.4. Logic of the Code
The code has been divided into 3 different sections following the MVC design pattern:
*  Model: Holds all data about the game objects (player and maze)
*  View: Holds the knowledge on how to draw elements in the GUI
*  Controller: Listens to all GUI events (in other words, to the user) and updates the Model and View
*  Maze Generator: It is a class the controller calls to generate a new Labyrinth for every new Game.


## 6.5. Maze generator Algorithm
Following the recursive backtracking maze generating algorithm, it chooses a starting point in the field and adds it to a stack. Then it pops a cell from the start, and sets the created variable to true, then it checks whether the cell has any unvisited neighbors, it randomly chooses one of them and removes the wall between the current cell and the neighbour chosen. Then it pushes both cells back into the stack, and repeats the process again until the stack is empty. The algorithm works because cells are only pushed back into the array if they have unvisited neighbours, and since whenever it finds one such cell it changes that, the loop always finishes.

Copyright © 2019-2025 aMAZEing by Gonzalo Calleja, Clara Dubois and Marlene Lantz ®, All Rights Reserved
