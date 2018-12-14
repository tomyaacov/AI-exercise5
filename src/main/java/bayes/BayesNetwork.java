package bayes;

import bayes.variables.Blockage;
import bayes.variables.Evacuees;
import bayes.variables.Variable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BayesNetwork {

    @Getter @Setter
    List<Variable> variables;

    public BayesNetwork() {
        this.variables = new ArrayList<>();
    }

    @Override
    public String toString() {
        String finalString = "";
        for(Variable v : variables){
            finalString += v.getClass().getSimpleName() + " " + v.getId() + "\n" + v + "\n";
        }
        return finalString;
    }

    public static void main(String[] args) {
        BayesNetwork n = new BayesNetwork();
        Evacuees evacuees = new Evacuees();
        evacuees.setId("1");
        List<Variable> l = new ArrayList<>(1);
        Blockage b = new Blockage(1);
        b.setId("1");
        l.add(0, b);
        evacuees.setParents(l);
        n.getVariables().add(evacuees);
        n.getVariables().add(b);
        System.out.println(n);
    }
}
