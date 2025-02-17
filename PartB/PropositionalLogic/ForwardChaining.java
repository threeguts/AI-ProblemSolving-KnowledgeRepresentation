
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ForwardChaining {
    public static boolean forwardChain(KnowledgeBase base, Literal check) {
        Queue<Literal> agenda = new LinkedList<>(base.getFacts());//gnwsta facts pou anakiklwnontai oso kanoume pop
        Set<Literal> inferred = new HashSet<>();//facts pou exagoume
        List<Rules> rules = base.getRules();

        Map<Rules, Integer> count = new HashMap<>();// count = num of premises
        for (Rules rule :rules) {
            count.put(rule, rule.getPremises().size());
        }

        while (!agenda.isEmpty()) {
            Literal p = agenda.poll();
            if (p.equals(check)) {return true;}//an i zoi mas einai efkoli to vriskei
            if (!inferred.contains(p)) {//allios vazoume to fact sta apodeigmena an dn einai idi
                inferred.add(p);
                for (Rules rule : rules ) {//psaxnoume sta rules an to fact afto einai proipothesi. se osa eibnai to svinoume mexri n exoume vrei oles tis proipotheseis gia kapoio rule...
                    if (rule.hasPremise(p)) {
                        count.put(rule, count.get(rule) - 1);
                        if (count.get(rule) == 0) {
                            Literal conclusion = rule.getConclusion();
                            if (!base.isFact(conclusion) && !inferred.contains(conclusion)) {
                                agenda.add(conclusion);//tote mporoume na to valoume stin agenda kai na to checkaroume!!!yay!!!
                            }
                        }
                    }
                }
            }
        }
        return false;//e an dn einai efkoli i zoi toulaxiston prospathisame...false loipon...
    }
}

