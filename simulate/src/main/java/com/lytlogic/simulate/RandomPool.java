package com.lytlogic.simulate;

import java.util.Random;

public class RandomPool {
    private Random random = new Random();
    private static RandomPool instance = new RandomPool();

    public static double nextGaussian() {
        return instance.random.nextGaussian();
    }

    public static int nextInt(int bound) {
        return instance.random.nextInt(bound);
    }

    public static int nextInt() {
        return instance.random.nextInt();
    }

    public static double nextDouble() {
        return instance.random.nextDouble();
    }

    static double[] GROUP_MEMBERS = { 0, 0.1453, 0.389, 0.6576, 0.8332, 0.9335, 0.9755, 0.9898, 0.9954, 0.9977, 1 };

    public static int randomIncubation() {
        int d;
        do {
            d = (int) (Constant.INCUBATION
                    + (Constant.MAX_INCUBATION - Constant.INCUBATION) / 3 * RandomPool.nextGaussian());
        } while (d < 0);
        return d;
    }

    public static int randomGroupMemberNumbers() {
        double p = nextDouble();
        int n = 0;
        do {
            n++;
        } while (n < 10 && GROUP_MEMBERS[n] < p);
        return n;
    }
}