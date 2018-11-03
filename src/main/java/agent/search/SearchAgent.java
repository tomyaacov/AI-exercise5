package agent.search;

import agent.Agent;
import agent.AgentAction;
import config.HurricaneGraph;
import config.HurricaneNode;
import entities.State;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SearchAgent extends Agent{

    private int expandGlobalCounter;
    private final int maxExpand;
    private List<HurricaneNode> path;

    public SearchAgent(SimulatorContext context, int maxExpand) {
        super(context);
        this.expandGlobalCounter = 0;
        this.maxExpand = maxExpand;
        this.path = new LinkedList<>();
    }

    @Override
    public AgentAction doNextAction(double currTime){
        if(path.isEmpty()){
            List<State> stateList = search(new State(this.context, getCurrNode(), null, getPeople(), 0));
            path = stateList.stream()
                    .map(state -> state.getCurrNode())
                    .collect(Collectors.toList());
        }
        HurricaneNode nextNode = path.remove(0);
        HurricaneNode currNode = getCurrNode();
        setCurrNode(nextNode);
        return new AgentAction(nextNode,
                calculateTraverseOperation(nextNode.getEdgeBetween(currNode)));
    }


    public List<State> search(State currState){
        PriorityQueue<State> fringe = new PriorityQueue<State>();
        int expandCounter = 0;
        fringe.add(currState);
        while(! fringe.isEmpty()){
            State s = fringe.poll();
            if (s.isGoalState() || expandCounter == this.maxExpand){
                return getStatePath(s);
            }
            fringe.addAll(expand(s));
            expandCounter++;
            expandGlobalCounter++;
        }
        return null;
    }

    private Collection<? extends State> expand(State s) {
        List<State> expandState = new LinkedList<>();
        Iterator<Edge> it = s.getCurrNode().getEdgeIterator();
        while (it.hasNext()) {
            Edge currentEdge = it.next();
            if (!HurricaneGraph.isEdgeBlock(currentEdge)) {
                HurricaneNode node = currentEdge.getOpposite(s.getCurrNode());
                Map<String, Boolean> peopleInNodes = updatePeopleInNodesMap(s, node);
                expandState.add(constructNewState(s, currentEdge, node, peopleInNodes));
            }
        }
        return expandState;
    }

    private State constructNewState(State s, Edge currentEdge, HurricaneNode node, Map<String, Boolean> peopleInNodes) {
        return new State(s,
                node,
                peopleInNodes,
                s.getTime() + calculateTraverseSearchOperation(currentEdge, s.getPeople()),
                s.getPeople() + node.getPeople(),
                calculateNextStateCost(s, currentEdge));
    }

    protected double calculateNextStateCost(State s, Edge currentEdge) {
        return s.getCostSoFar() + calculateTraverseSearchOperation(currentEdge, s.getPeople());
    }

    private double calculateTraverseSearchOperation(Edge e, int peopleInVehicle){
        return e.getNumber("weight") * (1 + context.getK() * peopleInVehicle);

    }

    private Map<String, Boolean> updatePeopleInNodesMap(State s, HurricaneNode node) {
        Map<String, Boolean> peopleInNodes = new HashMap<>(s.getPeopleInside());
        if (peopleInNodes.containsKey(node.getId())){
            peopleInNodes.put(node.getId(), false);
        }
        return peopleInNodes;
    }

    private List<State> getStatePath(State s) {
        LinkedList<State> statePath = new LinkedList<>();
        while (s.getPrev() != null){
            statePath.addFirst(s);
            s = s.getPrev();
        }
        return statePath;
    }

}
