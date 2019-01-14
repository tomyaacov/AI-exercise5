import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;


    void initialize(){
        List<State> states = new LinkedList<>();
        String startId = graph.getAttribute("start");







    }
}
