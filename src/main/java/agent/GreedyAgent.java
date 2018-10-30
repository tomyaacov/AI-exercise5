package agent;

import algorithms.Algorithm;
import algorithms.DijkstraOutput;
import config.HurricaneNode;
import simulator.SimulatorContext;

public class GreedyAgent extends Agent {

    public GreedyAgent(SimulatorContext context) {
        super(context);
    }

    //@Override
    //public HurricaneNode doNextAction(double currTime) {
    //    double shortestPathDist = Double.MAX_VALUE;
    //    DijkstraOutput output = Algorithm.dijkstra(getContext(), getCurrNode(), getPeople());
    //    //if(getPeople() > 0){
    //    //    for(int = 1; i < outp)
    //    //}
    //    return null;
    //}
//
    //@Override
    //public HurricaneNode traverse() {
    //    return null;
    //}

    @Override
    public AgentAction doNextAction(double currTime) {
        return null;
    }
}
