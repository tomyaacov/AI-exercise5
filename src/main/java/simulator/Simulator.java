package simulator;

import agent.Agent;
import agent.AgentFactory;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.ui.swingViewer.Viewer;
import parser.Parser;

import java.awt.event.ActionEvent;
import java.io.File;
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
    private AgentFactory agentFacroty;

    @Getter @Setter
    private Parser parser;


    public void run(){
        initialize();
        Viewer view = context.getGraph().display();
        for(Agent agent : agents){
            agent.doActionInNode();
        }
        while(isGameOn()){
            String initPos="";
            for (int i=0; i<agents.size(); i++){
                initPos += "agent " + (i+1)+ " in node: " + agents.get(i).getCurrNode().getId() +
                        "people in vehicle: " + agents.get(i).getPeople() + "; ";
            }
            context.getGraph().setAttribute(
                    "ui.title", initPos);
            for (int i = 0; i < agents.size(); i++ ){
                Agent agent = agents.get(i);
                if (isAgentTimeNotOver(agent)){
                    HurricaneNode node = agent.doNextAction();
                    agent.doActionInNode();
                    setAgentState(i, agent, node);
                }

            }
        }
    }

    private void setAgentState(int i, Agent agent, HurricaneNode node) {
        String agentState;
        if(node == null){
            agentState = "agent time is up.";
        } else{
            agentState = "agent " + (i+1) + "is in node " + node.getId() + "; agent time is: "
                    + agent.getTime() + "; deadline: " + context.getDeadline() + "; agent have " +
                    agent.getPeople() + " people in vehicle";
        }
        if(node != null){
            node.setAttribute("ui.label", node);
        }
        context.getGraph().setAttribute("ui.title",  agentState);
    }

    private boolean isAgentTimeNotOver(Agent agent) {
        return agent.getTime() < context.getDeadline();
    }

    private boolean isGameOn() {
        int deadline =  context.getDeadline();
        return agents.stream().anyMatch(agent -> agent.getTime() < deadline);
    }

    private void initialize(){

        //TODO any spring like dependency injection?
        Scanner input = new Scanner(System.in);
        agents = new LinkedList<>();
        parser = new Parser();
        context = initializeGraph();
        agentFacroty = new AgentFactory(context);

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
            return parser.parseFile("src.main.resources.graph".replace(".", File.separator));
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
