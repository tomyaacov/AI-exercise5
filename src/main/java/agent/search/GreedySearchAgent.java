package agent.search;

import agent.AgentAction;
import entities.State;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

public class GreedySearchAgent extends SearchAgent {


    public GreedySearchAgent(SimulatorContext context) {
        super(context, 1);
    }

    @Override
    protected double calculateNextStateCost(State s, Edge currentEdge) {
        return 0;

    }

    }
