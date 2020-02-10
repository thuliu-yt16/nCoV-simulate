package com.lytlogic.simulate;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPanel extends JPanel implements Runnable {

    public MyPanel() {
        super();
        this.setBackground(new Color(Constant.COLOR_BACKGROUND));
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
                g.setColor(new Color(Constant.COLOR_NORMAL));
                break;
            case Infected:
                nInfected++;
                continue;
            case Exposed:
                nExposed++;
                continue;
            }
            switch (person.actionState) {
            case Normal:
                break;
            case Isolated:
                nIsolated++;
                continue;
            }
            g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 2, 2);
        }

        for (Person person : persons) {
            if (person.x < 0 || person.x >= Constant.FRAME_WIDTH || person.y < 0 || person.y >= Constant.FRAME_HEIGHT) {
                continue;
            }
            switch (person.state) {
            case Normal:
                continue;
            case Infected:
                g.setColor(new Color(Constant.COLOR_INFECTED));
                break;
            case Exposed:
                g.setColor(new Color(Constant.COLOR_EXPOSED));
                break;
            }
            switch (person.actionState) {
            case Normal:
                break;
            case Isolated:
                g.setColor(new Color(Constant.COLOR_ISOLATED));
                break;
            }
            g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 3, 3);
        }

        g.setColor(new Color(Constant.COLOR_NORMAL));
        g.drawString("天数:      " + World.getInstance().day, 20, 20);
        g.drawString("全部:      " + World.getInstance().persons.size(), 20, 40);
        g.setColor(new Color(Constant.COLOR_INFECTED));
        g.drawString("感染人数: " + nInfected, 20, 60);
        g.setColor(new Color(Constant.COLOR_EXPOSED));
        g.drawString("潜伏人数:  " + nExposed, 20, 80);
        g.setColor(new Color(Constant.COLOR_ISOLATED));
        g.drawString("隔离人数: " + nIsolated, 20, 100);
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
