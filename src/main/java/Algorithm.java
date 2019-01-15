import java.util.List;

public class Algorithm {

    public static void ValueIteration(MDP mdp, double discount, double epsilon){
        double tempValue;
        double delta = Double.MAX_VALUE;
        while (delta >= epsilon){
            delta = 0;
            for (State s: mdp.getStates()){
                tempValue = s.getReward() + discount*getMaxExcpectedValue(mdp, s);
                if (Math.abs(tempValue-s.getUtility()) > delta){
                    delta = Math.abs(tempValue-s.getUtility());
                }
                s.setUtility(tempValue);
            }
        }
    }

    public static double getMaxExcpectedValue(MDP mdp, State s){
        double maxExpectedValue = 0;
        Action bestAction = null;
        List<Action> possibleActions = mdp.getAllActions(s);
        for(Action a : possibleActions){
            double currentExpectedValue = 0;
            List<StateProbability> stateProbabilities = mdp.transitionFunction(s, a);
            for (StateProbability sp : stateProbabilities){
                currentExpectedValue += sp.getValue();
            }
            if (currentExpectedValue >= maxExpectedValue){
                maxExpectedValue = currentExpectedValue;
                bestAction = a;
            }
        }
        s.setBestAction(bestAction);
        return maxExpectedValue;
    }
}
