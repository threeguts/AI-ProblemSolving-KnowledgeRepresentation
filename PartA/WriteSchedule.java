import java.io.FileWriter;
import java.io.IOException;

public class WriteSchedule {
    public void writeScheduleToHTML(Lesson[][][] schedule, String fileName) {
    
        // Write the schedule to an HTML file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("<!DOCTYPE html>\n");
            writer.append("<html lang=\"en\">\n");
            writer.append("<head><meta charset=\"UTF-8\"><title>School Schedule</title>");
            writer.append("<style>table {border-collapse: collapse; width: 100%;} td, th {border: 1px solid black; padding: 8px; text-align: center;} th {background-color:rgb(96, 94, 94);} .day {background-color:rgb(55, 146, 221);} .hour {background-color:rgba(163, 197, 202, 0.9);} .section {background-color:rgb(191, 119, 161);}</style></head>\n");
            writer.append("<body><h1>Σχολικό Πρόγραμμα Γυμνασίου</h1><table>\n");

            // Table Header
            writer.append("<tr><th>Μέρα</th><th>Ώρα</th>");
            for (int section = 0; section < schedule[0][0].length; section++) {
                if(section > 5){
                    writer.append("<th>Γ " + (section - 5) + "</th>");
                }else if(section > 2){
                    writer.append("<th>Β " + (section - 2) + "</th>");
                }else{
                    writer.append("<th>A " + (section + 1) + "</th>");
                }
            }
            writer.append("</tr>\n");

            // Table Body
            for (int day = 0; day < schedule.length; day++) {
                for (int hour = 0; hour < schedule[0].length; hour++) {
                    writer.append("<tr>");
                    if (hour == 0) {
                        switch (day) {
                            case 0:
                                writer.append("<td rowspan=\"" + schedule[0].length + "\" class=\"day\">Δευτέρα " + "</td>");
                                break;
                            case 1:
                                writer.append("<td rowspan=\"" + schedule[0].length + "\" class=\"day\">Τρίτη " + "</td>");
                                break;
                            case 2:
                                writer.append("<td rowspan=\"" + schedule[0].length + "\" class=\"day\">Τετάρτη " + "</td>");
                                break;
                            case 3:
                                writer.append("<td rowspan=\"" + schedule[0].length + "\" class=\"day\">Πέμπτη " + "</td>");
                                break;
                            default:
                                writer.append("<td rowspan=\"" + schedule[0].length + "\" class=\"day\">Παρασκευή " + "</td>");
                                break;
                        }
                    }
                    
                    switch (hour) {
                        case 0:
                            writer.append("<td class=\"hour\">08:00-08:45" + "</td>");
                            break;
                        case 1:
                            writer.append("<td class=\"hour\">08:45-09:30" + "</td>");   
                            break;
                        case 2:
                            writer.append("<td class=\"hour\">09:45-10:30" + "</td>");                            
                            break;
                        case 3:
                            writer.append("<td class=\"hour\">10:30-11:15" + "</td>");                            
                            break;
                        case 4:
                            writer.append("<td class=\"hour\">11:30-12:15" + "</td>");                            
                            break;
                        case 5:
                            writer.append("<td class=\"hour\">12:25-13:10" + "</td>");                            
                            break;
                        case 6:
                            writer.append("<td class=\"hour\">13:15-14:00" + "</td>");                            
                            break;
                    }
                    
                    String[] sectionMapping = {"Α1", "Α2", "Α3", "Β1", "Β2", "Β3", "Γ1", "Γ2", "Γ3"};

                    for (int slot = 0; slot < schedule[0][0].length; slot++) {
                        if (schedule[day][hour][slot] != null) {
                            Lesson lesson = schedule[day][hour][slot];  // Παίρνουμε το μάθημα για το συγκεκριμένο slot
                            String section = lesson.getSection();

                            // Ελέγχουμε αν το τμήμα του μαθήματος αντιστοιχεί στο slot
                            for (int sectionIndex = 0; sectionIndex < sectionMapping.length; sectionIndex++) {
                                if (section.equals(sectionMapping[sectionIndex])) {
                                    writer.append("<td class=\"section\">" + lesson.toStringSchedule() + "</td>");
                                    break;  // Διακόπτουμε το loop αν βρούμε το σωστό τμήμα
                                }
                            }
                        } else {
                            // Αν δεν υπάρχει μάθημα, προσθέτουμε "Κενό"
                            writer.append("<td class=\"section\">" + "Κενό" + "</td>");
                        }
                    }
                    writer.append("</tr>\n");
                }
            }

            writer.append("</table></body></html>");

            System.out.println("Schedule has been written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}