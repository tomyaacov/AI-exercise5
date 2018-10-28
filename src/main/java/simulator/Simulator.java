package simulator;

import agent.Agent;
import agent.AgentFacroty;
import lombok.Getter;
import lombok.Setter;
import parser.Parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Simulator {

    @Getter @Setter
    private SimulatorContext context;

    @Getter @Setter
    private List<Agent> agents;

    @Getter @Setter
    private AgentFacroty agentFacroty;

    @Getter @Setter
    private Parser parser;


    public void run(){
        initialize();

    }

    private void initialize(){

        //TODO any spring like dependency injection?
        Scanner input = new Scanner(System.in);
        agents = new LinkedList<>();
        agentFacroty = new AgentFacroty();
        parser = new Parser();

        context = initializeGraph();
        context.getGraph().display(); //TODO delete
        System.out.println("Welcome to Agent simulator");
        System.out.println("Please enter a value for K constant:");
        context.setK(input.nextDouble());
        System.out.print("Please specify the number of agents: ");
        int agentNumber = input.nextInt();
        for (int i = 0; i < agentNumber; i++){
            initializeAgent(input, i);
        }
    }

    private void initializeAgent(Scanner input, int i) {
        System.out.println("Agent no. " + (i+1) + ":");
        System.out.println("Please specify agent Type: 1) Humam  2) Greedy  3) Vandal");
        int type = input.nextInt();
        System.out.println("Please specify Agent " + (i+1) +
                " initial position (number in between 1 to " + context.getGraph().getNodeCount() +"):");
        int position = input.nextInt();
        Agent agent = agentFacroty.getAgent(type);
        agent.setCurrNode(context.getGraph().getNode(String.valueOf(position)));
        agents.add(agent);
    }

    private SimulatorContext initializeGraph() {
        try {
            return parser.parseFile("src\\main\\resources\\graph");
        } catch (IOException e) {
            System.err.println("parsing 'graph.txt' file encountered a problem. Check for valid input");
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        Simulator s= new Simulator();
        s.run();
    }
}
