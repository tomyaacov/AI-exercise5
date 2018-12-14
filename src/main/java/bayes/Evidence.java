package bayes;

import bayes.variables.Variable;
import lombok.Getter;
import lombok.Setter;

public class Evidence {

    @Getter @Setter
    private Variable var;

    @Getter @Setter
    private boolean value;
}
