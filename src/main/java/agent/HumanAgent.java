package agent;

import config.HurricaneNode;
import org.graphstream.graph.Edge;
import simulator.SimulatorContext;

import java.util.Iterator;
import java.util.Scanner;

public class HumanAgent extends Agent{

    private Scanner scanner;
    private SimulatorContext context;

    public HumanAgent(SimulatorContext context){
        super();
        this.context = context;
        scanner =  new Scanner(System.in);
    }

    @Override
    public HurricaneNode doNextAction() {
        while (true) {
            System.out.println("Please choose human agent next move: 1) traverse.  2) no-op.");
            int operation = 0;
            try {
                operation = scanner.nextInt();
            } catch (Exception e){}

            if (operation == 1) {
                return traverse();
            } else if (operation == 2) {
                setTime(getTime() + 1);
                return getCurrNode();
            } else {
                System.out.println("Illegal operation, please try again.");
            }
        }
    }

    @Override
    public HurricaneNode traverse() {
        String nodeId = null;
        Edge e = null;
        while (nodeId == null || e == null) {
            try {
                System.out.println("Human agent traverse: please choose node number to explore:");
                nodeId = scanner.next();
                e = getCurrNode().getEdgeBetween(nodeId);
                if (e == null) {
                    System.out.println("No edge between " + getCurrNode().getId() + " and " + nodeId + ". Please try again.");
                }
            } catch (Exception e1) {
                System.out.println("Illegal value. Please try again.");
            }
        }
        if(isEnoughTime(e)){
            setTime(calculateTraverseOperation(e) + getTime());
            setCurrNode(context.getGraph().getNode(nodeId));
            return getCurrNode();
        }
        return null; //no enough time, agent done
    }

    private boolean isEnoughTime(Edge e) {
        // currTime + w(1+Kp) < deadline ?
        return getTime() + calculateTraverseOperation(e) <= context.getDeadline();
    }

    private double calculateTraverseOperation(Edge e){
        return e.getNumber("weight") * (1 + context.getK() * getCurrNode().getPeople());
    }
}
