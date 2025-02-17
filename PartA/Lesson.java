
public class Lesson {
    private String lessonCode;
    private String teacherCode;
    private String lessonName;
    private int hours;
    private String grade;
    private String section;
    private String teacherName;
    private int maxDailyHours;
    private int maxWeeklyHours;
    
    public Lesson(String c, String t, String n, int h, String g, String s, String tm, int maxD, int maxW){
        this.lessonCode = c;
        this.teacherCode = t;
        this.lessonName = n;
        this.hours = h;
        this.grade = g;
        this.section = s;
        this.teacherName = tm;
        this.maxDailyHours = maxD;
        this.maxWeeklyHours = maxW;
    }

    //setters, getters and toString() just for any case
    public String getLessonCode(){
        return this.lessonCode; 
    }

    public String getTeacherCode(){
        return this.teacherCode; 
    }

    public String getLessonName(){
        return this.lessonName;
    }

    public int getHours(){
        return this.hours;
    }

    public String getGrade(){
        return this.grade;
    }

    public String getSection(){
        return this.section;
    }

    public String getTeacherName(){
        return this.teacherName;
    }

    public int getMaxDailyHours(){
        return this.maxDailyHours;
    }

    public int getMaxWeeklyHours(){
        return this.maxWeeklyHours;
    }


    public void setLessonCode(String lc){
        this.lessonCode = lc; 
    }

    public void setTeacherCode(String tc){
        this.teacherCode = tc; 
    }

    public void setLessonName(String n){
        this.lessonName = n;
    }

    public void setHours(int h){
        this.hours = h;
    }

    public void setGrade(String g){
        this.grade = g;
    }

    public void setSection(String s){
        this.section = s;
    }

    public void setTeacherName(String tn){
        this.teacherName = tn; 
    }

    public void setMaxDailyHours(int dh){
        this.maxDailyHours = dh; 
    }

    public void setMaxWeeklyHours(int wh){
        this.maxWeeklyHours = wh; 
    }

    public String toString(){
        return "Lesson Name: " + this.lessonCode +
        "\nTeacher Name: " + this.teacherCode +
        "\nLesson Name: " + this.lessonName +
        "\nLesson Hours: " + this.hours +
        "\nGrade: " + this.grade +
        "\nSection: " + section +
        "\nTeacher Name: " + this.teacherName +
        "\nTeacher Max Daily Hours: " + this.maxDailyHours +
        "\nTeacher Max Weekly Hours: " + this.maxWeeklyHours;   
    }

    public String toStringSchedule(){
        //return this.lessonName + " " + this.teacherName + " " + this.getSection() ;
        return this.lessonName + "\n" + this.teacherName + "\n" + this.section;
    }

}