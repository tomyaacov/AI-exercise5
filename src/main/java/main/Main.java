package main;

import bayes.BayesNetwork;
import bayes.Evidence;
import bayes.variables.Blockage;
import bayes.variables.Variable;
import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.ui.view.Viewer;
import parser.Parser;
import bayes.parser.BayesParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    @Getter @Setter
    private Parser parser;

    @Getter @Setter
    private BayesParser bayesParser;

    @Getter @Setter
    HurricaneGraph graph;

    @Getter @Setter
    BayesNetwork bayesNetwork;

    @Getter @Setter
    List<Evidence> evidenceList;


    public void run(){
        initialize();
        Viewer view = graph.display();
        runMenu();

    }

    private void runMenu() {
        Scanner input = new Scanner(System.in);
        while(true) {
            printMenu();
            int inputNum = input.nextInt();
            if (inputNum == 1) {
                evidenceList.clear();
            }
            if (inputNum == 2) {
                enterEvidence();
            }
            if (inputNum == 3) {
                doProbabilisticReasoning();
            }
            if (inputNum == 4) {
                System.out.println("Bye!");
                break;
            }
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
        bayesParser = new BayesParser();
        bayesNetwork = bayesParser.initBayes(graph);
        System.out.println("Resulting Bayes Network:\n" + bayesNetwork);
        evidenceList = new ArrayList<>();
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

    private void enterEvidence(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter variable type:\n1.Flooding\n2.Blockage\n3.Evacuees");
        int typeNum = input.nextInt();
        String typeString = getTypeString(typeNum);
        if (typeNum == 2){
            System.out.println("Please enter edge id (example 1-2):");
        } else {
            System.out.println("Please enter vertex id:");
        }
        String id = input.next();
        Variable v = getVariable(typeString, id);
        System.out.println("Please enter assignment:\n0.false\n1.true");
        int assignment = input.nextInt();
        boolean bolAssignment = assignment==1;
        Evidence e = new Evidence(v, bolAssignment);
        evidenceList.add(e);
        System.out.println("Resulting Evidence List:\n" + evidenceList);
    }

    private String getTypeString(int type){
        switch (type){
            case 1:
                return "Flooding";
            case 2:
                return "Blockage";
            case 3:
                return "Evacuees";
            default:
                throw new java.lang.Error("No variable type matching the entered input");
        }
    }

    private Variable getVariable(String type, String id){
        for (Variable v : bayesNetwork.getVariables()){
            if (type.equals(v.getClass().getSimpleName()) && id.equals(v.getId())){
                return v;
            }
        }
        throw new java.lang.Error("Invalid edge/vertex id");
    }

    private void doProbabilisticReasoning(){
        return;
    }

    public static void main(String[] args) {
        Main s= new Main();
        s.run();
    }
}

