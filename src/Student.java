public class Student extends Person {
    private int classroomAssignment;

    public Student() {

    }

    int getClassroomAssignment() {
        return classroomAssignment;
    }

    void setClassroomAssignment(int random) {
        this.classroomAssignment = random;
    }
}
