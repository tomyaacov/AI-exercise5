package parser;

import config.HurricaneGraph;
import config.HurricaneNode;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Parser {

    public HurricaneGraph parseFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HurricaneGraph graph = configGraph();

        parseVerticesNumber(reader.readLine(), graph);
        parseVertices(reader, graph);
        parseEdges(reader, graph);
        parseDeadline(reader,graph);
        parseStatrPosition(reader, graph);
        parseShelter(reader, graph);
        return graph;
    }

    private void parseDeadline(BufferedReader reader, HurricaneGraph graph) throws IOException {
        String line = reader.readLine();
        line = clearComments(line);
        graph.addAttribute("deadline", line.split(" ")[1]);
    }

    private void parseStatrPosition(BufferedReader reader, HurricaneGraph graph) throws IOException {
        String line = reader.readLine();
        line = clearComments(line);
        graph.addAttribute("start", line.split(" ")[1]);
    }

    private void parseShelter(BufferedReader reader, HurricaneGraph graph) throws IOException {
        String line = reader.readLine();
        line = clearComments(line);
        graph.addAttribute("shelter", line.split(" ")[1]);
    }

    private void parseVertices(BufferedReader reader, Graph graph) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && line.startsWith("V")) {
            parseVertex(line, graph);
        }
    }

    private void parseVertex(String vertexToParse, Graph graph) {
        vertexToParse = clearComments(vertexToParse);
        String[] vertexData = vertexToParse.split(" ");
        String vertexId = vertexData[1];
        int evacuees = 0;
        if (vertexData.length>2){
            evacuees = Integer.valueOf(vertexData[2]);
        }
        HurricaneNode currNode = graph.getNode(vertexId);
        currNode.setEvacuees(evacuees);
        currNode.addAttribute("ui.label", currNode);
    }


        private void parseEdges(BufferedReader reader, Graph graph) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && line.startsWith("E")) {
            parseEdge(graph, line);
        }
    }

    private HurricaneGraph configGraph() {
        HurricaneGraph graph = new HurricaneGraph("Tutorial 1");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        String styleSheet =
                "node {" +
                    "text-size: 16px;" +
                    "fill-color: black;" +
                "}" +
                "node.shelter {" +
                    "fill-color: green;" +
                "}" +
                "edge {" +
                    "text-size: 24px;" +
                    "fill-color: black;" +
                "}" +
                "edge.block{" +
                    "fill-color: red;" +
                "}";
        graph.addAttribute("ui.stylesheet", styleSheet);
        return graph;
    }

    private void parseEdge(Graph graph, String line) {
        line = clearComments(line);
        String[] edgeInfo = line.split(" ");
        int edgeWeight = Integer.valueOf(edgeInfo[3].substring(1));
        double blockProbability = 0;
        if (edgeInfo.length > 4){
            blockProbability = Double.valueOf(edgeInfo[4]);
        }
        Edge e = graph.addEdge(edgeInfo[1] + "-" + edgeInfo[2], edgeInfo[1], edgeInfo[2]);
        setEdgeAttributes(edgeWeight, blockProbability, e);
    }

    private void setEdgeAttributes(int edgeWeight, double blockProbability, Edge e) {
        e.addAttribute("weight", edgeWeight);
        e.addAttribute("blockProb", blockProbability);

        e.addAttribute("ui.label", edgeWeight);
        e.setAttribute("ui.class", "edge");
        e.addAttribute("block", false);
        e.addAttribute("ui.label", e.getAttribute("blockProb").toString());


    }

    private String clearComments(String line) {
        return line.split(";")[0].trim();
    }

    private void parseVerticesNumber(String verticesToParse, Graph graph){
        verticesToParse = clearComments(verticesToParse);
        int verticesNumber = Integer.valueOf(verticesToParse.trim().split(" ")[1]);
        for (int i = 0; i < verticesNumber; i++){
            graph.addNode(String.valueOf(i+1)).addAttribute("ui.label", "node id: " + (i+1));
        }
    }




    public static void main(String[] args) throws IOException {

        Parser p = new Parser();
        HurricaneGraph s =p.parseFile("src\\main\\resources\\graph");
        s.display();
    }
}
