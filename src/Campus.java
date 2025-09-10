import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Campus extends JPanel implements KeyListener {
    ArrayList<Classroom> classrooms = null;

    boolean pause = false;

    public Campus(ArrayList<Classroom> classrooms) {
        this.classrooms = classrooms;

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics base) {
        super.paintComponent(base);
        Graphics2D g = (Graphics2D) base;
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom classroom = classrooms.get(i);
            g.setColor(Color.BLACK);
            g.drawRect(classroom.getLeft(), classroom.getTop(), classroom.getWidth(), classroom.getHeight());
            classroom.draw(g);
        }
    }

    public boolean getPause() {
        return pause;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            pause = !pause;
        }
    }
    @Override
    public void keyTyped(KeyEvent event) {
    }
    @Override
    public void keyReleased(KeyEvent event) {
    }
}
