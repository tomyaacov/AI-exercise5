package entities;

import algorithms.Algorithm;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Node;
import simulator.SimulatorContext;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class State implements Comparable{

    @Getter @Setter
    private State prev;

    @Getter @Setter
    private HurricaneNode currNode;

    @Getter @Setter
    private Map<String, Boolean> peopleInside;

    @Getter @Setter
    private double time;

    @Getter @Setter
    private int people;

    @Getter @Setter
    private double costSoFar;

    @Getter @Setter
    private static int deadline;

    public State(State prev, HurricaneNode currNode, Map<String, Boolean> peopleInside, double time, int people, double costSoFar) {
        this.prev = prev;
        this.currNode = currNode;
        this.peopleInside = peopleInside;
        this.time = time;
        this.people = people;
        this.costSoFar = costSoFar;
    }

    public State(SimulatorContext simulatorContext, HurricaneNode currNode, State prev, int people, double costSoFar) {
        this.prev = prev;
        this.currNode = currNode;
        this.time = simulatorContext.getTime();
        this.people = people;
        this.costSoFar = costSoFar;
        this.peopleInside = initializePeopleInside(simulatorContext);
    }

    public Boolean isGoalState(){
        return deadline <= time || (!peopleInside.containsValue(true) && people==0);
    }

    private Map<String, Boolean> initializePeopleInside(SimulatorContext simulatorContext){
        Map<String, Boolean> peopleInNodes= new HashMap<>();
        HurricaneNode checkedNode;
        for(int i = 1; i <= simulatorContext.getGraph().getNodeCount(); i++){
            checkedNode = simulatorContext.getGraph().getNode(Integer.toString(i));
            if(checkedNode.getPeople() > 0  && !checkedNode.isShelter()){
                peopleInNodes.put(Integer.toString(i), true);
            }
        }
        return peopleInNodes;
    }


    @Override
    public int compareTo(Object o) {
        State s =(State)o;
        double diff = (getCostSoFar() + Algorithm.heuristicFunction(this)
                - (s.getCostSoFar() + Algorithm.heuristicFunction(s)));
        if(diff <0){
            return -1;
        } else if(diff > 0 ){
            return 1;
        }
        return 0;
    }


    public static void main(String[] args) {

    }
}
