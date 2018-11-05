package simulator;

import agent.Agent;
import agent.AgentAction;
import agent.AgentFactory;
import agent.search.SearchAgent;
import config.HurricaneNode;
import entities.State;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.ui.view.Viewer;
import parser.Parser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Simulator {

    @Getter @Setter
    static SimulatorContext context;

    @Getter @Setter
    private List<Agent> agents;

    @Getter @Setter
    private AgentFactory agentFacroty;

    @Getter @Setter
    private Parser parser;


    public void run(){
        initialize();
        Viewer view = context.getGraph().display();
        pickUpPeopleInInitPosition();
        play();
    }

    private void play() {
        while(isGameOn(context.getTime())){
            for (int i = 0; i < agents.size(); i++ ){
                Agent agent = agents.get(i);
                AgentAction action = agent.doNextAction(context.getTime());
                if(action == null){
                    context.getGraph().setAttribute("ui.title",  "time is UP. Agent " + (i+1) + " last opertaion failed");
                    return;
                }
                context.setTime(context.getTime() + action.getTime());
                agent.doActionInNode();
                setAgentState(i, agent, context.getTime());

                //TODO may remove
                System.out.println("press to see next move");
                Scanner input = new Scanner(System.in);
                input.next();
            }
        }
        for (int i = 0; i < agents.size(); i++ ){
            Agent agent = agents.get(i);
            if(agent instanceof SearchAgent){
                System.out.println("Agent " + (i+1) + " performance measure is " + ((SearchAgent) agent).calculatePerformanceMeasure());
            }
        }
        context.getGraph().setAttribute("ui.title",  "time is UP.");

    }

    private boolean isGameOn(double time) {
        return time < context.getDeadline();
    }

    private void pickUpPeopleInInitPosition() {
        for(Agent agent : agents){
            agent.doActionInNode();
            HurricaneNode currNode = agent.getCurrNode();
            currNode.setAttribute("ui.label", currNode);
        }
        setInitStateInViewerTitle();
    }

    private void setInitStateInViewerTitle() {
        for (int i=0; i<agents.size(); i++){
            setAgentState(i, agents.get(i), 0);
        }
    }

    private void setAgentState(int i, Agent agent, double time) {
        String agentState = "agent " + (i+1) + "is in node " + agent.getCurrNode().getId() + "; time is: "
                +time + "; deadline: " + context.getDeadline() + "; agent have " +
                agent.getPeople() + " people in vehicle";

        agent.getCurrNode().setAttribute("ui.label", agent.getCurrNode());
        context.getGraph().setAttribute("ui.title",  agentState);
    }


    private void initialize(){

        //TODO any spring like dependency injection?
        Scanner input = new Scanner(System.in);
        agents = new LinkedList<>();
        parser = new Parser();
        context = initializeGraph();
        context.setTime(0);
        //TODO: think if we should add deadline to context here
        State.setDeadline(context.getDeadline());
        agentFacroty = new AgentFactory(context);

        System.out.println("Welcome to Agent simulator");
        System.out.println("Please enter a value for K constant:");
        context.setK(input.nextDouble());
        System.out.println("Please enter a value for f:");
        context.setF(input.nextDouble());
        System.out.print("Please specify the number of agents: ");
        int agentNumber = input.nextInt();
        for (int i = 0; i < agentNumber; i++){
            initializeAgent(input, i);
        }
    }

    private void initializeAgent(Scanner input, int i) {
        System.out.println("Agent no. " + (i+1) + ":");
        System.out.println("Please specify agent Type: 1) Humam  2) Greedy  3) Vandal 4) " +
                "Greedy search agent 5) A* search agent  6) Real time search agent");
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
