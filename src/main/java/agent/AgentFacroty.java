package agent;

public class AgentFacroty {

    public Agent getAgent(int agentType){
        if (agentType == 1){
            return new HumanAgent();
        }
        //TODO elseif...
        else {
            return null;
        }
    }
}
