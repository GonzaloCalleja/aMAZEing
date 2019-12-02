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

# xecutive Summary
This project undertaken by our group aims to test our knowledge and application abilities of not only Programming concepts seen in but also programming itself. It tests to see if we can think and figure out problems in new ways. From a technical point of view, many challenges were faced such as developing of the algorithm, decoupling of the code, and getting the interactions among classes to function properly. 

# 1. Project Overview
Project “aMAZEing” consists of a game to show our understanding of Java and more generally Programming concepts. Our objective is to create a simple, fun and addictive video game in which a labyrinth is generated and end goal is to get out of the cave. 
Due to a tight time constraints, we needed to ensure integrity and consistency throughout the project so as to provide and ensure a solid outcome. 
# 2. Requirements
In this section we provide a detailed table of all tasks associated with the creation of the game and how important they are. We then proceed to prioritise them based on what we feel is most essential in the event that we do not have time to code everything and need to leave out functionality.


** Note: The priority scale is from 1 to 5, where 1 is the most important to have and 5 is the least important and should only be added if time permits.

# 3. Software Development Iterations:
We decided that, as this project is complex in terms of architecture, design and features, we will phase it through the use of  iterations thereby employing short term goals that can be achieved one by one. These iterations are placed in order where 1 refers to the first iteration and 10, the last.

# 4. Obstacles
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
## 6.1. IntelliJ and GitHub
## 6.2. Libraries
## 6.3. Logic of the code
## 6.4. Classes
## 6.5. Architecture
## 6.6. Algorithm
Following the recursive backtracking, the algorithm chooses a starting point in the field. If the current cell, which receives the term as a "visited" cell, has any unvisited neighbors, it randomly choose one and remove the wall between them. So, this becomes the new current cell and therefore, a visited cell. The algorithm ends when the process has backed all the way up to the starting point.


