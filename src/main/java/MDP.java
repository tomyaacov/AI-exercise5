import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;

import java.util.LinkedList;
import java.util.List;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;


    void initialize(){
        List<State> states = new LinkedList<>();
//        graph.getNode(1).get

    }

    public List<StateProbability> transitionFunction(State state, Action action){
        return null;
    }

    public List<Action> getAllActions(State state){
        List<Action> actionList = new LinkedList<>();
        if (state.isGoal()){
            return actionList;
        }
        for(Edge e:graph.getEachEdge()){
            if (e.getNode0().getId().equals(state.getLocation()) || e.getNode1().getId().equals(state.getLocation())){
                continue;//TODO: add action to list here
            }
        }
        return actionList;
    }
}
