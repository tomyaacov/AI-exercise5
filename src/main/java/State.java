import lombok.Getter;
import lombok.Setter;
import main.Main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class State {

    @Getter @Setter
    private String location;

    @Getter @Setter
    private Map<String, Integer> peopleInVertex;

    @Getter @Setter
    private Map<String, Boolean> blockedEdge;

    @Getter @Setter
    private int peopleSaved;

    @Getter @Setter
    private int time;

    @Getter @Setter
    private double utility;

    @Getter @Setter
    private boolean reachable;

    @Getter @Setter
    private Action bestAction;

    public State(String location, Map<String, Integer> peopleInVertex, Map<String, Boolean> blockedEdge, int peopleSaved, int time, double utility) {
        this.location = location;
        this.peopleInVertex = peopleInVertex;
        this.blockedEdge = blockedEdge;
        this.peopleSaved = peopleSaved;
        this.time = time;
        this.utility = utility;
        this.reachable = false;
        this.bestAction = null;
    }

    public State(String location, Map<String, Integer> peopleInVertex, Map<String, Boolean> blockedEdge, int peopleSaved, int time) {
        this.location = location;
        this.peopleInVertex = peopleInVertex;
        this.blockedEdge = blockedEdge;
        this.peopleSaved = peopleSaved;
        this.time = time;
        this.utility = 0;
        this.reachable = false;
        this.bestAction = null;
    }

    public int getReward(){
        if(time==0){
            return peopleSaved;
        } else {
            return 0;
        }
    }

    public boolean isGoal(){
        return  time <= 0;
    }

    @Override
    public String toString() {
        String s = "State(" +
                "location=" + location +
                ", peopleInVertex=" + peopleInVertex +
                ", blockedEdge=" + blockedEdge +
                ", peopleSaved=" + peopleSaved +
                ", time=" + time +
                ')' + " utility=" + utility;
        s += " best action: " + bestAction;
        s += reachable ? " reachable" : " unreachable";
        return s;
    }

    public static void main(String[] args) {
        Map<String, Integer> peopleInVertex = new HashMap<>();
        peopleInVertex.put("1", 5);
        peopleInVertex.put("2", 0);
        Map<String, Boolean> blockedEdge = new HashMap<>();
        blockedEdge.put("1-2", true);
        blockedEdge.put("1-3", false);
        State s = new State("1",peopleInVertex, blockedEdge, 0, 10);
        System.out.println(s);
    }
}
