import lombok.Getter;
import lombok.Setter;

public class Action {

    @Getter @Setter
    private String from;

    @Getter @Setter
    private String to;

    public Action(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "move from " + from + " to " + to;
    }
}
