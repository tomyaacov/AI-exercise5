package bayes.variables;

import bayes.Evidence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Flooding extends Variable{

    @Getter @Setter
    private double p;

    public Flooding(double p) {
        this.p = p;
    }

    @Override
    public double pi(List<Evidence> evidences) {
        return p;
    }
}
