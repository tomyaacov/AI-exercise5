package inference;

import bayes.BayesNetwork;
import bayes.Evidence;
import bayes.variables.Variable;
import main.Main;

import java.util.*;
import java.util.stream.Collectors;

public class EnumerationInference {

    public static double ask(Variable x, List<Evidence> evidences, BayesNetwork bayesNetwork){
        Map<Variable, Boolean> evidenceMap = createEvidenceMap(evidences);
        if (evidenceMap.containsKey(x)){
            return evidenceMap.get(x)== true ? 1: 0;
        }
        List<Variable> vars = new LinkedList<>(bayesNetwork.getVariables());

        evidenceMap.put(x, true);
        double xTrue = enumerateAll(vars,evidenceMap);

        evidenceMap = createEvidenceMap(evidences);
        evidenceMap.put(x, false);


        vars = new LinkedList<>(bayesNetwork.getVariables());

        double xFalse = enumerateAll(vars, evidenceMap);
        return  xTrue/ (xTrue+xFalse);

    }

    private static Map<Variable, Boolean> createEvidenceMap(List<Evidence> evidences) {
        return evidences.stream().collect(Collectors.toMap(Evidence::getVar, Evidence::isValue));
    }

    private static double enumerateAll(List<Variable> vars, Map<Variable,Boolean> evidenceMap){
        if (vars.isEmpty()){
            return 1.0;
        }
        Variable Y = vars.remove(0);
        Boolean y = evidenceMap.get(Y);
        List<Evidence> YParentsEvidences = getYParentsEvidences(evidenceMap, Y);

        if (y != null){
            return y ? Y.pi(YParentsEvidences) * enumerateAll(new LinkedList<>(vars), evidenceMap) :
                    Y.qi(YParentsEvidences) * enumerateAll(new LinkedList<>(vars), evidenceMap);
        } else {
            evidenceMap.put(Y, true);
            double yTrue = Y.pi(YParentsEvidences) * enumerateAll(new LinkedList<>(vars), evidenceMap);
            evidenceMap.put(Y, false);
            double yFalse = Y.qi(YParentsEvidences) * enumerateAll(new LinkedList<>(vars), evidenceMap);
            evidenceMap.remove(Y);
            return yTrue + yFalse;
        }
    }

    private static List<Evidence> getYParentsEvidences(Map<Variable, Boolean> evidenceMap, Variable y) {
        return y.getParents().stream()
                        .map(variable -> new Evidence(variable,evidenceMap.get(variable)))
                        .collect(Collectors.toList());
    }





// TODO may be much more efficient
//    private static List<Variable> getRelevantVars(List<Variable> bayesVariables, Variable x, List<Variable> evidences){
//        List<Variable> relevant = new LinkedList<>();
//        HashSet<Variable> ancestors = new HashSet<>();
//        for (Variable v : evidences){
//            Variable curr = v;
//            relevant.add(curr);
//            while(! curr.getParents().isEmpty()){
//                relevant.addAll(curr.getParents());
//            }
//        }
//        for (Variable v : bayesVariables){
//            Variable curr = v;
//            while(curr != null){
//                if(curr)
//            }
//        }
//
//    }
}
