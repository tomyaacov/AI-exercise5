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

    //TODO: add bayes network and evidence list

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

        Scanner input = new Scanner(System.in);
        parser = new Parser();
        graph = initializeGraph();
        //TODO: initialize bayes network and evidence list here

        printMenu();
    }


    private HurricaneGraph initializeGraph() {
        try {
            return parser.parseFile("src.main.resources.graph".replace(".", File.separator));
        } catch (IOException e) {
            printMenu();
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }
}

