import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

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
        List<Integer> peopleInVertex = new LinkedList<>();
        peopleInVertex.add(5);
        peopleInVertex.add(0);
        List<String> blockedEdge = new LinkedList<>();
        blockedEdge.add("U");
        blockedEdge.add("B");
        State s = new State("2",peopleInVertex, blockedEdge, 0, 10, 1.5);
        double p = 0.5;
        StateProbability sp = new StateProbability(s, p);
        System.out.println(sp.getValue());
    }
}
