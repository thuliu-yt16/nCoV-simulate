package com.lytlogic.simulate;

import java.util.List;
import java.util.ArrayList;

public class Person {
    public int x;
    public int y;

    public Group group;

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

    public Person(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void infect(int day) {
        infectedTime = day;
        do {
            isolatedDelay = (int) (Constant.ISOLATED_DELAY + RandomPool.nextGaussian());
        } while (isolatedDelay < 0);
        state = State.Infected;
    }

    public void expose(int day) {
        exposedTime = day;
        do {
            incubation = (int) (Constant.INCUBATION + RandomPool.nextGaussian() * 2);
        } while (incubation < 1);
        state = State.Exposed;
    }

    public void update(int day) {
        if (state == State.Exposed && day >= (incubation + exposedTime)) {
            infect(day);
        } else if (state == State.Infected && actionState == ActionState.Normal && day >= (infectedTime + isolatedDelay)
                && Constant.ISOLATED_STRATEGY) {
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