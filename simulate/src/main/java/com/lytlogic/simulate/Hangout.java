package com.lytlogic.simulate;

import java.util.*;

public class Hangout implements Event {
    public List<Person> persons = new ArrayList<Person>();
    public int x;
    public int y;
    public int time;

    public Hangout(List<Person> ps, int t) {
        for (Person p : ps) {
            if (!p.isIsolated())
                persons.add(p);
        }
        x = 0;
        y = 0;
        for (Person p : ps) {
            x += p.x;
            y += p.y;
        }

        x /= ps.size();
        y /= ps.size();
        time = t;
    }

    @Override
    public void act() {
        List<Boolean> willExpose = new ArrayList<>(persons.size());

        for (int i = 0; i < persons.size(); i++) {
            boolean w = false;
            Person thisp = persons.get(i);
            for (int j = 0; j < persons.size(); j++) {
                if (w) {
                    break;
                }
                if (j != i) {
                    Person thatp = persons.get(j);
                    if (thatp.carryVirus() && RandomPool.nextDouble() < (thatp.unusual ? 5 : 1)
                            * (Math.pow(2, 1 - thisp.distance(thatp) / Constant.SAFE_DISTANCE) - 1)
                            * Constant.HANGOUT_INFECTED_RATE) {
                        w = true;
                    }
                }
            }
            willExpose.add(w);
        }

        for (int i = 0; i < persons.size(); i++) {
            if (willExpose.get(i) && !persons.get(i).carryVirus()) {
                persons.get(i).expose(time);
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