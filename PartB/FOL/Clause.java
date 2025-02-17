import java.util.*;

class Clause {
    List<Predicate> predicates;

    Clause() { this.predicates = new ArrayList<Predicate>(); }

    Clause(List<Predicate> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clause other = (Clause) obj;
        return Objects.equals(new HashSet<>(predicates), new HashSet<>(other.predicates)); // Compare sets of predicates
    }

    public List<Predicate> getPredicates(){
        return this.predicates;
    }
    
    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }

    public boolean isEmpty(){
        return predicates.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(new HashSet<>(predicates));
    }

    @Override
    public String toString() {
        return predicates.toString();
    }
}
