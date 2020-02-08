package com.lytlogic.simulate;

import java.util.*;

public class GroupMeal implements Event {
    public List<Person> persons = new ArrayList<Person>();
    public int x;
    public int y;
    public int time;

    public GroupMeal(Group g, int t) {
        persons.addAll(g.activeMembers());
        x = g.cx;
        y = g.cy;
        time = t;
    }

    @Override
    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public void act() {
        int nInfected = 0;
        for (Person p : persons) {
            if (p.isInfected() && !p.isIsolated()) {
                nInfected++;
            }
        }

        double r = 1 - Math.pow(1 - Constant.GROUP_MEAT_INFECTED_RATE, nInfected);
        for (Person p : persons) {
            if (!p.carryVirus() && RandomPool.nextDouble() < r) {
                p.expose(time);
            }
        }
    }

    @Override
    public int getTime() {
        return time;
    }
}