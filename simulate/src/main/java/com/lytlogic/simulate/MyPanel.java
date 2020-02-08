package com.lytlogic.simulate;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPanel extends JPanel implements Runnable {

    public MyPanel() {
        super();
        this.setBackground(new Color(0x444444));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        List<Person> persons = World.getInstance().persons;
        int nInfected = 0;
        int nExposed = 0;
        int nIsolated = 0;
        for (Person person : persons) {
            if (person.x < 0 || person.x >= Constant.FRAME_WIDTH || person.y < 0 || person.y >= Constant.FRAME_HEIGHT) {
                continue;
            }
            switch (person.state) {
            case Normal:
                g.setColor(new Color(0xdddddd));
                break;
            case Infected:
                g.setColor(new Color(0xdd0000));
                nInfected++;
                break;
            case Exposed:
                g.setColor(new Color(0x00dddd));
                nExposed++;
                break;
            }
            switch (person.actionState) {
            case Normal:
                g.fillOval(person.x, person.y, 3, 3);
                break;
            case Isolated:
                g.fillRect(person.x, person.y, 3, 3);
                nIsolated++;
                break;
            }
        }

        g.setColor(new Color(0xffc125));
        g.drawString("Day:      " + World.getInstance().day, 20, 20);
        g.drawString("All:      " + World.getInstance().persons.size(), 20, 40);
        g.drawString("Infected: " + nInfected, 20, 60);
        g.drawString("Exposed:  " + nExposed, 20, 80);
        g.drawString("Isolated: " + nIsolated, 20, 100);
    }

    public static int worldTime = 0;

    public Timer timer = new Timer();

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (worldTime % Constant.SIMULATE_INTERVAL == 0) {
                World.getInstance().go();
            }
            worldTime++;
            MyPanel.this.repaint();
        }
    }

    @Override
    public void run() {
        timer.schedule(new MyTimerTask(), 0, Constant.DISPLAY_TIME);
    }
}
