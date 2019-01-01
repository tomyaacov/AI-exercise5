package bayes.variables;

import bayes.Evidence;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Evacuees extends Variable{

    @Getter @Setter
    private double noisyPi;

    @Override
    public double pi(List<Evidence> evidences) {
        if (evidences.size() == 0){
            throw new java.lang.Error("Evacuees evidence list should be greater than 0");
        } else {
            double noisyProduct = 1;
            for (Evidence e : evidences){
                Blockage b = (Blockage)e.getVar();
                noisyProduct *= e.isValue() ? 1 - b.getNoisyPEvacuees() : 1;
            }
            return 1 - noisyProduct == 0 ? 0.001 : 1 - noisyProduct;
        }
    }

    public static void main(String[] args) {
        Evacuees evacuees = new Evacuees();
        List<Evidence> l_true = new ArrayList<>(1);
        Blockage a = new Blockage(1);
        l_true.add(0, new Evidence(a, true));
        List<Evidence> l_false = new ArrayList<>(1);
        l_false.add(0, new Evidence(a, false));
        System.out.println(evacuees.pi(l_true));
        System.out.println(evacuees.pi(l_false));
        System.out.println(evacuees.qi(l_true));
        System.out.println(evacuees.qi(l_false));
    }
}
