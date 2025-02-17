import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        System.out.println("Ente the FOL statement you wish to prove ");
        String FOLstatement = input.nextLine();
        Predicate goal = getPredicate(FOLstatement,false);

        if(goal==null){
            System.out.println("Invalid FOL statement. Please enter a valid predicate.");
            return;
        }


        List<Clause> knowledgeBase = new ArrayList<>();
        String KBfilepath="KB.txt";
        ArrayList<Predicate> rule = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(KBfilepath))){

            String line;
            
            while((line=br.readLine())!=null){
                boolean isNegated=false;                                               //checks if our clause has a NOT
                //if our line has an OR that means that it is a rule e.g Human(x) OR NOT Mortal(x)
                if(line.contains("OR")){
                    String[] parts = line.split("\\s+OR\\s+");
                    for (String part : parts){                                         //seperates the line into its clauses
                        if(part.startsWith("NOT")){                             //if its negative remove the NOT
                            part = part.substring(4).trim();                //removes it
                            isNegated=true;
                            rule.add(getPredicate(part, isNegated));
                        }else{
                            isNegated=false;
                            rule.add(getPredicate(part, isNegated));
                        }
                        if(getPredicate(part, isNegated)!=null){
                            rule.add(getPredicate(part, isNegated));
                        }else{
                            System.out.println("Invalid predicate in rule: " + part);
                            break;
                        }

                    }
                }else if(line!=null){                                                  //if it doesnt have an OR that means that it is a rule
                    rule.add(getPredicate(line, isNegated));
                }
            }
            
            for(Predicate x:rule){knowledgeBase.add(getClause(x));}

        }catch(IOException e){
            System.out.println("Error in the given file."+e.getMessage());
        }
        //begins the forward chaining process
        ForwardChaining fc = new ForwardChaining(knowledgeBase);
        boolean goalReached = fc.forwardChain(goal);

        if(goalReached){
            System.out.println(goal + " = True");
        }else{
            System.out.println(goal + " = False");
        } 
    }


    private static Predicate getPredicate(String input, boolean isNegated) {
        boolean term0Var=false;
        boolean term1Var=false;
        if (input.isEmpty()) {
            return null;
        }
        int startIndex = input.indexOf('(');
        int endIndex = input.indexOf(')');
        if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
            System.out.println("Invalid format for predicate: " + input);
            return null;
        }
        String name = input.substring(0, startIndex);
        String term = input.substring(startIndex + 1, endIndex);
        if (term.isEmpty()) {
            System.out.println("Invalid term in predicate: " + input);
            return null;
        }
        if(term.toString().contains(",")){
            startIndex = term.indexOf(',');
            String term0 = term.substring(0, startIndex);
            String term1 = term.substring(startIndex + 1);
            if(term0.length()==1){term0Var=true;}
            if(term1.length()==1){term1Var=true;}
            Predicate pred =new Predicate(name, Arrays.asList(new Term(term0,term0Var),new Term(term1,term1Var)), isNegated);
            return pred;
        }else{
            if(term.length()==1){term1Var=true;}
            return new Predicate(name, Arrays.asList(new Term(term,term1Var)), isNegated);
        }
        
    }
    private static Clause getClause(Predicate predicate){
        //creates the clause object
        Clause clause = new Clause(Arrays.asList(predicate));
        return clause;
    }
}
