package config;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;

import java.util.Iterator;

public class HurricaneGraph extends SingleGraph {


    /** @deprecated */
    @Deprecated
    public HurricaneGraph() {
        this("HurricaneGraph");
    }

    public HurricaneGraph(String id) {
        this(id, true, false);
    }

    /** @deprecated */
    @Deprecated
    public HurricaneGraph(boolean strictChecking, boolean autoCreate) {
        this("HurricaneGraph", strictChecking, autoCreate);
    }

    public HurricaneGraph(String id, boolean strictChecking, boolean autoCreate) {
        super(id, strictChecking, autoCreate);
        this.setNodeFactory(new NodeFactory<SingleNode>() {
            public SingleNode newInstance(String id, Graph graph) {
                return new HurricaneNode((AbstractGraph)graph, id);
            }
        });
    }

    public static boolean isEdgeBlock(Edge e){
        return e.getAttribute("block");
    }

    public static void setEdgeBlock(Edge e, boolean block){
        e.setAttribute("block", block);
        if(block){
            e.setAttribute("ui.class", "block");
        } else{
            e.setAttribute("ui.class", "edge");
        }
    }



}
