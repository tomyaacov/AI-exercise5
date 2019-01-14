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
import java.security.AlgorithmConstraints;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    @Getter @Setter
    private Parser parser;


    @Getter @Setter
    HurricaneGraph graph;

//    @Getter @Setter
//    MDP mdp;


    public void run(){
        initialize();
        Viewer view = graph.display();
//        mdp = new MDP(graph);
//        mdp.initialize();
//        Algorithm.
    }


    private void initialize(){
        System.out.println("Welcome to Assignment 5!");
        Scanner input = new Scanner(System.in);
        parser = new Parser();
        graph = initializeGraph();
    }


    private HurricaneGraph initializeGraph() {
        try {
            return parser.parseFile("src.main.resources.graph".replace(".", File.separator));
        } catch (IOException e) {
            System.exit(1);
        }
        return null;
    }


    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }

}

