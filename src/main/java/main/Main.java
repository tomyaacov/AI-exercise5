package main;

import bayes.BayesNetwork;
import bayes.Evidence;
import bayes.variables.Blockage;
import bayes.variables.Evacuees;
import bayes.variables.Flooding;
import bayes.variables.Variable;
import config.HurricaneGraph;
import inference.EnumerationInference;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import org.graphstream.ui.view.Viewer;
import parser.Parser;
import bayes.parser.BayesParser;

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
        for (Evidence e : evidenceList){
            if (e.getVar().equals(v)){
                System.out.println("Variable already exists in evidence list");
                return;
            }
        }
        System.out.println("Please enter assignment:\n0.false\n1.true");
        int assignment = input.nextInt();
        boolean bolAssignment = assignment==1;
        Evidence e = new Evidence(v, bolAssignment);
        evidenceList.add(e);
        System.out.println("Resulting Evidence List:\n" + evidenceList);
    }

    private void doProbabilisticReasoning(){
//        Variable x=bayesNetwork.getVariables().get(0);
//        x= bayesNetwork.getVariables().get(3);
//        double p=EnumerationInference.ask(x, evidenceList, bayesNetwork);
//        System.out.println(p);

        Scanner input = new Scanner(System.in);
        System.out.println(PROBABILITY_INFERENCE);

        int typeNum = input.nextInt();
        if (typeNum == 1) {
            List<Variable> evacuees = getEvacueesVariables();
            doConjunctiveQueries(evacuees);
        } else if (typeNum == 2) {
            List<Variable> floodings = getFloodingVariables();
            doConjunctiveQueries(floodings);
        } else if (typeNum == 3) {
            List<Variable> blocks = getBlocksVariables();
            doConjunctiveQueries(blocks);
        } else if (typeNum == 4) {
            System.out.println("Please enter path (example - 2413)");
            String path = input.next();
            List<String> pathList = Arrays.asList(path.split(""));
            List<Blockage> blockageList = new ArrayList<>();
            for(int i = 0; i < pathList.size()-1; i++){
                String id1 = pathList.get(i);
                String id2 = pathList.get(i+1);
                Edge e = getEdge(id1, id2);
                if (e == null){
                    System.out.println("no edge between " + id1 + " and " + id2);
                    return;
                }
                Blockage b = getBlockage(e.getId());
                blockageList.add(b);
            }
            doConjunctiveQueriesBlockage(blockageList);

        }

    }

    private void doConjunctiveQueriesBlockage(List<Blockage> variables) {
        double prob = 1;
        List<Evidence> evidences = new LinkedList<>(evidenceList);
        for (Variable var : variables) {
            prob *= EnumerationInference.ask(var, evidences, bayesNetwork);
            if (! evidences.stream().anyMatch(evidence -> evidence.getVar().equals(var))){
                evidences.add(new Evidence(var, false));
            }
        }
        System.out.println(prob);
    }

    private Blockage getBlockage(String id){
        for (Variable v : bayesNetwork.getVariables()){
            if (v instanceof Blockage && v.getId().equals(id)){
                return (Blockage)v;
            }
        }
        return null;
    }

    private Edge getEdge(String id1, String id2) {
        Edge e = graph.getEdge(id1 + "-" + id2);
        if(e == null){
            return graph.getEdge(id2 + "-" + id1);
        }
        return e;
    }

    private List<Variable> getBlocksVariables() {
        return bayesNetwork.getVariables().stream()
                        .filter(variable -> variable instanceof Blockage)
                        .collect(Collectors.toList());
    }

    private void doConjunctiveQueries(List<Variable> variables) {
        double prob = 1;
        List<Evidence> evidences = new LinkedList<>(evidenceList);
        for (Variable var : variables) {
            prob *= EnumerationInference.ask(var, evidences, bayesNetwork);
            if (! evidences.stream().anyMatch(evidence -> evidence.getVar().equals(var))){
                evidences.add(new Evidence(var, true));
            }
        }
        System.out.println(prob);
    }

    private List<Variable> getFloodingVariables() {
        return bayesNetwork.getVariables().stream()
                        .filter(variable -> variable instanceof Flooding)
                        .collect(Collectors.toList());
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

    private List<Variable> getEvacueesVariables() {
        return bayesNetwork.getVariables().stream()
                            .filter(variable -> variable instanceof Evacuees)
                            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
//        System.out.println(f(new LinkedList<>()));


        Main s= new Main();

        s.run();
    }

}

