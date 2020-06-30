package com.lytlogic.simulate;

import javax.swing.*;

import com.lytlogic.simulate.Person.ActionState;
import com.lytlogic.simulate.Person.State;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPanel extends JPanel {

    public World world;

    public MyPanel(World w) {
        super();
        world = w;
        this.setBackground(new Color(Constant.COLOR_BACKGROUND));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        List<Person> persons = world.persons;
        for (Person person : persons) {
            if (person.x < 0 || person.x >= Constant.FRAME_WIDTH || person.y < 0 || person.y >= Constant.FRAME_HEIGHT) {
                continue;
            }
            if (person.state == State.Normal && person.actionState == ActionState.Normal) {
                g.setColor(new Color(Constant.COLOR_NORMAL));
                g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 2, 2);
            }
        }

        for (Person person : persons) {
            if (person.x < 0 || person.x >= Constant.FRAME_WIDTH || person.y < 0 || person.y >= Constant.FRAME_HEIGHT) {
                continue;
            }
            if (person.actionState == ActionState.Isolated) {
                g.setColor(new Color(Constant.COLOR_ISOLATED));
                g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 3, 3);
            } else if (person.state == State.Infected) {
                g.setColor(new Color(Constant.COLOR_INFECTED));
                g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 3, 3);
            } else if (person.state == State.Exposed) {
                g.setColor(new Color(Constant.COLOR_EXPOSED));
                g.fillOval(person.x + Constant.INFO_WIDTH, person.y, 3, 3);
            }
        }

        g.setColor(new Color(Constant.COLOR_NORMAL));
        g.drawString("天数:      " + world.day, 20, 20);
        g.drawString("全部:      " + world.persons.size(), 20, 40);
        g.setColor(new Color(Constant.COLOR_INFECTED));
        g.drawString("感染人数: " + world.nInfected, 20, 60);
        g.setColor(new Color(Constant.COLOR_EXPOSED));
        g.drawString("潜伏人数:  " + world.nExposed, 20, 80);
        g.setColor(new Color(Constant.COLOR_ISOLATED));
        g.drawString("隔离人数: " + world.nIsolated, 20, 100);
    }

}
