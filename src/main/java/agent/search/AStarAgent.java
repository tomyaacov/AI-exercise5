package agent.search;

import simulator.SimulatorContext;

public class AStarAgent extends SearchAgent{

    public AStarAgent(SimulatorContext context) {
        super(context, Integer.MAX_VALUE);
    }
}
