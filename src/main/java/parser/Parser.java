package parser;

import config.HurricaneGraph;
import config.HurricaneNode;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import simulator.SimulatorContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Parser {

    public SimulatorContext parseFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HurricaneGraph graph = configGraph();

        parseVerticesNumber(reader.readLine(), graph);
        parseEdges(reader, graph);
        parseVertices(reader, graph);
        int deadline = parseDeadline(reader.readLine(), graph);

        SimulatorContext context = new SimulatorContext();
        context.setGraph(graph);
        context.setDeadline(deadline);
        return context;
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
        boolean shelter = vertexData[2].toLowerCase().equals("s");
        int people = vertexData.length > 3 ? Integer.parseInt(vertexData[3]) : 0;

        HurricaneNode currNode = graph.getNode(vertexId);
        setNodeAttributes(shelter, people, currNode);
    }

    private void setNodeAttributes(boolean shelter, int people, HurricaneNode currNode) {
        currNode.setPeople(people);
        currNode.setShelter(shelter);
        currNode.addAttribute("ui.label", currNode);
        if (shelter){
            currNode.addAttribute("ui.class", "shelter");
        }
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
                        "	fill-color: black;" +
                        "}" +
                        "node.shelter {" +
                        "	fill-color: red;" +
                        "}" +
                        "edge {" +
                        "text-size: 24px;\n" +
                        "}";
        graph.addAttribute("ui.stylesheet", styleSheet);
        return graph;
    }

    private void parseEdge(Graph graph, String line) {
        line = clearComments(line);
        String[] edgeInfo = line.split(" ");
        int edgeWeight = Integer.valueOf(edgeInfo[3].substring(1));

        Edge e = graph.addEdge(edgeInfo[1] + "-" + edgeInfo[2], edgeInfo[1], edgeInfo[2]);
        setEdgeAttributes(edgeWeight, e);
    }

    private void setEdgeAttributes(int edgeWeight, Edge e) {
        e.addAttribute("weight", edgeWeight);
        e.addAttribute("ui.label", edgeWeight);
        e.setAttribute("ui.class", "edge");
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

    private int parseDeadline(String deadlineToParse, Graph graph){
        deadlineToParse = clearComments(deadlineToParse);
        return Integer.valueOf(deadlineToParse.split(" ")[1]);
    }


//TODO del
    public static void main(String[] args) throws IOException {

        Parser p = new Parser();
        p.parseFile("src\\main\\resources\\graph");

    }
}
