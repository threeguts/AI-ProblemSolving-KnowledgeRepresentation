import java.util.*;

class Unifier {
    Map<String, String> substitutions = new HashMap<>();

    boolean unify(Term x, Term y) {
        if (x.isVariable) return substitute(x.name, y.name);
        if (y.isVariable) return substitute(y.name, x.name);
        return x.name.equals(y.name);
    }

    private boolean substitute(String var, String value) {
        if (substitutions.containsKey(var)) {
            return substitutions.get(var).equals(value);
        }
        substitutions.put(var, value);
        return true;
    }

    public boolean unify(List<Term> terms1, List<Term> terms2) {
        if (terms1.size() != terms2.size()) {
            return false; // cant unify if arity differs
        }

        for (int i = 0; i < terms1.size(); i++) {
            if (!unify(terms1.get(i), terms2.get(i))) {
                return false; // fail if any pair of terms fails to unify
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return substitutions.toString();
    }
}