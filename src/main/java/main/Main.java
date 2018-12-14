package main;

import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.ui.view.Viewer;
import parser.Parser;
import simulator.Simulator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    @Getter @Setter
    private Parser parser;

    @Getter @Setter
    HurricaneGraph graph;

    //TODO: add bayes network and avidence list

    public void run(){
        initialize();
        Viewer view = graph.display();
        runMenu();

    }

    private void runMenu() {
        while(true) {
        }
        }


    private void initialize(){

        Scanner input = new Scanner(System.in);
        parser = new Parser();
        graph = initializeGraph();
        //TODO: initialize bayes network and evidence list here

        System.out.println("Welcome to Assignment 4 menu");
    }


    private HurricaneGraph initializeGraph() {
        try {
            return parser.parseFile("src.main.resources.graph".replace(".", File.separator));
        } catch (IOException e) {
            System.err.println("parsing 'graph.txt' file encountered a problem. Check for valid input");
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }
}
