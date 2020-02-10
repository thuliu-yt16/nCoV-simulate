package com.lytlogic.simulate;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MyPanel p = new MyPanel();
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(Constant.FRAME_WIDTH + Constant.INFO_WIDTH, Constant.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }
}
