package com.lytlogic.simulate;

import java.util.*;

public class NeighborMeal implements Event {

    public List<Person> persons = new ArrayList<Person>();
    public int x;
    public int y;
    public int time;

    public NeighborMeal(Group g1, Group g2, int t) {
        persons.addAll(g1.activeMembers());
        persons.addAll(g2.activeMembers());
        x = (g1.cx + g2.cx) / 2;
        y = (g1.cy + g2.cy) / 2;
        time = t;
    }

    @Override
    public void act() {
        int nInfected = 0;
        for (Person p : persons) {
            if (p.carryVirus()) {
                nInfected++;
            }
        }
        if (nInfected == 0)
            return;
        double r = 1 - Math.pow(1 - Constant.NEIGHBOR_MEAT_INFECTED_RATE, nInfected);
        for (Person p : persons) {
            if (!p.carryVirus() && RandomPool.nextDouble() < r) {
                p.expose(time);
            }
        }
    }

    @Override
    public Point getLocation() {
        Point p = new Point(x, y);
        return p;
    }

    @Override
    public int getTime() {
        return time;
    }
}