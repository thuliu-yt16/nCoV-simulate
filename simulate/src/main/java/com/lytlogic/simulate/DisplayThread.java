package com.lytlogic.simulate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

class DisplayThread implements Runnable {
    public MyPanel panel = null;
    public Chart chart = null;
    public List<World> worlds;

    public DisplayThread(List<World> ws, MyPanel p, Chart c) {
        worlds = ws;
        panel = p;
        chart = c;
    }

    public static int worldTime = 0;

    public Timer timer = new Timer();

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            for (World world : worlds) {
                world.go();
            }
            panel.repaint();
            chart.update();
            chart.panel.repaint();
            worldTime++;
        }
    }

    @Override
    public void run() {
        timer.schedule(new MyTimerTask(), 0, Constant.DISPLAY_TIME * Constant.SIMULATE_INTERVAL);
    }
}