package com.lytlogic.simulate;

import java.util.List;
import java.util.ArrayList;

public class Group {
    public List<Person> members = new ArrayList<Person>();
    public List<Group> neighbors = new ArrayList<Group>();

    public int cx;
    public int cy;

    public Group(int x, int y) {
        cx = x;
        cy = y;
    }

    public List<Person> activeMembers() {
        List<Person> ps = new ArrayList<Person>();
        for (Person p : members) {
            if (!p.isIsolated()) {
                ps.add(p);
            }
        }
        return ps;
    }
}