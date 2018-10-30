package algorithms;

import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

public class DijkstraOutput {

    @Getter @Setter
    private List<Double> dist;

    @Getter @Setter
    private List<HurricaneNode> prev;

    @Getter @Setter
    private List<Integer> nodeCarrying;

    public DijkstraOutput(List<Double> dist, List<HurricaneNode> prev, List<Integer> nodeCarrying) {
        this.dist = dist;
        this.prev = prev;
        this.nodeCarrying = nodeCarrying;
    }

    @Override
    public String toString() {
        return "DijkstraOutput \n" +
                "dist=" + dist + "\n" +
                ", prev=" + prev + "\n" +
                ", nodeCarrying=" + nodeCarrying + "\n";
    }
}
