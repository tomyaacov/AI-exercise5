package bayes;


import bayes.variables.Variable;
import lombok.Getter;
import lombok.Setter;



public class Evidence {

    @Getter @Setter
    private Variable var;

    @Getter @Setter
    private boolean value;

    public Evidence(Variable var, boolean value) {
        this.var = var;
        this.value = value;
    }

    @Override
    public String toString() {
        return (value ? "" : "not ") + var.getClass().getSimpleName() + " " + var.getId();
    }

}
