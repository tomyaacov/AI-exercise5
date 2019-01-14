import lombok.Getter;
import lombok.Setter;
import main.Main;

import java.util.LinkedList;
import java.util.List;

public class State {

    @Getter @Setter
    private int location;

    @Getter @Setter
    private List<Integer> peopleInVertex;

    @Getter @Setter
    private List<String> blockedEdge;

    @Getter @Setter
    private int peopleSaved;

    @Getter @Setter
    private int time;

    @Getter @Setter
    private double utility;

    @Getter @Setter
    private boolean reachable;

    public State(int location, List<Integer> peopleInVertex, List<String> blockedEdge, int peopleSaved, int time, double utility) {
        this.location = location;
        this.peopleInVertex = peopleInVertex;
        this.blockedEdge = blockedEdge;
        this.peopleSaved = peopleSaved;
        this.time = time;
        this.utility = utility;
        this.reachable = false;
    }

    public State(int location, List<Integer> peopleInVertex, List<String> blockedEdge, int peopleSaved, int time) {
        this.location = location;
        this.peopleInVertex = peopleInVertex;
        this.blockedEdge = blockedEdge;
        this.peopleSaved = peopleSaved;
        this.time = time;
        this.utility = 0;
        this.reachable = false;
    }

    public int getReward(){
        if(time==0){
            return peopleSaved;
        } else {
            return 0;
        }
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
        s += reachable ? " reachable" : " unreachable";
        return s;
    }

    public static void main(String[] args) {
        List<Integer> peopleInVertex = new LinkedList<>();
        peopleInVertex.add(5);
        peopleInVertex.add(0);
        List<String> blockedEdge = new LinkedList<>();
        blockedEdge.add("U");
        blockedEdge.add("B");
        State s = new State(1,peopleInVertex, blockedEdge, 0, 10);
        System.out.println(s);
    }
}
