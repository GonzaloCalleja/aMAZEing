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
## 6.2. Libraries
## 6.3. Architecture
## 6.4. Classes
## 6.5. Logic of the Code
## 6.6. Maze generator Algorithm
Following the recursive backtracking, the algorithm chooses a starting point in the field. If the current cell, which receives the term as a "visited" cell, has any unvisited neighbors, it randomly choose one and remove the wall between them. So, this becomes the new current cell and therefore, a visited cell. The algorithm ends when the process has backed all the way up to the starting point.


