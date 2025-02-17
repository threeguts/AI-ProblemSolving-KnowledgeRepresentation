import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Reader {

    public List <Lesson> readXml(String lessonpath,String teacherpath) {
        List<Lesson> lessons = new ArrayList<>();
        Map<String, List<Object>> lessoninfoMap = new HashMap<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //open files

            File lessonFile = new File(lessonpath);
            File teacherFile = new File(teacherpath);
            Document lessonDoc = builder.parse(lessonFile);
            Document teacherDoc = builder.parse(teacherFile);

            //create nodes

            NodeList lessonNodes = lessonDoc.getElementsByTagName("lesson");
            NodeList teacherNodes = teacherDoc.getElementsByTagName("teacher");
            
            
            //gets lesson info and saves in a map

            for(int m = 0; m < lessonNodes.getLength(); m++){   
                    Node lessonNode = lessonNodes.item(m);  
                        
                    if(lessonNode.getNodeType() == Node.ELEMENT_NODE){
                        Element lessonElement = (Element) lessonNode;
                        String lessonCode = lessonElement.getAttribute("code");
                        String lessonName = lessonElement.getAttribute("name");
                        int hours = Integer.parseInt(lessonElement.getAttribute("hours"));
                        lessoninfoMap.put(lessonCode, Arrays.asList(lessonName,hours));
                    }
            }
            
            //gets teacher info

            for (int j = 0; j < teacherNodes.getLength(); j++){
                Node teacherNode = teacherNodes.item(j);
                if (teacherNode.getNodeType() == Node.ELEMENT_NODE){
                    Element teacherElement = (Element) teacherNode;

                    String teacherCode = teacherElement.getAttribute("id");
                    String teacherName = teacherElement.getAttribute("name");
                    int maxDailyHours = Integer.parseInt(teacherElement.getAttribute("maxDailyHours"));
                    int maxWeeklyHours = Integer.parseInt(teacherElement.getAttribute("maxWeeklyHours"));

                    /*for each teacher, goes through every taught lesson. searches for the code in the infomap. if it matches, creates three lesson objects, one for each class 
                        section and puts them on the list*/
                    NodeList teachesNodes = teacherDoc.getElementsByTagName("teaches");
                    for (int k = 0; k < teachesNodes.getLength(); k++) {

                        
                        Element teachesElement = (Element) teachesNodes.item(k);
                        String lessonCode = teachesElement.getAttribute("lessonCode");
                        String grade = teachesElement.getAttribute("grade");
                        
                        if(lessoninfoMap.containsKey(lessonCode)){
                            for (int s = 1; s <= 3; s++) {
                                String section = grade +  String.valueOf(s);
                                Lesson lesson = new Lesson(lessonCode, teacherCode, (String) lessoninfoMap.get(lessonCode).get(0), (int) lessoninfoMap.get(lessonCode).get(1), grade, section , teacherName, maxDailyHours, maxWeeklyHours);
                                lessons.add(lesson);
                            }
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("XML file reading issue."+ e.getMessage()); 
            e.printStackTrace();
        }

        return lessons;
    }

}