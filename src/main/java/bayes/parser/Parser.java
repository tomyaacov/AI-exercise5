package bayes.parser;

import bayes.BayesNetwork;
import bayes.variables.Blockage;
import bayes.variables.Flooding;
import bayes.variables.Variable;
import config.HurricaneGraph;
import config.HurricaneNode;
import org.graphstream.graph.Edge;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public BayesNetwork initBayes(HurricaneGraph graph){

        BayesNetwork network = new BayesNetwork();
        Map<String, Flooding> floodings = new HashMap<>();

        graph.getEachNode().forEach(node -> floodings.put(node.getId(),initFloodVariable((HurricaneNode) node)));

        graph.getEachEdge().forEach(edge -> );

    }

    private Flooding initFloodVariable(HurricaneNode node) {
        Flooding currFlooding = new Flooding();
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
}
