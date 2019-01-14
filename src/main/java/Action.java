import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;

public class Action {

    @Getter @Setter
    private String from;

    @Getter @Setter
    private String to;

    @Getter @Setter
    private Edge edge;

    public Action(String from, String to, Edge edge) {
        this.from = from;
        this.to = to;
        this.edge = edge;
    }

    @Override
    public String toString() {
        return "move from " + from + " to " + to;
    }
}
