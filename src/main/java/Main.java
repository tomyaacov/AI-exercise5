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
        System.out.println("Running Value Iteration...");
        Algorithm.ValueIteration(mdp, 1, 0.0001);
        System.out.println(mdp);
        runTestingSimulations(10000);
        Scanner input = new Scanner(System.in);
        System.out.println("Run Simulation? (1-Yes, 0-No)");
        int typeNum = input.nextInt();
        while (typeNum == 1){
            runSimulation();
            System.out.println("Run Simulation? (1-Yes, 0-No)");
            typeNum = input.nextInt();
        }
    }


    private void initialize(){
        System.out.println("Welcome to Assignment 5!");
        Scanner input = new Scanner(System.in);
        parser = new Parser();
        graph = initializeGraph();
    }


    private HurricaneGraph initializeGraph() {
        try {
            return parser.parseFile("src.main.resources.graph2".replace(".", File.separator));
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
        System.out.println("Final Reward: " + s.getPeopleSaved());
    }

    private void runTestingSimulations(int numOfSimulations){
        List<Integer> resultList = new ArrayList<>();
        for (int i=0; i<numOfSimulations; i++){
            State s = mdp.calculateInitState();
            s = mdp.getStates().get(mdp.getStates().indexOf(s));
            while (!s.isGoal() && s.getBestAction() != null){
                s = mdp.sampleNextState(s);
            }
            resultList.add(s.getPeopleSaved());
        }
        OptionalDouble average = resultList
                .stream()
                .mapToDouble(a -> a)
                .average();
        System.out.println("Result of running " + numOfSimulations + " simulations: " + average);
    }


    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }

}

