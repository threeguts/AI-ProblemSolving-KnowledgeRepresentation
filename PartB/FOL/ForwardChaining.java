import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ForwardChaining{
    List<Clause> knowledgeBase = new ArrayList<>();
    List<Predicate> Facts = new ArrayList<>();
    Unifier unifier = new Unifier();
    Set<Predicate> derivedFacts = new HashSet<>();
    Map<String, String> reverseMap = new HashMap<>();

    public ForwardChaining(List<Clause> knowledgeBase) {
        this.knowledgeBase=knowledgeBase;
    }

    void updateKB(List<Clause> KB){
        this.knowledgeBase = KB;
    }

    void addClause(Clause clause){
        knowledgeBase.add(clause);
    }

    public boolean forwardChain(Predicate FOLstatement){
        
        for(Clause clause : knowledgeBase){
            if(clause.getPredicates().size()==1){                                                //it's a fact eg. Human(Socrates)
                Predicate fact= clause.getPredicates().get(0);                             //get first element from predicate list
                if (!derivedFacts.contains(fact)){                                               //prevent duplicate inferences
                    derivedFacts.add(fact);
                    Facts.add(fact);

                    if (fact.toString().equals(FOLstatement.toString())){return true;}           //if the FOLstatement that the user gave us is the same as an already existing fact then it's automatically true 
                    if(checkTermsIdenticality(fact, FOLstatement)){return true;}
                }
            }
            for(Predicate predicate : clause.getPredicates()){                                   //for each predicate eg Love(x,John), Mortal(Socrates)
                
                for(int l=0;l<FOLstatement.terms.size();l++){                                    //for each term of our goal FOL
                    for(int i=0;i<predicate.terms.size();i++){
                        Term currentGoalTerm=FOLstatement.terms.get(l);
                        Term currectPredicateTerm=predicate.terms.get(i);   
                        
                        unifier.substitutions.entrySet().removeIf(entry -> reverseMap.putIfAbsent(entry.getValue(), entry.getKey()) != null); //if our predicate has to variable terms, dont have duplicate values eg Screams(x,y)!=Screams(Mary,Mary)
                        if(unifier.unify(currectPredicateTerm,currentGoalTerm)){
                            
                            for(Clause clause2 : knowledgeBase){                                 //after getting the unified subs, we begin the process of chaging each variable with our substitution
                                for(Predicate predicate2:clause2.getPredicates()){
                                    List<Term> insideTerms= new ArrayList<>();
                                    for(String value: unifier.substitutions.keySet()){ 
                                        for(int j=0; j<predicate2.terms.size();j++){
                                            if(value.equals(predicate2.terms.get(j).toString())){
                                                if(j>0){                                         //this loop ensures that rules with multiple terms can be done correctly
                                                    for(int k=0;k<predicate2.terms.size();k++){
                                                        if(!predicate2.terms.get(k).isVariable){
                                                            insideTerms.add(predicate2.terms.get(k));
                                                        }
                                                    }
                                                    insideTerms.remove(predicate2.terms.get(j));
                                                }
                                                
                                                Term newTerm = new Term(unifier.substitutions.get(value));
                                                insideTerms.add(newTerm);
                                                Predicate newFact= new Predicate(predicate2.getName(),insideTerms,predicate2.isNegated);
                                                if(newFact.toString().equals(FOLstatement.toString())){return true;}
                                                if(!Facts.contains(newFact)){
                                                    if (!derivedFacts.contains(newFact)){                                     //prevent duplicate inferences
                                                        derivedFacts.add(newFact);
                                                        Facts.add(newFact);
                                                        if(newFact.toString().equals(FOLstatement.toString())){ return true;} //check if our current newly made fact is the FOL statement we are looking to prove
                                                        if(checkTermsIdenticality(newFact, FOLstatement)){return true;}       //need to check if our new predicate already exists but with just opposite term placement eg Loves(Mary,John), Loves(John,Mary)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }    
                        }
                    }
                }
            }
        
        }return false;
    }
    
    public boolean checkTermsIdenticality(Predicate predicate, Predicate goal){
        int similarTermsCounter=0;
        if(goal.terms.size()==predicate.terms.size() && goal.getName().equals(predicate.getName())){      //if our current term has the same amount of terms as our FOL statementsearch for similarities
            for(int i=0;i<goal.terms.size();i++){                                                         //loop through goals and predicate's terms to search for similar terms 
                for(int j=0;j<goal.terms.size();j++){
                    if(goal.terms.get(i).toString().equals(predicate.terms.get(j).toString())){
                        similarTermsCounter++;
                    }
                }
            }if(similarTermsCounter==goal.terms.size()){return true;}
        }
        return false;
    }

}