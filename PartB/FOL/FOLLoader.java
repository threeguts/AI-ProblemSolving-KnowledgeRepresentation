import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FOLLoader {
    protected static Vec2<ArrayList<Clause>, Clause> KB;
    protected static ArrayList<Clause> clauses;

    public void initialize(String filename) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String sCurrentLine, firstLine = br.readLine();
            Clause finalClause, intermediateClause;
            Predicate finalLiteral, intermediateLiteral;
            KB = new Vec2<ArrayList<Clause>, Clause>();
            clauses = new ArrayList<Clause>();
            firstLine = firstLine.trim().replaceAll(" +", " ");
            String[] sizes = firstLine.split(" ");
            boolean check = true;
            if(sizes.length != 1) check = false;
            else if(!(Integer.parseInt(sizes[0]) > 0) ) check = false;
            if (check) {
                String implCurrentLine, andCurrentLine;
                int aCurrentSize, aCurrentSize1, andCurrentSize;
                boolean implicationDiscovery;
                while (((sCurrentLine = br.readLine()) != null) && (check)) {
                    sCurrentLine = sCurrentLine.trim().replaceAll(" +", " ");;
                    String[] aCurrentLine = sCurrentLine.split(" "); // separator is: " " = [whitespace]
                    aCurrentSize = aCurrentLine.length;
                    if(aCurrentLine[0].equals("PROVE")) {
                        System.out.print(sCurrentLine.substring(6, sCurrentLine.length()) + " ");
                        if(sCurrentLine.contains("OR")) {
                            if(sCurrentLine.substring(0, 6).contains("PROVE")) {
                                String[] andArrCurrentLine = sCurrentLine.split("PROVE ")[1].split(" OR "); // separator is: " OR " = [whitespace]+OR+[whitespace]
                                andCurrentSize = andArrCurrentLine.length;
                                finalClause = new Clause();
                                int g;
                                for(g=0; g < andCurrentSize; g++){
                                    aCurrentLine = andArrCurrentLine[g].split(" ");
                                    String[] aCurrentLine1 = aCurrentLine[0].split("\\("); // separator is: "(" = [left parenthesis]
                                    aCurrentSize = aCurrentLine.length;
                                    aCurrentSize1 = aCurrentLine1.length;
                                    if(aCurrentSize1 != 2)check = false;
                                    else {
                                        String name = aCurrentLine1[0];
                                        if (name.contains("NOT")) {
                                            name = name.replace("NOT", "");
                                            intermediateLiteral = new Predicate(name, true);
                                        } else intermediateLiteral = new Predicate(name, false);
                                        if( aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ',' && aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ')')check = false;
                                        else {
                                            intermediateLiteral.getMembers().add(new Term(aCurrentLine1[1].substring(0, aCurrentLine1[1].length()-1)));
                                            for(int j = 1; j < aCurrentSize; j++) {
                                                if((j < aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ',')|| (j == aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ')') ) {
                                                    check = false;
                                                    break;
                                                }
                                                else{
                                                    intermediateLiteral.getMembers().add(new Term(aCurrentLine[j].substring(0, aCurrentLine[j].length()-1)));
                                                }
                                            }
                                            finalClause.getPredicates().add(intermediateLiteral);
                                        }
                                    }
                                }
                                /*Clause Handling*/
                                KB.setYValue(finalClause);
                            } else check = false;
                        }else{
                            finalClause = new Clause();
                            String[] aCurrentLine1 = aCurrentLine[1].split("\\("); // separator is: "(" = [left parenthesis]
                            aCurrentSize1 = aCurrentLine1.length;
                            if(aCurrentSize1 != 2)check = false;
                            else {
                                String name = aCurrentLine1[0];
                                if (name.contains("NOT")) {
                                    name = name.replace("NOT", "");
                                    finalLiteral = new Predicate(name, true);
                                } else finalLiteral = new Predicate(name, false);
                                if( aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ',' &&  aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ')')check = false;
                                else {
                                    finalLiteral.getMembers().add(new Term(aCurrentLine1[1].substring(0, aCurrentLine1[1].length()-1)));
                                    for(int j = 2; j < aCurrentSize; j++) {
                                        if((j < aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ',')|| (j == aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ')') ) {
                                            check = false;
                                            break;
                                        }
                                        else{
                                            finalLiteral.getMembers().add(new Term(aCurrentLine[j].substring(0, aCurrentLine[j].length()-1)));
                                        }
                                    }
                                    finalClause.getPredicates().add(finalLiteral);
                                    KB.setYValue(finalClause);
                                }
                            }
                        }
                    } else {
                        if (sCurrentLine.contains("OR")) {
                            String[] andArrCurrentLine = sCurrentLine.split(" OR "); // separator is: " OR " = [whitespace]+OR+[whitespace]
                            andCurrentSize = andArrCurrentLine.length;
                            intermediateClause = new Clause();
                            int g;
                            for(g=0; g < andCurrentSize; g++){
                                aCurrentLine = andArrCurrentLine[g].split(" ");
                                String[] aCurrentLine1 = aCurrentLine[0].split("\\("); // separator is: "(" = [left parenthesis]
                                aCurrentSize = aCurrentLine.length;
                                aCurrentSize1 = aCurrentLine1.length;
                                if(aCurrentSize1 != 2)check = false;
                                else {
                                    String name = aCurrentLine1[0];
                                    if (name.contains("NOT")) {
                                        name = name.replace("NOT", "");
                                        intermediateLiteral = new Predicate(name, true);
                                    } else intermediateLiteral = new Predicate(name, false);
                                    if( aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ',' && aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ')')check = false;
                                    else {
                                        intermediateLiteral.getMembers().add(new Term(aCurrentLine1[1].substring(0, aCurrentLine1[1].length()-1)));
                                        for(int j = 1; j < aCurrentSize; j++) {
                                            if((j < aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ',')|| (j == aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ')') ) {
                                                check = false;
                                                break;
                                            }
                                            else{
                                                intermediateLiteral.getMembers().add(new Term(aCurrentLine[j].substring(0, aCurrentLine[j].length()-1)));
                                            }
                                        }
                                        intermediateClause.getPredicates().add(intermediateLiteral);
                                    }
                                }
                            }
                            /*Clause Handling*/
                            clauses.add(intermediateClause);
                        } else {
                            String[] aCurrentLine1 = aCurrentLine[0].split("\\("); // separator is: "(" = [left parenthesis]
                            aCurrentSize1 = aCurrentLine1.length;
                            intermediateClause = new Clause();
                            if(aCurrentSize1 != 2)check = false;
                            else {
                                String name = aCurrentLine1[0];
                                if (name.contains("NOT")) {
                                    name = name.replace("NOT", "");
                                    intermediateLiteral = new Predicate(name, true);
                                } else intermediateLiteral = new Predicate(name, false);
                                if( aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ',' && aCurrentLine1[1].charAt(aCurrentLine1[1].length() - 1)!= ')')check = false;
                                else {
                                    intermediateLiteral.getMembers().add(new Term(aCurrentLine1[1].substring(0, aCurrentLine1[1].length()-1)));
                                    for(int j = 1; j < aCurrentSize; j++) {
                                        if((j < aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ',')|| (j == aCurrentSize - 1 && aCurrentLine[j].charAt(aCurrentLine[j].length() - 1)!= ')') ) {
                                            check = false;
                                            break;
                                        }
                                        else{
                                            intermediateLiteral.getMembers().add(new Term(aCurrentLine[j].substring(0, aCurrentLine[j].length()-1)));
                                        }
                                    }
                                    intermediateClause.getPredicates().add(intermediateLiteral);
                                }
                            }
                            clauses.add(intermediateClause);
                        }
                    }
                }
                if(!check) {
                    System.out.println("Wrong file format; please try again.");
                }
            }else {
                System.out.println("Wrong file format; please try again.");
            }
        } catch (IOException e) {
            System.err.println("The system could not find the file specified");
            e.printStackTrace();
        } finally { // Stream closure should be executed at any case
            try {
                if (br != null) br.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        KB.setTValue(clauses);
    }

}