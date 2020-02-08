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

}