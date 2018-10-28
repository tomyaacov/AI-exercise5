package simulator;

import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;

public class SimulatorContext {

    @Getter @Setter
    private HurricaneGraph graph;

    @Getter @Setter
    private int deadline;

    @Getter @Setter
    private double k;


}
