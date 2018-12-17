package bayes;


import bayes.variables.Variable;
import lombok.Getter;
import lombok.Setter;



public class Evidence {

    @Getter @Setter
    private Variable var;

    @Setter @Getter
    private boolean value;

    public Evidence(Variable var, boolean value) {
        this.var = var;
        this.value = value;
    }

    @Override
    public String toString() {
        return (value ? "" : "not ") + var.getClass().getSimpleName() + " " + var.getId();
    }

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if (!(other instanceof Evidence)) {
            return false;
        }
        Evidence evidence = (Evidence) other;

        if (! var.getClass().getSimpleName().equals((other.getClass().getSimpleName()))){
            return false;
        }
        return var.getId().equals(((Evidence) other).getVar().getId());
    }

}
