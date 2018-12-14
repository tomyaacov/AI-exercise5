package bayes.variables;

import bayes.Evidence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Variable {

    @Getter @Setter
    private String id;

    @Getter @Setter
    private List<Variable> parents;

    public abstract double pi(List<Evidence> evidences);

    public  double qi(List<Evidence> evidences){
        return 1-pi(evidences);
    }

}
