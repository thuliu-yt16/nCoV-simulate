package com.lytlogic.simulate;

import java.util.*;

public class World {

    public int day = 0;

    public List<Person> persons = new ArrayList<Person>();
    public List<Person> activePersons = new ArrayList<Person>();
    public List<Person> inactivePersons = new ArrayList<Person>();
    public List<Group> groups = new ArrayList<Group>();
    public List<Event> events = new ArrayList<Event>();

    public int nInfected = 0;
    public int nExposed = 0;
    public int nIsolated = 0;
    public boolean isolatedStrategy = Constant.ISOLATED_STRATEGY;

    public World() {
        initGroups();
        initRelations();
        initInfected();
    }

    public void initInfected() {
        int n = (int) (Constant.INIT_INFECTED_RATE * persons.size());
        for (int i = 0; i < n; i++) {
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

        double[] cxs = { 0.7, 0.34, 0.77, 0.16, 0.64, 0.78, 0.57, 0.23, 0.51, 0.37 };
        double[] cys = { 0.15, 0.45, 0.89, 0.38, 0.74, 0.9, 0.57, 0.12, 0.53, 0.29 };

        for (int c = 0; c < Constant.COMMUNITY_NUMBER; c++) {
            int gn = Constant.GROUP_NUMBER;
            // double cx = RandomPool.nextDouble();
            // double cy = RandomPool.nextDouble();
            double cx = cxs[c];
            double cy = cys[c];

            for (int i = 0; i < gn; i++) {
                double gx = cx + Constant.COMMUNITY_RANGE * RandomPool.nextGaussian();
                double gy = cy + Constant.COMMUNITY_RANGE * RandomPool.nextGaussian();

                int gx_int = (int) (gx * Constant.FRAME_WIDTH);
                int gy_int = (int) (gy * Constant.FRAME_HEIGHT);
                Group g = new Group(gx_int, gy_int);
                int pn = RandomPool.randomGroupMemberNumbers();

                for (int j = 0; j < pn; j++) {
                    int px = (int) ((gx + Constant.GROUP_RANGE * RandomPool.nextGaussian()) * Constant.FRAME_WIDTH);
                    int py = (int) ((gy + Constant.GROUP_RANGE * RandomPool.nextGaussian()) * Constant.FRAME_HEIGHT);
                    boolean unusual = RandomPool.nextDouble() < Constant.UNUSUAL_PERSON_RATE;
                    Person p = new Person(px, py, this, unusual);
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
        for (int i = 0; i < nHangout; i++) {
            List<Person> ps = new ArrayList();

            int nMember = RandomPool.randomHangoutMembers();
            Set<Integer> pis = RandomPool.randomN(nMember, activePersons.size());
            for (int pi : pis) {
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
        start();

        addEvents();
        triggerEvents();

        for (Person p : persons) {
            p.update(day);
        }

        end();
    }

    public void start() {
        day++;
        events.clear();
        activePersons.clear();
        inactivePersons.clear();

        for (Person p : persons) {
            if (p.isIsolated()) {
                inactivePersons.add(p);
            } else {
                activePersons.add(p);
            }
        }
        // System.out.println(activePersons.size());
    }

    public void end() {

        nExposed = 0;
        nInfected = 0;
        nIsolated = 0;

        for (Person person : persons) {
            switch (person.state) {
            case Normal:
                break;
            case Infected:
                nInfected++;
                break;
            case Exposed:
                nExposed++;
                break;
            }
            switch (person.actionState) {
            case Normal:
                break;
            case Isolated:
                nIsolated++;
                break;
            }
        }

        if (!isolatedStrategy && nInfected * 1.0 / persons.size() >= Constant.ISOLATED_STRATEGY_START) {
            isolatedStrategy = true;
        }
    }
}