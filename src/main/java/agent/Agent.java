package agent;

import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

public abstract class Agent {

    @Getter @Setter
    private HurricaneNode currNode;

    @Getter @Setter
    private int people;

    @Getter @Setter
    protected SimulatorContext context;

    public Agent(SimulatorContext context) {
        this.context = context;
    }

    /**
     * first thing to be called each agent turn
     * perform action in node (pick up people in normal node or drop off in shelter)
     */
    public void doActionInNode(){
        if(currNode.isShelter()) {
            currNode.setPeople(people);
            people = 0;
        } else {
            people += currNode.getPeople();
            currNode.setPeople(0);
        }
    }

    /**
     * calculate next action (traverse/ noOp) according to agent's strategy
     * nextAction will always call noOp() or traverse() methods
     * returns action time
     */
    public abstract AgentAction doNextAction(double currTime);

    protected boolean isEnoughTime(Edge edgeToTraverse, double currTime) {
        // currTime + w(1+Kp) < deadline ?
        return currTime + calculateTraverseOperation(edgeToTraverse) <= context.getDeadline();
    }

    protected double calculateTraverseOperation(Edge e){
        return e.getNumber("weight") * (1 + context.getK() * getCurrNode().getPeople());
    }

}
