package bayes.variables;

import bayes.Evidence;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Variable {

    @Getter @Setter
    private String id;

    @Getter @Setter
    private List<Variable> parents;

    public Variable() {
        this.parents = new ArrayList<>();
    }

    public abstract double pi(List<Evidence> evidences);

    public String toStringPi(List<Evidence> evidences){
        String evidencesString = getEvidenceString(evidences);
        return "P(" + this.getClass().getSimpleName() + " " + id + "|" + evidencesString + ") = " + pi(evidences);
    }

    private String getEvidenceString(List<Evidence> evidences) {
        String evidencesString = "";
        for(Evidence e : evidences){
            evidencesString += e;
        }
        return evidencesString;
    }

    public  double qi(List<Evidence> evidences){
        return 1-pi(evidences);
    }

    public String toStringQi(List<Evidence> evidences){
        String evidencesString = getEvidenceString(evidences);
        return "P(not " + this.getClass().getSimpleName() + " " + id + "|" + evidencesString + ") = " + qi(evidences);
    }

    public String recToString(List<Evidence> evidences, int counter){
        if (parents.size() == counter){
            return toStringPi(evidences) + "\n" + toStringQi(evidences) + "\n";
        }
        Evidence with = new Evidence(parents.get(counter), true);
        Evidence without = new Evidence(parents.get(counter), false);
        List<Evidence> withList = new ArrayList<>(evidences);
        List<Evidence> withoutList = new ArrayList<>(evidences);
        withList.add(with);
        withoutList.add(without);

        return recToString(withList, counter+1) + recToString(withoutList, counter+1);
    }

    @Override
    public String toString() {
        return recToString(new ArrayList<>(), 0);
    }

    public static void main(String[] args) {
        Evacuees evacuees = new Evacuees();
        evacuees.setId("1");
        List<Variable> l = new ArrayList<>(1);
        Blockage b = new Blockage(1);
        b.setId("1");
        l.add(0, b);
        evacuees.setParents(l);
        System.out.println(evacuees);
    }
}