package agent;

import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;

public class AgentAction {

    @Getter @Setter
    private HurricaneNode currNode;

    @Getter @Setter
    private double time;

    protected AgentAction(HurricaneNode node, double time){
        this.currNode = node;
        this.time = time;
    }
}
