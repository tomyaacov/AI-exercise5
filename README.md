# Introduction to Artificial Intelligence - Assignment 4
Avi Rosen 303079511<br>
Tom Yaacov 305578239<br>
<br>
The assignment is written in java as a maven project.
<br>
We used GraphStream open source package for graph representaion.
<br><br>

<u>Bayes Network Construction and Reasoning:</u><br>
Construction:<br>
Our program constructs the network in 2 main steps:<br>
1. Graph parser - the graph is being parsed in the same manner as the previous assignments (except some minor changes in order to keep probabilities of the network). 
2. Bayes parser - The network variables are initialized from the graph generated in the previous step and are added to the network.

Reasoning:<br>
Our reasonning algorithm uses the Enumeration algorithm. the algorithm computes the probability of a variable given a list of evidences and the network. I order to infer over multiple variables our program is using conjunctive queries chain rule and infer separately over the given variable and a changing list of evidences.
<br>
<br>
<u>Example:</u><br>

<br>
<br>
<u>Running the program:</u><br>
In order to run our program run the Main class, and you'll get the starting menu as described in the assignment description.
<br>

