package com.lytlogic.simulate;

import javax.swing.*;
import java.util.*;

public class Data {
    public static void main(String[] args) {
        simulate();
    }

    public static void simulate() {
        double security = 0.75;
        // Constant.NEIGHBOR_MEAT_INFECTED_RATE *= security;
        // Constant.GROUP_MEAT_INFECTED_RATE *= security;
        Constant.HANGOUT_INFECTED_RATE *= security;
        // for (int d = 1; d < 4; d++) {
        // double delay = d * 0.5;
        double delay = 1.0;
        Constant.ISOLATED_DELAY = delay;
        List<World> worlds = new ArrayList<World>();
        for (int i = 0; i < Constant.WORLD_INSTANCES; i++) {
            worlds.add(new World());
        }
        int maxDay = 100;

        Record record = new Record(worlds, maxDay);
        record.run();
        record.write("C:/something/nCoV-simulate/data/delay-" + delay + "-security-" + security + "-infalse.txt");
        // }
    }
}