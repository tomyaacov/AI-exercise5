package agent;

import agent.search.AStarAgent;
import agent.search.GreedySearchAgent;
import agent.search.RealTimeAStarAgent;
import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;
import simulator.SimulatorContext;

import java.util.Scanner;

public class AgentFactory {

    private SimulatorContext context;

    public AgentFactory(SimulatorContext context){
        this.context = context;
    }

    public Agent getAgent(int agentType) {

        switch (agentType) {

            case 1:
                return new HumanAgent(context);
            case 2:
                return new GreedyAgent(context);
            case 3:
                return new VandalAgent(context);
            case 4:
                return new GreedySearchAgent(context);
            case 5:
                return new AStarAgent(context);
            case 6: {
                Scanner input = new Scanner(System.in);
                System.out.println("Please specify max expand value");
                int maxExpand = input.nextInt();
                return new RealTimeAStarAgent(context, maxExpand);
            }
            default:
                return null;
        }
    }
}
