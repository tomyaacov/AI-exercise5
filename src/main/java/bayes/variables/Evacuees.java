package bayes.variables;

import bayes.Evidence;

import java.util.List;

public class Evacuees extends Variable{
    @Override
    public double pi(List<Evidence> evidences) {
        return 0;
    }
}
