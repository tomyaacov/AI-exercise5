package main;


import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String PROBABILITY_INFERENCE= "Please enter query type:\n" +
            "1. What is the probability that each of the vertices contains evacuees?\n" +
            "2. What is the probability that each of the vertices is flooded?\n" +
            "3. What is the probability that each of the edges is blocked?\n" +
            "4, What is the probability that a certain path (set of edges) is free from blockages? (Note that the distributions of blockages in edges are NOT necessarily independent.)\n" +
            "5. What is the path from a given location to a goal that has the highest probability of being free from blockages? (bonus)";


    @Getter @Setter
    private Parser parser;


    @Getter @Setter
    HurricaneGraph graph;


    public void run(){
        initialize();
        Viewer view = graph.display();
        runMenu();

    }

    private void runMenu() {
        Scanner input = new Scanner(System.in);
        while(true) {
            printMenu();
        }
        }

    private void printMenu() {
        System.out.println("Please choose from the following options\n1.Reset evidence list to empty.\n2.Add piece of evidence to evidence list.\n3.Do probabilistic reasoning and report the results.\n4.Quit.");
    }


    private void initialize(){
        System.out.println("Welcome to Assignment 4!");
        Scanner input = new Scanner(System.in);
        parser = new Parser();
        graph = initializeGraph();
    }


    private HurricaneGraph initializeGraph() {
        try {
            return parser.parseFile("src.main.resources.non_trivial1".replace(".", File.separator));
        } catch (IOException e) {
            printMenu();
            System.exit(1);
        }
        return null;
    }


    public static void main(String[] args) {
//        System.out.println(f(new LinkedList<>()));


        Main s= new Main();

        s.run();
    }

}

