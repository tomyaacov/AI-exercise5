import config.HurricaneGraph;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class StateSpace {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;
    
}
