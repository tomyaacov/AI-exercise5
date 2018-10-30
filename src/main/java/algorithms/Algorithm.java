package algorithms;

import config.HurricaneNode;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
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
        List<HurricaneNode> Q = new ArrayList<HurricaneNode>(simulatorContext.getGraph().getNodeCount());
        List<Double> dist = new ArrayList<Double>(simulatorContext.getGraph().getNodeCount());
        List<HurricaneNode> prev = new ArrayList<HurricaneNode>(simulatorContext.getGraph().getNodeCount());
        List<Integer> nodeCarrying = new ArrayList<Integer>(simulatorContext.getGraph().getNodeCount());

        for(Node n:simulatorContext.getGraph()) {
            Q.add(Integer.parseInt(n.getId())-1, (HurricaneNode)n);
            dist.add(Integer.parseInt(n.getId())-1, Double.MAX_VALUE);
            prev.add(Integer.parseInt(n.getId())-1, null);
            nodeCarrying.add(Integer.parseInt(n.getId())-1, 0);

        }

        dist.set(Integer.parseInt(source.getId())-1, 0.0);
        nodeCarrying.set(Integer.parseInt(source.getId())-1, sourceCarrying);

        while (!Q.isEmpty()){
            u = Q.remove(dist.indexOf(Collections.min(dist)));

            Iterator<Edge> it = u.getEdgeIterator();
            while (it.hasNext()){
                currentEdge = it.next();
                v = currentEdge.getOpposite(u);
                alt = dist.get(Integer.parseInt(u.getId())-1) + currentEdge.getNumber("weight")
                        * (1 + simulatorContext.getK() * nodeCarrying.get(Integer.parseInt(u.getId())-1) );
                if (alt < dist.get(Integer.parseInt(v.getId())-1)){
                    dist.set(Integer.parseInt(v.getId())-1, alt);
                    prev.set(Integer.parseInt(v.getId())-1, u);
                    if (!v.isShelter()){
                        nodeCarrying.set(Integer.parseInt(v.getId())-1, nodeCarrying.get(Integer.parseInt(u.getId())-1) + v.getPeople());
                    }
                }
            }
        }

        return new DijkstraOutput(dist, prev, nodeCarrying);
    }

    public static void main(String[] args) throws Exception{
        Parser parser = new Parser();
        SimulatorContext context = parser.parseFile("src.main.resources.graph_dijkstra_test".replace(".", File.separator));
        context.setK(0.5);
        Viewer view = context.getGraph().display();
        HurricaneNode source = context.getGraph().getNode("1");
        DijkstraOutput output = Algorithm.dijkstra(context, source, 0);
        System.out.println(output);

    }
}
