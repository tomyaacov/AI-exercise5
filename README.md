# Introduction to Artificial Intelligence - Assignment 5
#####Avi Rosen 303079511<br>
#####Tom Yaacov 305578239<br>
<br>
The assignment is written in java as a maven project.
<br>
We used GraphStream open source package for graph representaion.
<br><br>


#####Methods Overview:<br>

Belief state is composed of the following state variables:<br>
     Location<br>
     People at vertex v<br>
     Blocked edge e<br>
     People saved <br>
     Time <br>
     Carrying<br>
The reward can be given only at the end (terminal state), so we have R(s)=0 for all states, except for:<br>
     R(X, X, X, X, X, Sa, Deadline, X) = Sa    ; where X stands for "don't care".<br>
Transition probabilities are deterministic (i.e. in {0, 1}) except when the resulting location is adjacent to one or more edges with state unknown, in which case its state becomes blocked or not, depending on the blocakge probability.<br>  
We used Value iteration algorithm in order to find the optimal policy. 


#####Running the program:<br>
In order to run our program run the Main class, and you'll get a full printout of the value of each belief-state, and the optimal action in that belief state, if it exists. In addition, you'll get an option to run a sequence of simulations. 
<br>

