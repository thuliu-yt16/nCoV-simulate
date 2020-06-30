package com.lytlogic.simulate;

import java.util.List;
import java.util.ArrayList;

public class Person {
    public int x;
    public int y;

    public Group group;
    public World world;
    public boolean unusual;

    public enum State {
        Normal, Exposed, Infected
    }

    public enum ActionState {
        Normal, Isolated
    }

    public State state = State.Normal;
    public ActionState actionState = ActionState.Normal;

    public int isolatedTime = -1;
    public int exposedTime = -1;
    public int incubation = -1;
    public int infectedTime = -1;
    public int isolatedDelay = -1;

    public Person(int x, int y, World w, boolean u) {
        this.x = x;
        this.y = y;
        this.world = w;
        this.unusual = u;
    }

    private void infect(int day) {
        infectedTime = day;
        isolatedDelay = RandomPool.randomIsolatedDelay();
        state = State.Infected;
    }

    public void expose(int day) {
        exposedTime = day;
        incubation = RandomPool.randomIncubation();
        state = State.Exposed;
    }

    public void update(int day) {
        if (state == State.Exposed && day >= (incubation + exposedTime)) {
            infect(day);
        }

        if (state == State.Infected && actionState == ActionState.Normal && day >= (infectedTime + isolatedDelay)
                && world.isolatedStrategy) {
            isolate(day);
            if (Constant.FAMILY_ISOLATED) {
                for (Person p : group.members) {
                    if (p != this) {
                        p.isolate(day);
                    }
                }
            }
        }
    }

    public void isolate(int day) {
        actionState = ActionState.Isolated;
        isolatedTime = day;
    }

    public boolean isIsolated() {
        return actionState == ActionState.Isolated;
    }

    public boolean isInfected() {
        return state == State.Infected;
    }

    public boolean carryVirus() {
        return state == State.Exposed || state == State.Infected;
    }

    public double distance(Person p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}