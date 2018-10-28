package config;

import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class HurricaneNode extends SingleNode {

    @Getter @Setter
    private int people;

    @Getter @Setter
    private boolean shelter;

    public HurricaneNode(AbstractGraph graph, String id) {
        super(graph, id);
    }

    public String toString(){
        String nodeKind = isShelter() ? "Shelter; " : "";
        return "node id:" + getId() + "; " + nodeKind + "people inside: " + getPeople();
    }
}
