package bayes.variables;

import bayes.Evidence;
import lombok.Getter;
import lombok.Setter;
import main.Main;

import java.util.ArrayList;
import java.util.List;

public class Blockage extends Variable{

    @Getter @Setter
    private double noisyPBlockage;

    @Getter @Setter
    private double noisyPEvacuees;

    public Blockage(int edgeWeight) {
        this.noisyPBlockage = 0.6*1/edgeWeight;
        this.noisyPEvacuees = edgeWeight > 4 ? 0.8 : 0.4;//TODO: check if greater or equal...
    }

    @Override
    public double pi(List<Evidence> evidences) {
        if (evidences.size() != 2){
            throw new java.lang.Error("Blockage evidence list should be of size 2");
        } else {
            double noisyPA = evidences.get(0).isValue() ? 1-noisyPBlockage : 1;
            double noisyPB = evidences.get(1).isValue() ? 1-noisyPBlockage : 1;
            double prob =  1 - (noisyPA*noisyPB);
            return prob == 0 ? 0.001 : prob;
        }
    }
    public static void main(String[] args) {
        Blockage blockage = new Blockage(1);
        List<Evidence> l = new ArrayList<>(2);
        Flooding a = new Flooding(0.2);
        Flooding b = new Flooding(0.4);
        l.add(0, new Evidence(a, true));
        l.add(1, new Evidence(b, true));
        System.out.println(blockage.pi(l));
    }
}
