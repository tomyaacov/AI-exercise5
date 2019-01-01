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
<u>Non-trivial example:</u><br>
Graph:<br>
V 4 ; <br>
V 1 F 0.9;<br>
V 2 F 0.0001 ;<br>
V 3 F 0.0001 ;<br>
V 4 F 0.9 ;<br>
<br>
E1 1 2 W9 ;<br>
E2 2 3 W9 ;<br>
E3 3 4 W9 ;<br>
E4 4 1 W1 ;<br>
<br>
Resulting Bayes Network:<br>
Flooding 1<br>
P(Flooding 1) = 0.9<br>
P(not Flooding 1) = 0.09999999999999998<br>
<br>
Flooding 2<br>
P(Flooding 2) = 1.0E-4<br>
P(not Flooding 2) = 0.9999<br>
<br>
Flooding 3<br>
P(Flooding 3) = 1.0E-4<br>
P(not Flooding 3) = 0.9999<br>
<br>
Flooding 4<br>
P(Flooding 4) = 0.9<br>
P(not Flooding 4) = 0.09999999999999998<br>
<br>
Blockage 1-2<br>
P(Blockage 1-2|Flooding 1, Flooding 2) = 0.12888888888888883<br>
P(not Blockage 1-2|Flooding 1, Flooding 2) = 0.8711111111111112<br>
P(Blockage 1-2|Flooding 1, not Flooding 2) = 0.06666666666666665<br>
P(not Blockage 1-2|Flooding 1, not Flooding 2) = 0.9333333333333333<br>
P(Blockage 1-2|not Flooding 1, Flooding 2) = 0.06666666666666665<br>
P(not Blockage 1-2|not Flooding 1, Flooding 2) = 0.9333333333333333<br>
P(Blockage 1-2|not Flooding 1, not Flooding 2) = 0.001<br>
P(not Blockage 1-2|not Flooding 1, not Flooding 2) = 0.999<br>
<br>
Blockage 2-3<br>
P(Blockage 2-3|Flooding 2, Flooding 3) = 0.12888888888888883<br>
P(not Blockage 2-3|Flooding 2, Flooding 3) = 0.8711111111111112<br>
P(Blockage 2-3|Flooding 2, not Flooding 3) = 0.06666666666666665<br>
P(not Blockage 2-3|Flooding 2, not Flooding 3) = 0.9333333333333333<br>
P(Blockage 2-3|not Flooding 2, Flooding 3) = 0.06666666666666665<br>
P(not Blockage 2-3|not Flooding 2, Flooding 3) = 0.9333333333333333<br>
P(Blockage 2-3|not Flooding 2, not Flooding 3) = 0.001<br>
P(not Blockage 2-3|not Flooding 2, not Flooding 3) = 0.999<br>
<br>
Blockage 4-1<br>
P(Blockage 4-1|Flooding 4, Flooding 1) = 0.84<br>
P(not Blockage 4-1|Flooding 4, Flooding 1) = 0.16000000000000003<br>
P(Blockage 4-1|Flooding 4, not Flooding 1) = 0.6<br>
P(not Blockage 4-1|Flooding 4, not Flooding 1) = 0.4<br>
P(Blockage 4-1|not Flooding 4, Flooding 1) = 0.6<br>
P(not Blockage 4-1|not Flooding 4, Flooding 1) = 0.4<br>
P(Blockage 4-1|not Flooding 4, not Flooding 1) = 0.001<br>
P(not Blockage 4-1|not Flooding 4, not Flooding 1) = 0.999<br>
<br>
Blockage 3-4<br>
P(Blockage 3-4|Flooding 3, Flooding 4) = 0.12888888888888883<br>
P(not Blockage 3-4|Flooding 3, Flooding 4) = 0.8711111111111112<br>
P(Blockage 3-4|Flooding 3, not Flooding 4) = 0.06666666666666665<br>
P(not Blockage 3-4|Flooding 3, not Flooding 4) = 0.9333333333333333<br>
P(Blockage 3-4|not Flooding 3, Flooding 4) = 0.06666666666666665<br>
P(not Blockage 3-4|not Flooding 3, Flooding 4) = 0.9333333333333333<br>
P(Blockage 3-4|not Flooding 3, not Flooding 4) = 0.001<br>
P(not Blockage 3-4|not Flooding 3, not Flooding 4) = 0.999<br>
<br>
Evacuees 1<br>
P(Evacuees 1|Blockage 1-2, Blockage 4-1) = 0.88<br>
P(not Evacuees 1|Blockage 1-2, Blockage 4-1) = 0.12<br>
P(Evacuees 1|Blockage 1-2, not Blockage 4-1) = 0.8<br>
P(not Evacuees 1|Blockage 1-2, not Blockage 4-1) = 0.19999999999999996<br>
P(Evacuees 1|not Blockage 1-2, Blockage 4-1) = 0.4<br>
P(not Evacuees 1|not Blockage 1-2, Blockage 4-1) = 0.6<br>
P(Evacuees 1|not Blockage 1-2, not Blockage 4-1) = 0.001<br>
P(not Evacuees 1|not Blockage 1-2, not Blockage 4-1) = 0.999<br>
<br>
Evacuees 2<br>
P(Evacuees 2|Blockage 1-2, Blockage 2-3) = 0.96<br>
P(not Evacuees 2|Blockage 1-2, Blockage 2-3) = 0.040000000000000036<br>
P(Evacuees 2|Blockage 1-2, not Blockage 2-3) = 0.8<br>
P(not Evacuees 2|Blockage 1-2, not Blockage 2-3) = 0.19999999999999996<br>
P(Evacuees 2|not Blockage 1-2, Blockage 2-3) = 0.8<br>
P(not Evacuees 2|not Blockage 1-2, Blockage 2-3) = 0.19999999999999996<br>
P(Evacuees 2|not Blockage 1-2, not Blockage 2-3) = 0.001<br>
P(not Evacuees 2|not Blockage 1-2, not Blockage 2-3) = 0.999<br>
<br>
Evacuees 3<br>
P(Evacuees 3|Blockage 2-3, Blockage 3-4) = 0.96<br>
P(not Evacuees 3|Blockage 2-3, Blockage 3-4) = 0.040000000000000036<br>
P(Evacuees 3|Blockage 2-3, not Blockage 3-4) = 0.8<br>
P(not Evacuees 3|Blockage 2-3, not Blockage 3-4) = 0.19999999999999996<br>
P(Evacuees 3|not Blockage 2-3, Blockage 3-4) = 0.8<br>
P(not Evacuees 3|not Blockage 2-3, Blockage 3-4) = 0.19999999999999996<br>
P(Evacuees 3|not Blockage 2-3, not Blockage 3-4) = 0.001<br>
P(not Evacuees 3|not Blockage 2-3, not Blockage 3-4) = 0.999<br>
<br>
Evacuees 4<br>
P(Evacuees 4|Blockage 3-4, Blockage 4-1) = 0.88<br>
P(not Evacuees 4|Blockage 3-4, Blockage 4-1) = 0.12<br>
P(Evacuees 4|Blockage 3-4, not Blockage 4-1) = 0.8<br>
P(not Evacuees 4|Blockage 3-4, not Blockage 4-1) = 0.19999999999999996<br>
P(Evacuees 4|not Blockage 3-4, Blockage 4-1) = 0.4<br>
P(not Evacuees 4|not Blockage 3-4, Blockage 4-1) = 0.6<br>
P(Evacuees 4|not Blockage 3-4, not Blockage 4-1) = 0.001<br>
P(not Evacuees 4|not Blockage 3-4, not Blockage 4-1) = 0.999<br>
<br>
Scenario 1:<br>
Empty evidence list<br>
Output:<br>
1. What is the probability that each of the vertices contains evacuees?<br>
P(Evacuees 1) = 0.3479634490459189<br>
P(Evacuees 2) = 0.04979521775370518<br>
P(Evacuees 3) = 0.04979521775370519<br>
P(Evacuees 4) = 0.347963449045919<br>
2. What is the probability that each of the vertices is flooded?<br>
P(Flooding 1) = 0.9<br>
P(Flooding 2) = 1.0E-4<br>
P(Flooding 3) = 9.999999999999998E-5<br>
P(Flooding 4) = 0.9<br>
3. What is the probability that each of the edges is blocked?<br>
P(Blockage 1-2) = 0.060106256666666656<br>
P(Blockage 2-3) = 0.0010131332988888892<br>
P(Blockage 4-1) = 0.7884099999999999<br>
P(Blockage 3-4) = 0.060106256666666656<br>
4, What is the probability that a certain path (set of edges) is free from blockages? (Note that the distributions of blockages in edges are NOT necessarily independent.)<br>
Please enter path (example - 2413)<br>
1234<br>
0.8825060187867795. What is the path from a given location to a goal that has the highest probability of being free from blockages? (bonus)<br>
5<br>
Please enter start node<br>
1<br>
Please enter goal node<br>
4<br>
1234<br>
0.882506018786779<br>
14<br>
0.21159000000000006<br>
<br>
Scenario 2:<br>
evidence list: [Blockage 2-3]<br>
Output:<br>
1. What is the probability that each of the vertices contains evacuees?<br>
P(Evacuees 1) = 0.3481856282327973<br>
P(Evacuees 2) = 0.8096818788006577<br>
P(Evacuees 3) = 0.8096818788006576<br>
P(Evacuees 4) = 0.34818562823279736<br>
2. What is the probability that each of the vertices is flooded?<br>
P(Flooding 1) = 0.9<br>
P(Flooding 2) = 0.006580860481242651<br>
P(Flooding 3) = 0.0065808604812426525<br>
P(Flooding 4) = 0.9<br>
3. What is the probability that each of the edges is blocked?<br>
P(Blockage 1-2) = 0.06051174250410975<br>
P(Blockage 2-3) = 1.0<br>
P(Blockage 4-1) = 0.78841<br>
P(Blockage 3-4) = 0.06051174250410976<br>
4, What is the probability that a certain path (set of edges) is free from blockages? (Note that the distributions of blockages in edges are NOT necessarily independent.)<br>
Please enter path (example - 2413)<br>
1234<br>
0.0<br>
5. What is the path from a given location to a goal that has the highest probability of being free from blockages? (bonus)<br>
Please enter start node<br>
1<br>
Please enter goal node<br>
4<br>
1234<br>
0.0<br>
14<br>
0.21158999999999994<br>

<br>
<u>Running the program:</u><br>
In order to run our program run the Main class, and you'll get the starting menu as described in the assignment description.
<br>

