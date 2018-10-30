package agent;

import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import simulator.SimulatorContext;

public class AgentFactory {

    private SimulatorContext context;

    public AgentFactory(SimulatorContext context){
        this.context = context;
    }

    public Agent getAgent(int agentType){
        if (agentType == 1){
            return new HumanAgent(context);
        } else {
            if (agentType == 2){
                return new GreedyAgent(context);
            } else {
                return null;
            }
        }

    }
}
