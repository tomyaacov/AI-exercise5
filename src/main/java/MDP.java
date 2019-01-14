import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;


    void initialize(){
        List<State> states = new LinkedList<>();
//        graph.getNode(1).get

    }
}
