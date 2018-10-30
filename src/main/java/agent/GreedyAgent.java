package agent;

import algorithms.Algorithm;
import algorithms.DijkstraOutput;
import config.HurricaneNode;
import simulator.SimulatorContext;

public class GreedyAgent extends Agent {

    public GreedyAgent(SimulatorContext context) {
        super(context);
    }

    @Override
    public AgentAction doNextAction(double currTime) {
        double shortestPathDist = Double.MAX_VALUE;
        Integer targetNode;
        HurricaneNode checkedNode;

        DijkstraOutput output = Algorithm.dijkstra(context, getCurrNode(), getPeople());

        if (getPeople() > 0){
            for(int i = 0; i < output.getDist().size(); i++){
                if (i+1 != Integer.parseInt(getCurrNode().getId())){//checking it is not the same node
                    checkedNode = context.getGraph().getNode(Integer.toString(i+1));
                    if (checkedNode.isShelter()){//looking for shelters only
                        if(shortestPathDist > output.getDist().get(i)){
                            shortestPathDist = output.getDist().get(i);
                            targetNode = i;
                        }
                    }

                }
            }
        } else {
            for(int i = 0; i < output.getDist().size(); i++){
                if (i+1 != Integer.parseInt(getCurrNode().getId())){//checking it is not the same node
                    checkedNode = context.getGraph().getNode(Integer.toString(i+1));
                    if (checkedNode.getPeople() > 0){//looking for vertex with people only
                        if(shortestPathDist > output.getDist().get(i)){
                            shortestPathDist = output.getDist().get(i);
                            targetNode = i;
                        }
                    }

                }
            }
        }

        if (shortestPathDist == Double.MAX_VALUE){
            //return noOPAction(currTime);
            return null;
        } else {
            return null;
        }

    }

    public AgentAction traverse(double currTime){
        return null;
    }
}
