import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Schedule implements Comparable<Schedule>{

    private List<Lesson> lessons ;  //A list for saving all the lessons when reading the xml files
    private Lesson[][][] genes; //Each position shows the exact time and day a "Lesson" will be taughed(slot)

    private int fitness;   //Integer that holds the fitness score of the chromosome   

     
    //For constructing a randomly created chromosome and for mutation
    public Lesson RandomLesson(List<Lesson> l) { 

        if (l.isEmpty()) {
            throw new IllegalArgumentException("The lessons list is empty.");
        }
        Random random = new Random();
        int r = random.nextInt(l.size()); 
        return l.get(r);
    }

    public Lesson findLesson(String key) {
        for (Lesson l : lessons) {
            if ((l.getLessonCode() + l.getSection()).equals(key)) {
                return l;
            }
        }
        throw new IllegalArgumentException("Lesson not found: " + key);
    }

    //Constructs a randomly created chromosome
    Schedule(){ 

        read(); //storing the lessons into the list
        this.genes = new Lesson[5][7][9];  //5 days, 7 hours, 9 sections(A1, A2,....)
                                            //genes[3][0][2] = Pempth prwth wra sto A3

        HashMap<String, Integer> remainingHours = new HashMap<>();
        for (Lesson l : lessons) {
            String key = l.getLessonCode() + l.getSection();
            remainingHours.put(key, l.getHours());
        }

        Random random = new Random();
        for (int z = 0; z < 9; z++) { //day
            for (int i = 0; i < 5; i++) { //time
                for (int j = 0; j < 7; j++) { //section
                    List<String> candidates = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : remainingHours.entrySet()) {
                        if (entry.getValue() > 0) {
                            candidates.add(entry.getKey());
                        }
                    }
                    if (!candidates.isEmpty()) {
                        String selectedKey = candidates.get(random.nextInt(candidates.size()));
                        Lesson lesson = findLesson(selectedKey);
                        this.genes[i][j][z] = lesson;
                        remainingHours.put(selectedKey, remainingHours.get(selectedKey) - 1);
                    }
                }
            }
        }
        this.calculateFitness();
    }
   
    //Constructs a copy of a chromosome
    Schedule(Lesson[][][] genes){

        read();     //storing the lessons into the list
    
        this.genes = new Lesson[5][7][9];
        for(int i = 0; i < 5; i++){
            for (int j=0; j <7; j++){
                for(int z =0; z < 9; z++)
            this.genes[i][j][z] = genes[i][j][z];
            }
        }
        this.calculateFitness();
    }

    //Calculates the fitness score of the chromosome as the number of constraints that are NOT violated
    //The maximum number of constraints that are NOT violated is 9
    void calculateFitness(){

        int fitnessScore = 0;
        HashMap <String, Integer> teachersDaily = new HashMap<>(); 
        HashMap <String, Integer> teachersWeekly = new HashMap<>(); 
        HashMap <String, Integer> lessonsHours = new HashMap<>();

        //gia na kalyfhtoyn oles oi wres kathe tmhmatos
        String hoursKey;
        for(Lesson l : lessons){;
            hoursKey = l.getLessonCode() + l.getSection(); 
            lessonsHours.put(hoursKey, 0);
        }
        
        List <String> teachers = new ArrayList<>();

        List <String> sections = new ArrayList<>();      
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0;     //constraints

        for(int i=0; i<5; i++){ 

            teachersDaily.clear();

            for(int j=0; j< 7; j++){ 
                
                sections.clear();
                teachers.clear();
                
                for(int z=0; z <9; z++ ){

                    //oxi kena anamesa
                    if(j != 6){
                        if(genes[i][j][z] == null){
                            c5 += genes[i][j+1][z] != null ? 1 : 0 ;
                        }
                    } 


                    if(genes[i][j][z] != null){

                        //max apaitoumenes ebdomadiaies wres
                        hoursKey = genes[i][j][z].getLessonCode() + genes[i][j][z].getSection();
                        lessonsHours.put(hoursKey, lessonsHours.get(hoursKey) + 1);
                        int assignedHours = lessonsHours.get(hoursKey);
                        int requiredHours = findLesson(hoursKey).getHours();
                        if (assignedHours != requiredHours) {
                            c7 ++; 
                        }

                        
                        //oxi enas kauhghths tautoxrona se diaforetika section
                        if(teachers.contains(genes[i][j][z].getTeacherCode())){
                            c3 ++;
                        }else{
                            teachers.add(genes[i][j][z].getTeacherCode());
                        }     

                         
                        //to poly ena mathhma ana wra se kathe tmhma
                        if(sections.contains(genes[i][j][z].getSection())){
                            c4++;
                        }else{
                            sections.add(genes[i][j][z].getSection());
                        }
                         
                                          
                        //daily hours kathe kathhghth
                        if(teachersDaily.containsKey(genes[i][j][z].getTeacherCode())){
                            teachersDaily.put(genes[i][j][z].getTeacherCode(), teachersDaily.get(genes[i][j][z].getTeacherCode()) + 1); //increase teacher's daily hours 
                            c1 += teachersDaily.get(genes[i][j][z].getTeacherCode()) > genes[i][j][z].getMaxDailyHours() ? 1 : 0;
                        }else{
                            teachersDaily.put(genes[i][j][z].getTeacherCode(), 1);
                        }                    

                        //weekly hours kathe kathghth
                        if(teachersWeekly.containsKey(genes[i][j][z].getTeacherCode())){
                            teachersWeekly.put(genes[i][j][z].getTeacherCode(), teachersWeekly.get(genes[i][j][z].getTeacherCode()) + 1); //increase teacher's weekly hours
                            c2 += teachersWeekly.get(genes[i][j][z].getTeacherCode()) > genes[i][j][z].getMaxWeeklyHours() ? 1 : 0;
                        }else{
                            teachersWeekly.put(genes[i][j][z].getTeacherCode(), 1);
                        }


                        //oxi more than 2 synexomenes wres per teacher
                        if(j < 5){
                            if( genes[i][j+1][z]!= null &&  genes[i][j+2][z]!= null){
                                c6 += (genes[i][j][z].getTeacherCode() == genes[i][j+1][z].getTeacherCode() && genes[i][j][z].getTeacherCode() == genes[i][j+2][z].getTeacherCode()) ? 1 : 0;
                            }
                        }

                        //omoiomprfes wres tmhmamtos
                        
                        //omoiomorfes wres mathhmatos

                        //omoiomprfes wres kauhghtwn
                        

                    }
                    
                }
            }
        }
        if(c1==0){fitnessScore ++;}
        if(c2==0){fitnessScore ++;}
        if(c3==0){fitnessScore ++;}
        if(c4==0){fitnessScore ++;}
        if(c5==0){fitnessScore ++;}
        if(c6==0){fitnessScore ++;}
        if(c7==0){fitnessScore++;}
        
        this.fitness = fitnessScore;
    }

    //Mutate by randomly changing the slot(Lesson) of the Schedule
    void mutate(){

        Random random = new Random();
        int day = random.nextInt(5);
        int hour = random.nextInt(7);
        int section = random.nextInt(9);

        String key = genes[day][hour][section] != null 
        ? genes[day][hour][section].getLessonCode() + genes[day][hour][section].getSection() : null;

        //replace
        Lesson newLesson = RandomLesson(lessons);
        genes[day][hour][section] = newLesson;

        this.calculateFitness();
        int previousFitness = this.fitness;

        //an to fitness einai xeirotero gyrname pisw
        if (this.fitness < previousFitness) {
            if (key != null) {
                genes[day][hour][section] = findLesson(key);
            } else {
                genes[day][hour][section] = null; 
            }
            this.calculateFitness(); 
        }

    }

    //setters and getters
    public Lesson[][][] getGenes(){
        return this.genes;
    }

    public void setGenes(Lesson[][][] genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int compareTo(Schedule s)
    {
        return this.fitness - s.fitness;
    }

    //write html file
    public void write(){

        WriteSchedule sch = new WriteSchedule();
        sch.writeScheduleToHTML(this.genes, "school_schedule.html");
       
        System.out.print("Fitness : " + this.fitness);
    }

    //read xml files 
    public void read(){

        Reader r = new Reader();
        lessons= r.readXml("lessons.xml", "teachers.xml");
    }
}