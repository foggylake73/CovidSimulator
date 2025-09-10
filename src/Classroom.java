import java.awt.*;
import java.util.ArrayList;


public class Classroom {
    private static int blockNum = 8;
    ArrayList<Student>[] blocks = null;
    Teacher teacher = null;

    private int currentBlock = 0;
    Color healthy = Color.GREEN;
    Color infected = Color.YELLOW;
    Color incubation = Color.RED;
    Color atHome = Color.BLUE;
    Color dead = Color.BLACK;

    // Coordinates: top left corner - (left, top)
    private int top = 0;
    private int left = 0;
    private int width = 0;
    private int height = 0;
    
    public Classroom(int top, int left, int width, int height) {
        blocks = new ArrayList[blockNum];
        for (int i = 0; i < blockNum; i++) {
            blocks[i] = new ArrayList<Student>();
        }
        teacher = new Teacher();

        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    void assignStudent(int block, Student student) {
        this.blocks[block].add(student);
    }

    public int getTop() {
        return top;
    }
    public int getLeft() {
        return left;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void simulateBlockClassroom(int blockIndex, double infectionRateWeekly) {
        boolean hasInfected = false;
        // if (teacher.isInfected() || teacher.isIncubation()) {
        if (teacher.isContagious()) {
            hasInfected = true;
        } else {
            for (int student = 0; student < blocks[blockIndex].size(); student++) {
                // if (blocks[blockIndex].get(student).isInfected() || blocks[blockIndex].get(student).isIncubation()) {
                if (blocks[blockIndex].get(student).isContagious()) {
                    hasInfected = true;
                    break;
                }
            }
        }

        if (hasInfected) {
            teacher.updateInfection(infectionRateWeekly);
            for (int student = 0; student < blocks[blockIndex].size(); student++) {
                blocks[blockIndex].get(student).updateInfection(infectionRateWeekly);
            }
        }
    }

    void draw(Graphics2D g) {
        int size = 6;
        int space = 2;
        int blockSize = size + space;
        int nRow = width / (blockSize);
        int studentTop = top + space;
        int studentLeft = left + space;

        if (teacher.isHealthy()) {
            g.setColor(healthy);
        } else if (teacher.isInfected()) {
            g.setColor(infected);
        } else if (teacher.isIncubation()) {
            g.setColor(incubation);
        }
        g.fillRect(studentLeft, studentTop, size, size);

        for (int i = 0; i < blocks[currentBlock].size(); i++) {
            Student student = blocks[currentBlock].get(i);

            if (student.isHealthy()) {
                g.setColor(healthy);
            } else if (student.isInfected()) {
                g.setColor(infected);
            } else if (student.isIncubation()) {
                g.setColor(incubation);
            } else if (student.isAtHome()) {
                g.setColor(atHome);
            } else {
                g.setColor(dead);
            }
            g.fillRect(studentLeft + ((i + 1) % nRow) * blockSize, studentTop + ((i + 1) / nRow) * blockSize, size, size);
        }
    }
}
