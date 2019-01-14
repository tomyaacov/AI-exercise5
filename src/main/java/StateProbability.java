import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StateProbability {

    @Getter @Setter
    private State state;

    @Getter @Setter
    private double probability;

    public StateProbability(State state, double probability) {
        this.state = state;
        this.probability = probability;
    }

    public double getValue(){
        return state.getUtility()*probability;
    }

    public static void main(String[] args) {
        Map<String, Integer> peopleInVertex = new HashMap<>();
        peopleInVertex.put("1", 5);
        peopleInVertex.put("2", 0);
        Map<String, Boolean> blockedEdge = new HashMap<>();
        blockedEdge.put("1-2", true);
        blockedEdge.put("1-3", false);
        State s = new State("1",peopleInVertex, blockedEdge, 0, 10, 0);
        double p = 0.5;
        StateProbability sp = new StateProbability(s, p);
        System.out.println(sp.getValue());
    }
}
