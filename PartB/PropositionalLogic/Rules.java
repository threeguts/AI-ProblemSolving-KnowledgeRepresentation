

import java.util.List;

class Rules {
    private List<Literal> premises;  //proipotheseis kanona
    private Literal conclusion;

    public Rules(List<Literal> premises, Literal conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }

    public List<Literal> getPremises() {
        return this.premises;
    }

    public Literal getConclusion() {
        return this.conclusion;
    }

    public boolean hasPremise(Literal fact) {
        return premises.contains(fact);
    }

    public void addPremise(Literal premise) {
        premises.add(premise);
    }


}