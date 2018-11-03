package entities;

import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Node;
import simulator.SimulatorContext;

import java.util.Map;

public class State {

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
    private static int deadline;

    public State(State prev, HurricaneNode currNode, Map<String, Boolean> peopleInside, double time, int people) {
        this.prev = prev;
        this.currNode = currNode;
        this.peopleInside = peopleInside;
        this.time = time;
        this.people = people;
    }

    public State(SimulatorContext simulatorContext, HurricaneNode currNode, State prev, int people) {
        this.prev = prev;
        this.currNode = currNode;
        this.time = simulatorContext.getTime();
        this.people = people;

    }

    public Boolean isGoalState(){
        return deadline <= time || (!peopleInside.containsValue(true) && people==0);
    }

    private void initializePeopleInside(SimulatorContext simulatorContext){
        HurricaneNode checkedNode;
        for(int i = 1; i <= simulatorContext.getGraph().getNodeCount(); i++){
            checkedNode = simulatorContext.getGraph().getNode(Integer.toString(i));
            if(checkedNode.getPeople() > 0  && !checkedNode.isShelter()){
                peopleInside.put(Integer.toString(i), true);
            }
        }
    }
}
