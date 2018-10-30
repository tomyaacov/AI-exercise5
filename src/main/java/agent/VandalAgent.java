package agent;

import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

import java.util.*;
import java.util.stream.Collectors;

public class VandalAgent extends Agent {

    @Setter @Getter
    private int turn;

    public VandalAgent(SimulatorContext context){
        super(context);
        this.turn = 0;
    }

    @Override
    public AgentAction doNextAction(double currTime) {
        if(getTurn() <= this.context.getGraph().getNodeCount() ){
            turn++;
            return noOp(currTime);
        }
        List<Edge> adjacentEdges = getAdjacentEdgesList();
        Edge minEdgeToBlock = getMinAdjacentEdge(adjacentEdges);
        if (minEdgeToBlock == null){ //no left edges
            return null;
        }
        HurricaneGraph.setEdgeBlock(minEdgeToBlock, true);
        Edge minEdgeToTraverse = getMinAdjacentEdge(adjacentEdges);
        if (minEdgeToTraverse == null){ //no left edges
            return null;
        }
        HurricaneNode traverseNode = minEdgeToTraverse.getOpposite(getCurrNode());
        setCurrNode(traverseNode);
        turn++;
        return new AgentAction(getCurrNode(), 1);
    }

    private List<Edge> getAdjacentEdgesList() {
        Iterator<Edge> it = getCurrNode().getEdgeIterator();
        List<Edge> adjacentEdges = new LinkedList<>();
        while (it.hasNext()){
            Edge e =it.next();
            adjacentEdges.add(e);
        }
        return adjacentEdges;
    }

    private Edge getMinAdjacentEdge(List<Edge> adjacentEdges) {
        Edge minEdge = adjacentEdges.stream()
                .filter(edge -> ! HurricaneGraph.isEdgeBlock(edge))
                .min(Comparator.comparing(edge -> edge.getNumber("weight"))).orElse(null);
        if(minEdge == null){ //no leftEdges
            return null;
        }
        double minWeight = minEdge.getNumber("weight");

        return adjacentEdges.stream()
                .filter(edge -> ! HurricaneGraph.isEdgeBlock(edge))
                .filter(edge -> edge.getNumber("weight") == minWeight)
                .min(Comparator.comparing(Edge::getId)).orElse(null);
    }

    @Override
    public void doActionInNode() {
        return;
    }

}
