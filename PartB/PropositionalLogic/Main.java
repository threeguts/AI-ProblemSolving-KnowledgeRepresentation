
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        try {
            KnowledgeBase base = Diavastis();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input type to be proven:");
            String input = scanner.nextLine().trim();
            Literal check = new Literal(input, false);
            boolean result = ForwardChaining.forwardChain(base, check);
            if (result) {
                System.out.println(check.getName() + " has been proven");
            } else {
                System.out.println(check.getName() + " can't be proved");
            }
            scanner.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
  
    public static KnowledgeBase Diavastis() throws IOException{
            String line;
            Boolean key = true;
            BufferedReader reader = new BufferedReader(new FileReader("Rulebook.txt"));
            KnowledgeBase basi = new KnowledgeBase();

            while ((line = reader.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) {continue;}
                if (line.equals("#Facts")){
                    key = true;
                    continue;
                }
                if (line.equals("#Rules")){
                    key = false;
                    continue;
                }
                if (key) {
                    if (line.startsWith("NOT")){
                        line = line.substring(4).trim();
                        basi.addFact(new Literal(line, true));
                    }else{
                        basi.addFact(new Literal(line, false));
                    }
                } else {
                    String[] parts = line.split("->");
                    Literal conclusion;
                        if (parts[1].startsWith("NOT")){
                            parts[1] = parts[1].substring(4).trim();
                            conclusion = new Literal(parts[1].trim(),true);
                        }else{
                            conclusion = new Literal(parts[1].trim(),false);
                        }
                        String[] premises = parts[0].split(",");
                        List<Literal> premisesList = new ArrayList<>();
                        for (String premise : premises) {
                            if (premise.startsWith("NOT")){
                                premise = premise.substring(4).trim();
                                premisesList.add(new Literal(premise.trim(),true));
                            }else{
                                premisesList.add(new Literal(premise.trim(),false));
                            }
                            
                        }
                        basi.addRule((new Rules(premisesList, conclusion)));
                }
            }

            reader.close(); 
            return basi;
    }
}


    

