package AdminPortal;

public class Course {
    private String code;
    private String name;
    private int lectures;
    private int tutorials;
    private int labs;
    private String instructor;

    public Course(String code, String name, int lectures, int tutorials, int labs, String instructor) {
        this.code = code;
        this.name = name;
        this.lectures = lectures;
        this.tutorials = tutorials;
        this.labs = labs;
        this.instructor = instructor;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getLectures() {
        return lectures;
    }

    public int getTutorials() {
        return tutorials;
    }

    public int getLabs() {
        return labs;
    }

    public String getInstructor() {
        return instructor;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLectures(int lectures) {
        this.lectures = lectures;
    }

    public void setTutorials(int tutorials) {
        this.tutorials = tutorials;
    }

    public void setLabs(int labs) {
        this.labs = labs;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}