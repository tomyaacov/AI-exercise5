import config.HurricaneGraph;
import config.HurricaneNode;
import lombok.Getter;
import lombok.Setter;
import org.graphstream.graph.Edge;
import parser.Parser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MDP {

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private HurricaneGraph graph;


    //TODO should be private
    public List<State> initialize(){
        List<State> states = new LinkedList<>();
        State init = calculateInitState();
        states.add(init);
        return states;
    }

    private State calculateInitState() {
        Map<String, Integer> peopleInNodes = getInitializePeopleInNodes();
        Map<String, Boolean> blockageThreat = getThreatBlockageEdges();
        return new State(
                graph.getAttribute("start"), peopleInNodes, blockageThreat,
                0, graph.getAttribute("deadline"), 0
        );
    }

    private Map<String, Boolean> getThreatBlockageEdges() {
        return graph.getEdgeSet().stream()
                .filter(edge -> ! edge.getAttribute("blockProb").equals(0))
                .collect(Collectors.toMap(
                    entry -> entry.getId(),
                    entry -> null));
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
        List<Edge> unknownEdges = new LinkedList<>();
        for (Edge e:node.getEachLeavingEdge()){
            if (state.getBlockedEdge().get(e.getId()) == null){
                unknownEdges.add(e);
            }
        }

        return stateProbabilityList;
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
        MDP m = new MDP();
        List<Map<String, Boolean>> allMaps = new LinkedList<>();
        m.getAllEdgeCombinations(unknownBlockage, new LinkedList<>(), allMaps);
        System.out.println(allMaps);
        System.out.println((m.initialize()));

    }
}
