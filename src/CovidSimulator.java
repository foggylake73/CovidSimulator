import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class CovidSimulator {
    private static int schoolDays = 292;
    private static int studentNum = 2024;
    private static int blockNum = 8;
    private static boolean even = true;
    private static int[] noSchoolDays = {
            12, 15, 50, 71, 72, 94,
            95, 96, 120, 121, 122, 123,
            124, 127, 128, 129, 130, 131,
            148, 152, 155, 183, 194, 222,
            225, 226, 227, 228, 229, 281
    };
    // used 7-day case rate starting on 8/30/21 ending on 6/13/22, staggered
    // chance of a person getting covid on 1 day over a 7-day period
    private static double[] infectionRate = {
            261, 263, 303, 290, 246, 211, 200,
            163, 123, 108, 107, 104, 139, 117,
            190, 209, 293, 552, 1182, 1397, 1312,
            991, 700, 436, 290, 199, 126, 91.5,
            81.1, 69.7, 60.5, 60.5, 81.5, 87.2, 108,
            129, 198, 238, 267, 242, 242, 234
    };
    // used 7-day death rate starting on 8/30/21 ending on 6/13/22
    // chance of person dying from covid on 1 day over a 7-day period
    private static double[] deathRate = {
            1.75, 1.23, 2, 2.88, 3.33, 3.6, 3.09,
            3.74, 2.99, 2.66, 2.4, 2.31, 1.77, 1.61,
            1.03, 2.27, 2.14, 1.93, 1.39, 1.08, 1.24,
            2.16, 2.89, 11.4, 6.47, 5.3, 6.81, 3.35,
            3.05, 2.17, 1.29, 1.18, 1.92, 1.79, 1.36,
            0.7, 0.56, 0.39, 0.23, 0.48, 0.54, 0.16
    };
    private static ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
    private static Student[] students = new Student[studentNum];

    private static long sleepTime = 10;

    private static int width = 1080;
    private static int height = 880;
    private static int topStart = 10;
    private static int leftStart = 200;
    private static int roomWidth = 60;
    private static int roomHeight = 26;
    private static boolean[][] roomVisibleFloor1 = {
            {true, true, true, true, true, true, true, true},
            {true, true, false, false, true, true, true, true},
            {true, true, true, true, true, true, true, true},
            {false, false, true, true, true, true, true, true},
            {false, false, true, false, false, false, false, false},
            {false, false, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, true},
            {false, false, false, true, true, true, true, true},
            {true, true, false, false, true, false, true, true},
            {true, true, true, true, false, false, true, true}
    };
    private static boolean[][] roomVisibleFloor2 = {
            {false, false, true, true, true, true, true, true, true, false, false, false},
            {false, false, true, false, false, true, true, true, true, false, true, true},
            {false, false, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, false, true, true, true, true, true, true, false, false},
            {true, true, false, true, false, false, false, false, false, false, false, true},
            {true, false, false, true, false, false, false, false, false, false, false, true},
            {true, false, true, true, false, false, false, false, false, false, false, true},
            {false, false, true, true, false, false, false, false, false, false, false, true},
            {false, false, false, true, false, false, false, false, false, false, false, true},
            {false, false, false, true, false, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, true, true, true, true, false, true},
            {true, false, false, false, false, false, false, false, false, false, false, false},
            {true, false, false, true, false, false, false, false, false, false, false, false},
            {true, true, false, false, false, false, false, false, false, false, false, false},
            {true, true, false, false, false, false, false, false, false, false, false, false}
    };

    private static void createRooms() {
        // Floor1
        for (int r = 0; r < roomVisibleFloor1.length; r++) {
            for (int c = 0; c < roomVisibleFloor1[r].length; c++) {
                if (roomVisibleFloor1[r][c]) {
                    classrooms.add(new Classroom(topStart + r * roomHeight, leftStart + c * roomWidth, roomWidth, roomHeight));
                }
            }
        }
        // Floor2
        int topStart2 = topStart + roomHeight * roomVisibleFloor1.length + 60;
        int leftStart2 = leftStart;
        for (int r = 0; r < roomVisibleFloor2.length; r++) {
            for (int c = 0; c < roomVisibleFloor2[r].length; c++) {
                if (roomVisibleFloor2[r][c]) {
                    classrooms.add(new Classroom(topStart2 + r * roomHeight, leftStart2 + c * roomWidth, roomWidth, roomHeight));
                }
            }
        }
    }

    private static void initializeStudents() {
        for (int studentIndex = 0; studentIndex < studentNum; studentIndex++) {
            students[studentIndex] = new Student();
        }
    }

    private static void assignStudentsToClassrooms() {
        Random rand = new Random();
        for (int blockIndex = 0; blockIndex < blockNum; blockIndex++) {
            for (int studentIndex = 0; studentIndex < studentNum; studentIndex++) {
                students[studentIndex].setClassroomAssignment(rand.nextInt());
            }

            Arrays.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    if (o1.getClassroomAssignment() > o2.getClassroomAssignment())
                        return 1;
                    else if (o1.getClassroomAssignment() == o2.getClassroomAssignment())
                        return 0;
                    else
                        return -1;
                }
            });

            for (int studentIndex = 0; studentIndex < studentNum; studentIndex++) {
                classrooms.get(studentIndex % classrooms.size()).assignStudent(blockIndex, students[studentIndex]);
            }
        }
    }

    private static double getInfectionRate(int week) {
        return infectionRate[week] / 100000;
    }

    private static double getDeathRate(int week) {
        return deathRate[week] / 100000;
    }

    private static boolean noSchool(int day) {
        for (int noSchoolDaysIndex = 0; noSchoolDaysIndex < noSchoolDays.length; noSchoolDaysIndex++) {
            if (day == noSchoolDays[noSchoolDaysIndex]) {
                return true;
            }
        }
        return false;
    }

    private static void simulateBlock(int blockIndex, double infectionRateWeekly) {
        for (int classroomIndex = 0; classroomIndex < classrooms.size(); classroomIndex++) {
            classrooms.get(classroomIndex).simulateBlockClassroom(blockIndex, infectionRateWeekly);
        }
    }

    private static void simulateOutOfSchool(double infectionRateWeekly) {
        for (int studentIndex = 0; studentIndex < studentNum; studentIndex++) {
            students[studentIndex].updateInfection(infectionRateWeekly);
        }
        for (int teacherIndex = 0; teacherIndex < classrooms.size(); teacherIndex++) {
            classrooms.get(teacherIndex).teacher.updateInfection(infectionRateWeekly);
        }
    }

    private static String getTimeStr() {
        String pattern = "yyyy-MM-dd-HH-mm-ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        JFrame frame = new JFrame("School Covid Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setPreferredSize(new Dimension(CovidSimulator.width, CovidSimulator.height));
        createRooms();
        initializeStudents();
        assignStudentsToClassrooms();
        Campus campus = new Campus(classrooms);
        frame.setResizable(true);
        frame.add(campus);
        frame.pack();
        frame.setVisible(true);

        // open result file
        String outputFilename = "analyze/simulator-" + getTimeStr() + ".csv";
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFilename)));

        // number of weeks in a school year (starting on monday, ends on friday) * 7 - 2 = 292
        for (int day = 1; day <= schoolDays; day++) {
            double infectionRateWeekly = getInfectionRate((day - 1) / 7);
            double deathRateWeekly = getDeathRate((day - 1) / 7);

            simulateOutOfSchool(infectionRateWeekly);

            if (day % 7 != 6 && day % 7 != 0 && !noSchool(day)) {
                if (even) {
                    for (int blockIndex = 0; blockIndex < blockNum / 2; blockIndex++) {
                        // loop through the first half (0-3) blocks
                        simulateBlock(blockIndex, infectionRateWeekly);

                        Thread.sleep(CovidSimulator.sleepTime);
                    }
                } else {
                    for (int blockIndex = blockNum / 2; blockIndex < blockNum; blockIndex++) {
                        // loop through the second half (4-7) blocks
                        simulateBlock(blockIndex, infectionRateWeekly);

                        Thread.sleep(CovidSimulator.sleepTime);
                    }
                }

                even = !even;
            }

            campus.repaint();
            while (campus.getPause()) {
                Thread.sleep(1000);
            }

            pw.println(day + "," + Person.infectedNum + "," + Person.deadNum);
            Person.resetNum();

            for (int studentIndex = 0; studentIndex < studentNum; studentIndex++) {
                students[studentIndex].updateStatus(deathRateWeekly);
            }
            for (int teacherIndex = 0; teacherIndex < classrooms.size(); teacherIndex++) {
                classrooms.get(teacherIndex).teacher.updateStatus(deathRateWeekly);
            }
        }

        pw.close();
    }
}
