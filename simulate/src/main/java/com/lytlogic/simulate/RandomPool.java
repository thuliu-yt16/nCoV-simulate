package com.lytlogic.simulate;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class RandomPool {
    private static Random random = new Random();
    public static Random randomSeed;
    public static boolean usingSeed = false;

    public static double nextGaussian() {
        Random r = usingSeed ? randomSeed : random;
        return r.nextGaussian();
    }

    public static int nextInt(int bound) {
        Random r = usingSeed ? randomSeed : random;
        return r.nextInt(bound);
    }

    public static double nextDouble() {
        Random r = usingSeed ? randomSeed : random;
        return r.nextDouble();
    }

    static double[] GROUP_MEMBERS = { 0, 0.1453, 0.389, 0.6576, 0.8332, 0.9335, 0.9755, 0.9898, 0.9954, 0.9977, 1 };

    public static int randomIncubation() {
        int d;
        do {
            d = (int) (Constant.INCUBATION + (Constant.MAX_INCUBATION - Constant.INCUBATION) / 3 * nextGaussian());
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

    public static int randomHangoutMembers() {
        int nMember;
        do {
            nMember = (int) (nextGaussian() * 3 + Constant.HANGOUT_MEMBERS);
        } while (nMember <= 1);
        return nMember;
    }

    public static Set<Integer> randomN(int n, int bound) {
        Set<Integer> members = new HashSet();
        for (int j = 0; j < n; j++) {
            int pi;
            do {
                pi = nextInt(bound);
            } while (members.contains(pi));
            members.add(pi);
        }
        return members;
    }

    public static int randomIsolatedDelay() {
        int isolatedDelay;
        do {
            isolatedDelay = (int) (Constant.ISOLATED_DELAY + nextGaussian());
        } while (isolatedDelay < 0);
        return isolatedDelay;
    }
}