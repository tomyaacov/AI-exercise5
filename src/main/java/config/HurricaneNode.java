package config;

import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class HurricaneNode extends SingleNode {



    public HurricaneNode(AbstractGraph graph, String id) {
        super(graph, id);
    }

    public String toString(){
        return "node id:" + getId();
    }
}
