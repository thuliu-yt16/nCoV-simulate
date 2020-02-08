package com.lytlogic.simulate;

public class Constant {
    public static int FRAME_WIDTH = 1000;
    public static int FRAME_HEIGHT = 1000;

    // 社区数
    public static int COMMUNITY_NUMBER = 10;
    // 家庭数
    public static int GROUP_NUMBER = 100;
    // 一天帧数
    public static int SIMULATE_INTERVAL = 30;
    // 可视化间隔
    public static int DISPLAY_TIME = 1000 / 60;

    // 初始感染人群
    public static double INIT_INFECTED_NUMBER = 10;
    // 邻居聚餐频率
    public static double NEIGHBOR_MEAT = 1.0 / 5;
    // 平均邻居数
    public static double NEIGHBOR_NUMBER = 2.0;
    // 聚餐传染概率
    public static double NEIGHBOR_MEAT_INFECTED_RATE = 0.3;
    // 家庭内部传染概率
    public static double GROUP_MEAT_INFECTED_RATE = 0.5;

    // 外出频率
    public static double HANGOUT_RATE = 0.5;
    // 平均外出接触人数
    public static double HANGOUT_MEMBERS = 10;
    // 外出感染几率
    public static double HANGOUT_INFECTED_RATE = 0.05;
    // 潜伏期平均时长
    public static double INCUBATION = 7.0;

    public static double SAFE_DISTANCE = 100;

    // 从何时采取隔离政策
    public static double ISOLATED_STRATEGY_START = 0.05;
    public static boolean ISOLATED_STRATEGY = false;
    // 隔离延迟
    public static double ISOLATED_DELAY = 1.5;
    // 家人是否隔离
    public static boolean FAMILY_ISOLATED = true;
}