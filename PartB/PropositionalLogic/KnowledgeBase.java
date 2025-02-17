
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KnowledgeBase {
    private Set<Literal> facts; 
    private List<Rules> rules; 

    public KnowledgeBase() {
        this.facts = new HashSet<>();
        this.rules = new ArrayList<>();
    }
    public void addFact(Literal fact) {
        this.facts.add(fact);
    }

    public void addRule(Rules rule) {
        this.rules.add(rule);
    }

    public boolean isFact(Literal fact) {
        return this.facts.contains(fact);
    }

    public Set<Literal> getFacts() {
        return this.facts;
    }

    public List<Rules> getRules() {
        return this.rules;
    }
}