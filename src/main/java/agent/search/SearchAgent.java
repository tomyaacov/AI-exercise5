package agent.search;

import agent.Agent;
import agent.AgentAction;
import algorithms.Algorithm;
import entities.State;
import simulator.SimulatorContext;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class SearchAgent extends Agent{

    private int expandCounter;
    private List<AgentAction> actions;

    public SearchAgent(SimulatorContext context) {
        super(context);
        this.expandCounter = 0;
        this.actions = new LinkedList<>();
    }

    public AgentAction doNextAction(double currTime, int maxExpand){
        if(actions.isEmpty()){
//            search(new State(this.context, getCurrNode(), null, getPeople()), maxExpand);
        }
        return actions.remove(0);
    }


    public List<State> search(State currState, int maxExapnd){
        PriorityQueue<State> fringe = new PriorityQueue<State>
                (Comparator.comparing(Algorithm::heuristicFunction ));
        return null;
    }
}
