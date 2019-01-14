import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import parser.Parser;

import java.io.IOException;
import java.util.*;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;

    public MDP(HurricaneGraph graph) {
        this.graph = graph;
    }

    void initialize(){
        List<State> states = new LinkedList<>();
//        graph.getNode(1).get

    }

    public List<StateProbability> transitionFunction(State state, Action action){
        List<StateProbability> stateProbabilityList = new LinkedList<>();
        HurricaneNode node = graph.getNode(action.getTo());
        List<Edge> unknownBlockage = new LinkedList<>();
        for (Edge e:node.getEachLeavingEdge()){
            if (state.getBlockedEdge().get(e.getId()) == null){
                unknownBlockage.add(e);
            }
        }
        List<Map<String, Boolean>> allMaps = new LinkedList<>();
        getAllEdgeCombinations(unknownBlockage, new LinkedList<>(), allMaps);
        for (Map<String, Boolean> map : allMaps){
            State newState = buildNewState(state, map, node.getId(), action.getEdge());
            double probability = computeProbability(map);
            stateProbabilityList.add(new StateProbability(newState, probability));
        }

        return stateProbabilityList;
    }

    public State buildNewState(State oldState, Map<String, Boolean> unknownBlockageMap, String nodeId, Edge edge){
        String location = nodeId;
        Map<String, Integer> peopleInVertex = new HashMap<>(oldState.getPeopleInVertex());
        Map<String, Boolean> blockedEdge =  new HashMap<>(oldState.getBlockedEdge());
        blockedEdge.putAll(unknownBlockageMap);
        int peopleSaved = oldState.getPeopleSaved();
        int time = oldState.getTime() - (int)edge.getAttribute("weight");
        int carrying = oldState.getCarrying();
        if (graph.getAttribute("shelter").equals(nodeId)){
            peopleSaved += carrying;
            carrying = 0;
        } else {
            carrying += peopleInVertex.get(nodeId);
            peopleInVertex.put(nodeId, 0);
        }
        return new State(location, peopleInVertex, blockedEdge, peopleSaved, time, carrying);

    }

    public double computeProbability(Map<String, Boolean> unknownBlockageMap){
        double probability = 1;
        for (Map.Entry<String, Boolean> entry : unknownBlockageMap.entrySet()) {
            if(entry.getValue()){
                probability *= (double)graph.getEdge(entry.getKey()).getAttribute("blockProb");
            } else {
                probability *= (1-(double)graph.getEdge(entry.getKey()).getAttribute("blockProb"));
            }
        }
        return probability;
    }

    public List<Action> getAllActions(State state){
        List<Action> actionList = new LinkedList<>();
        if (state.isGoal()){
            return actionList;
        }
        HurricaneNode node = graph.getNode(state.getLocation());
        for(Edge e:node.getEachEdge()){
            if(!"B".equals(state.getBlockedEdge().get(e.getId()))){//Edge is not blocked
                if (state.getTime()-(double)e.getAttribute("weight") >= 0){//there is enough time
                    Action a = new Action(node.getId(), e.getOpposite(node).getId(), e);
                    actionList.add(a);
                }
            }
        }
        return actionList;
    }

    public void getAllEdgeCombinations(List<Edge> unknownBlockage,
                                        List<Boolean> combination,
                                        List<Map<String, Boolean>> allMaps){
        if (unknownBlockage.size() == combination.size()){
            Map<String, Boolean> map = new HashMap<>();
            for (int i=0; i < unknownBlockage.size(); i++){
                map.put(unknownBlockage.get(i).getId(), combination.get(i));
            }
            allMaps.add(map);
        } else {
            combination.add(false);
            getAllEdgeCombinations(unknownBlockage, combination, allMaps);
            combination.remove(combination.size()-1);
            combination.add(true);
            getAllEdgeCombinations(unknownBlockage, combination, allMaps);
            combination.remove(combination.size()-1);
        }

    }

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        HurricaneGraph s =p.parseFile("src//main//resources//graph");
        Edge e1 = s.getEdge("2-4");
        Edge e2 = s.getEdge("2-3");
        Edge e3 = s.getEdge("2-5");

        List<Edge> unknownBlockage = new LinkedList<>();
        unknownBlockage.add(e1);
        unknownBlockage.add(e2);
        unknownBlockage.add(e3);
        MDP m = new MDP(s);
        List<Map<String, Boolean>> allMaps = new LinkedList<>();
        m.getAllEdgeCombinations(unknownBlockage, new LinkedList<>(), allMaps);
        System.out.println(allMaps);
        for (Map<String, Boolean> map : allMaps){
            System.out.println(m.computeProbability(map));
        }
    }
}
