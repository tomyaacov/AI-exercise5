package agent;

import algorithms.Algorithm;
import algorithms.DijkstraOutput;
import config.HurricaneNode;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

public class GreedyAgent extends Agent {

    public GreedyAgent(SimulatorContext context) {
        super(context);
    }

    @Override
    public AgentAction doNextAction(double currTime) {
        double shortestPathDist = Double.MAX_VALUE;
        Integer targetNode = null;
        HurricaneNode checkedNode;

        DijkstraOutput output = Algorithm.dijkstra(context, getCurrNode(), getPeople());

        if (getPeople() > 0){
            for(int i = 1; i < output.getDist().size(); i++){
                if (i != Integer.parseInt(getCurrNode().getId())){//checking it is not the same node
                    checkedNode = context.getGraph().getNode(Integer.toString(i));
                    if (checkedNode.isShelter()){//looking for shelters only
                        if(shortestPathDist > output.getDist().get(i)){
                            shortestPathDist = output.getDist().get(i);
                            targetNode = i;
                        }
                    }

                }
            }
        } else {
            for(int i = 1; i < output.getDist().size(); i++){
                if (i != Integer.parseInt(getCurrNode().getId())){//checking it is not the same node
                    checkedNode = context.getGraph().getNode(Integer.toString(i));
                    if (checkedNode.getPeople() > 0  && !checkedNode.isShelter()){//looking for vertex with people only
                        if(shortestPathDist > output.getDist().get(i)){
                            shortestPathDist = output.getDist().get(i);
                            targetNode = i;
                        }
                    }

                }
            }
        }

        HurricaneNode searchNode, searchNodeParent;
        Integer i = targetNode;

        if (shortestPathDist == Double.MAX_VALUE){
            return noOp(currTime);
        } else {
            searchNode = context.getGraph().getNode(Integer.toString(i));
            searchNodeParent = output.getPrev().get(i);
            while (true){
                if(searchNodeParent.equals(getCurrNode())){break;}
                searchNode = searchNodeParent;
                searchNodeParent = output.getPrev().get(Integer.parseInt(searchNode.getId()));

            }
            return traverse(currTime, getCurrNode().getEdgeBetween(searchNode.getId()), searchNode.getId());
        }

    }

    public AgentAction traverse(double currTime, Edge e, String nodeId){
        if(isEnoughTime(e, currTime)){
            AgentAction action =
                    new AgentAction(context.getGraph().getNode(nodeId), calculateTraverseOperation(e));

            setCurrNode(context.getGraph().getNode(nodeId));
            return action;
        }
        return null;
    }
}
