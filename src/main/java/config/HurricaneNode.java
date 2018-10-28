package config;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class HurricaneNode extends SingleNode {

    private int people;
    private boolean shelter;

    public HurricaneNode(AbstractGraph graph, String id) {
        super(graph, id);
    }


    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public boolean isShelter() {
        return shelter;
    }

    public void setShelter(boolean shelter) {
        this.shelter = shelter;
    }

    public String toString(){
        String nodeKind = isShelter() ? "Shelter; " : "";
        return "node id:" + getId() + "; " + nodeKind + "people inside: " + getPeople();
    }
}
