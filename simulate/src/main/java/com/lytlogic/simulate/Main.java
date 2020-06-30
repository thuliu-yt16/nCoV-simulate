package com.lytlogic.simulate;

import javax.swing.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<World> worlds = new ArrayList<World>();
        for (int i = 0; i < Constant.WORLD_INSTANCES; i++) {
            worlds.add(new World());
        }
        MyPanel p = new MyPanel(worlds.get(0));
        Chart c = new Chart(worlds);

        JFrame frame = new JFrame();
        frame.setLayout(null);
        p.setBounds(0, 0, Constant.FRAME_WIDTH + Constant.INFO_WIDTH, Constant.FRAME_HEIGHT);
        c.panel.setBounds(Constant.FRAME_WIDTH + Constant.INFO_WIDTH, Constant.CHART_Y, Constant.CHART_WIDTH,
                Constant.CHART_HEIGHT);

        frame.add(c.panel);
        frame.add(p);
        frame.setSize(Constant.FRAME_WIDTH + Constant.INFO_WIDTH + Constant.CHART_WIDTH, Constant.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DisplayThread dt = new DisplayThread(worlds, p, c);

        Thread thread = new Thread(dt);
        thread.run();
    }
}
