import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.ui.view.Viewer;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    @Getter @Setter
    private Parser parser;


    @Getter @Setter
    HurricaneGraph graph;

    @Getter @Setter
    MDP mdp;


    public void run(){
        initialize();
        Viewer view = graph.display();
        mdp = new MDP(graph);
        mdp.initialize();
        Algorithm.ValueIteration(mdp, 1, 0.01);
        System.out.println(mdp);
        runSimulation();

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

    private void runSimulation(){
        System.out.println("Starting Simulation");
        State s = mdp.calculateInitState();
        s = mdp.getStates().get(mdp.getStates().indexOf(s));
        while (!s.isGoal() && s.getBestAction() != null){
            System.out.println(s);
            s = mdp.sampleNextState(s);
        }
        System.out.println(s);
    }


    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }

}

