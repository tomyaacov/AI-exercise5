import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import parser.Parser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;

    public MDP(HurricaneGraph graph) {
        this.graph = graph;
    }

    //TODO should be private
    public Set<State> initialize(){
        Set<State> states = new HashSet<>();
        State init = calculateInitState();
        states.add(init);
        calculateStatesBFS(new LinkedList<>(Arrays.asList(init)), states);
        return states;
    }

    private void calculateStatesBFS(List<State> fringe, Set<State> allStates){
        while(!fringe.isEmpty()){
            State s = fringe.remove(0);
            List<Action> actions = getAllActions(s);
            List<State> newStates = buildNewStatesFromAction(s, actions);
            allStates.addAll(newStates);
            fringe.addAll(newStates.stream()
                    .filter(state -> ! state.isGoal())
                    .collect(Collectors.toList()));
            fringe = fringe.stream().distinct().collect(Collectors.toList());
        }
    }

    private List<State> buildNewStatesFromAction(State s, List<Action> actions) {
        List<State> states = new LinkedList<>();
        for (Action action: actions){
            states.addAll(transitionFunction(s, action).stream()
                    .map(sp -> sp.getState())
                    .collect(Collectors.toList()));
        }
        return states;
    }

    private List<State> updateFringe(List<State> fringe, List<State> newStates) {
        newStates.stream()
                .filter(state -> ! state.isGoal())
                .collect(Collectors.toList())
                .addAll(fringe);
        return fringe;
    }

    private State calculateInitState() {
        Map<String, Integer> peopleInNodes = getInitializePeopleInNodes();
        Map<String, Boolean> blockageThreat = getThreatBlockageEdges();

        return new State(
                graph.getAttribute("start"), peopleInNodes, blockageThreat,
                0, Integer.parseInt(graph.getAttribute("deadline")), 0
        );
    }

    private Map<String, Boolean> getThreatBlockageEdges() {
        Map<String, Boolean> blockageThreat = new HashMap<>();
        for (Edge edge : graph.getEdgeSet()){
            if (Double.valueOf(edge.getAttribute("blockProb").toString()) == 0){
                blockageThreat.put(edge.getId(), false);
            } else if (Double.valueOf(edge.getAttribute("blockProb").toString()) == 1){
                blockageThreat.put(edge.getId(), true);
            } else {
                blockageThreat.put(edge.getId(), null);
            }
        }
        return blockageThreat;
    }

    private Map<String, Integer> getInitializePeopleInNodes() {
        List<HurricaneNode> nodes = new LinkedList<>(graph.getNodeSet());
        return nodes.stream().collect(Collectors.toMap(
                        entry -> entry.getId(),
                        entry -> entry.getEvacuees()));
    }

    public List<StateProbability> transitionFunction(State state, Action action){
        List<StateProbability> stateProbabilityList = new LinkedList<>();
        HurricaneNode node = graph.getNode(action.getTo());
        List<Edge> unknownBlockage = new LinkedList<>();
        for (Edge e:node.getEachLeavingEdge()){
            if (e.equals(action.getEdge())){
                continue;
            }
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
                if (state.getTime()-(int)e.getAttribute("weight") >= 0){//there is enough time
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
//        System.out.println(allMaps);
//        for (Map<String, Boolean> map : allMaps){
//            System.out.println(m.computeProbability(map));
//        }
        System.out.println((m.initialize().size()));

    }
}
