package bayes.parser;

import bayes.BayesNetwork;
import bayes.variables.Blockage;
import bayes.variables.Evacuees;
import bayes.variables.Flooding;
import bayes.variables.Variable;
import config.HurricaneGraph;
import config.HurricaneNode;
import org.graphstream.graph.Edge;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    public BayesNetwork initBayes(HurricaneGraph graph){

        BayesNetwork network = new BayesNetwork();
        Map<String, Flooding> floodingsMap = new HashMap<>();
        Map<String, Blockage> blockagesMap = new HashMap<>();
        Map<String, Evacuees> evacueesMap = new HashMap<>();


        graph.getEachNode().forEach(node -> floodingsMap.put(node.getId(),initFloodVariable((HurricaneNode) node)));

        graph.getEachEdge().forEach(edge -> blockagesMap.put(edge.getId(),initBlockageVariable(edge, floodingsMap)));

        graph.getEachNode().forEach(node -> evacueesMap.put(node.getId(), initEvacueeVariable((HurricaneNode) node, blockagesMap)));

        List<Variable> all = getAllVariables(floodingsMap, blockagesMap, evacueesMap);
        network.setVariables(all);

        return  network;
    }


    private List<Variable> getAllVariables(Map<String, Flooding> floodingsMap, Map<String, Blockage> blockagesMap, Map<String, Evacuees> evacueesMap) {
        List<Variable> floodingList = floodingsMap.values().stream().collect(Collectors.toList());
        List<Variable> blockagesList = blockagesMap.values().stream().collect(Collectors.toList());
        List<Variable> evacueesList = evacueesMap.values().stream().collect(Collectors.toList());

        return Stream.of(floodingList, blockagesList, evacueesList)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    private Flooding initFloodVariable(HurricaneNode node) {
        Flooding currFlooding = new Flooding(node.getFloodingProb());
        currFlooding.setId(node.getId());
        currFlooding.setParents(null);
        return currFlooding;
    }

    private Blockage initBlockageVariable(Edge e, Map<String, Flooding> floodings){
        Blockage block = new Blockage(e.getAttribute("weight"));
        block.setId(e.getId());
        String id0 = e.getNode0().getId();
        String id1 = e.getNode1().getId();
        block.setParents(List.of(floodings.get(id0), floodings.get(id1)));
        return block;
    }

    private Evacuees initEvacueeVariable(HurricaneNode node, Map<String, Blockage> blockageMap){
        Evacuees evacuee = new Evacuees();
        evacuee.setId(node.getId());

        List<Variable> parents = getEvacuteeParents(node, blockageMap);
        evacuee.setParents(parents);
        return evacuee;

    }

    private List<Variable> getEvacuteeParents(HurricaneNode node, Map<String, Blockage> blockageMap) {
        List<Variable> parents = new LinkedList<>();
        Iterator<Edge> it = node.getEdgeIterator();
        while(it.hasNext()){
            Edge e = it.next();
            String id = e.getId();
            parents.add(blockageMap.get(id));
        }

        return parents;
    }

    public static void main(String[] args) throws IOException {
        parser.Parser p = new parser.Parser();
        HurricaneGraph s =p.parseFile("src\\main\\resources\\graph");
        Parser ps = new Parser();
        BayesNetwork bayes= ps.initBayes(s);
        System.out.println();

    }

}
