package algorithms;

import config.HurricaneGraph;
import config.HurricaneNode;
import entities.State;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import org.graphstream.ui.view.Viewer;
import parser.Parser;
import simulator.SimulatorContext;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class Algorithm {

    public static DijkstraOutput dijkstra(SimulatorContext simulatorContext, HurricaneNode source, int sourceCarrying){

        HurricaneNode u, v;
        Double alt;
        Edge currentEdge;
        List<HurricaneNode> Q = new ArrayList<HurricaneNode>(simulatorContext.getGraph().getNodeCount()+1);
        List<Double> dist = new ArrayList<Double>(simulatorContext.getGraph().getNodeCount()+1);
        List<HurricaneNode> prev = new ArrayList<HurricaneNode>(simulatorContext.getGraph().getNodeCount()+1);
        List<Integer> nodeCarrying = new ArrayList<Integer>(simulatorContext.getGraph().getNodeCount()+1);


        Q.add(0, null);
        dist.add(0, Double.MAX_VALUE);
        prev.add(0, null);
        nodeCarrying.add(0,null);


        for(Node n:simulatorContext.getGraph()) {
            Q.add(Integer.parseInt(n.getId()), (HurricaneNode)n);
            dist.add(Integer.parseInt(n.getId()), Double.MAX_VALUE);
            prev.add(Integer.parseInt(n.getId()), null);
            nodeCarrying.add(Integer.parseInt(n.getId()), 0);

        }

        dist.set(Integer.parseInt(source.getId()), 0.0);
        nodeCarrying.set(Integer.parseInt(source.getId()), sourceCarrying);

        Integer minDist;

        while (true){
            minDist = getMinDist(dist, Q);
            if (minDist == null){break;}
            u = Q.set(minDist, null);


            Iterator<Edge> it = u.getEdgeIterator();
            while (it.hasNext()) {
                currentEdge = it.next();
                if (!HurricaneGraph.isEdgeBlock(currentEdge)) {
                    v = currentEdge.getOpposite(u);
                    alt = dist.get(Integer.parseInt(u.getId())) + currentEdge.getNumber("weight")
                            * (1 + simulatorContext.getK() * nodeCarrying.get(Integer.parseInt(u.getId())));
                    if (alt < dist.get(Integer.parseInt(v.getId()))) {
                        dist.set(Integer.parseInt(v.getId()), alt);
                        prev.set(Integer.parseInt(v.getId()), u);
                        if (!v.isShelter()) {
                            nodeCarrying.set(Integer.parseInt(v.getId()), nodeCarrying.get(Integer.parseInt(u.getId())) + v.getPeople());
                        }
                    }
                }
            }
        }

        return new DijkstraOutput(dist, prev, nodeCarrying);
    }

    public static Integer getMinDist(List<Double> dist, List<HurricaneNode> Q){
        Double currMinDist = Double.MAX_VALUE;
        Integer result = null;
        for (int i = 1; i < dist.size(); i++){
            if(currMinDist > dist.get(i) && Q.get(i) != null){
                currMinDist = dist.get(i);
                result = i;
            }
        }
        return result;
    }

    public static double heuristicFunction(State state){
        return 0;
    }

    public static void main(String[] args) throws Exception{
        Parser parser = new Parser();
        SimulatorContext context = parser.parseFile("src.main.resources.graph_dijkstra_test".replace(".", File.separator));
        context.setK(0.5);
        Viewer view = context.getGraph().display();
        HurricaneNode source = context.getGraph().getNode("2");
        DijkstraOutput output = Algorithm.dijkstra(context, source, 1);
        System.out.println(output);

    }
}
