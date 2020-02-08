package com.lytlogic.simulate;

import java.util.*;

public class World {

    private static World instance = new World();
    public int day = 0;

    public static World getInstance() {
        return instance;
    }

    public List<Person> persons = new ArrayList<Person>();
    public List<Person> activePersons = new ArrayList<Person>();
    public List<Person> inactivePersons = new ArrayList<Person>();

    public List<Group> groups = new ArrayList<Group>();

    public List<Event> events = new ArrayList<Event>();

    public World() {
        initGroups();
        initRelations();
        initInfected();
    }

    public void initInfected() {
        for (int i = 0; i < Constant.INIT_INFECTED_NUMBER; i++) {
            int pi;
            do {
                pi = RandomPool.nextInt(persons.size());
            } while (persons.get(pi).carryVirus());
            persons.get(pi).expose(day);
        }
    }

    public void initRelations() {
        int gn = groups.size();
        int nRelations = (int) (gn * Constant.NEIGHBOR_NUMBER / 2);
        for (int i = 0; i < nRelations; i++) {
            int g1 = RandomPool.nextInt(gn);
            int g2;
            do {
                g2 = RandomPool.nextInt(gn);
            } while (g1 == g2);
            Group group1 = groups.get(g1);
            Group group2 = groups.get(g2);
            group1.neighbors.add(group2);
            group2.neighbors.add(group1);
        }
    }

    public void initGroups() {

        double[] cx = { 0.7, 0.34, 0.77, 0.16, 0.64, 0.78, 0.57, 0.23, 0.51, 0.37 };
        double[] cy = { 0.15, 0.45, 0.89, 0.38, 0.74, 0.9, 0.57, 0.12, 0.53, 0.29 };

        for (int c = 0; c < Constant.COMMUNITY_NUMBER; c++) {
            int gn = Constant.GROUP_NUMBER;

            for (int i = 0; i < gn; i++) {
                int gx = (int) ((cx[c] + 0.05 * RandomPool.nextGaussian()) * Constant.FRAME_WIDTH);
                int gy = (int) ((cy[c] + 0.05 * RandomPool.nextGaussian()) * Constant.FRAME_HEIGHT);

                Group g = new Group(gx, gy);
                int pn = RandomPool.nextInt(4) + 2;

                for (int j = 0; j < pn; j++) {
                    int px = gx + (int) (10 * RandomPool.nextGaussian());
                    int py = gy + (int) (10 * RandomPool.nextGaussian());
                    Person p = new Person(px, py);
                    p.group = g;
                    g.members.add(p);
                    persons.add(p);
                }
                groups.add(g);
            }
        }
    }

    public void addEvents() {
        addNeighborMeal();
        addFamilyMeal();
        addHangout();
    }

    void addNeighborMeal() {
        for (Group g : groups) {
            if (RandomPool.nextDouble() < Constant.NEIGHBOR_MEAT / 2 && g.neighbors.size() > 0) {
                int i = RandomPool.nextInt(g.neighbors.size());
                Group nb = g.neighbors.get(i);
                events.add(new NeighborMeal(g, nb, day));
            }
        }
    }

    void addFamilyMeal() {
        for (Group g : groups) {
            events.add(new GroupMeal(g, day));
        }
    }

    void addHangout() {
        int nHangout = (int) (Constant.HANGOUT_RATE * activePersons.size() / Constant.HANGOUT_MEMBERS);
        System.out.println(nHangout);
        for (int i = 0; i < nHangout; i++) {
            Set<Integer> members = new HashSet();
            List<Person> ps = new ArrayList();
            int nMember;
            do {
                nMember = (int) (RandomPool.nextGaussian() * 3 + Constant.HANGOUT_MEMBERS);
            } while (nMember <= 1);
            for (int j = 0; j < nMember; j++) {
                int pi;
                do {
                    pi = RandomPool.nextInt(activePersons.size());
                } while (members.contains(pi));
                members.add(pi);
                ps.add(activePersons.get(pi));
            }
            events.add(new Hangout(ps, day));
        }
    }

    void triggerEvents() {
        for (Event e : events) {
            e.act();
        }
    }

    public void go() {
        day++;
        events.clear();
        activePersons.clear();
        inactivePersons.clear();
        Collections.shuffle(persons);
        for (Person p : persons) {
            if (p.isIsolated()) {
                inactivePersons.add(p);
            } else {
                activePersons.add(p);
            }
        }

        addEvents();
        triggerEvents();

        int nInfected = 0;
        for (Person p : persons) {
            p.update(day);
            if (p.isInfected()) {
                nInfected++;
            }
        }

        if (nInfected * 1.0 / persons.size() >= Constant.ISOLATED_STRATEGY_START) {
            Constant.ISOLATED_STRATEGY = true;
        }

    }
}