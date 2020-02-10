package com.lytlogic.simulate;

public class Constant {
    public static int FRAME_WIDTH = 1000;
    public static int FRAME_HEIGHT = 1000;
    public static int INFO_WIDTH = 200;

    // 社区数
    public static int COMMUNITY_NUMBER = 30;
    public static double COMMUNITY_RANGE = 0.05;
    // 家庭数
    public static int GROUP_NUMBER = 1000;

    public static double GROUP_RANGE = 0.005;
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
    public static double INCUBATION = 3.0;
    // 最长时长
    public static double MAX_INCUBATION = 24.0;
    // 安全距离 传染性衰减
    public static double SAFE_DISTANCE = 200;

    // 从何时采取隔离政策 感染人数比例
    public static double ISOLATED_STRATEGY_START = 1;
    public static boolean ISOLATED_STRATEGY = false;
    // 隔离延迟
    public static double ISOLATED_DELAY = 1.5;
    // 家人是否隔离
    public static boolean FAMILY_ISOLATED = true;

    public static int COLOR_NORMAL = 0xdddddd;
    public static int COLOR_INFECTED = 0xdd0000;
    public static int COLOR_ISOLATED = 0x696969;
    public static int COLOR_EXPOSED = 0xffc125;
    public static int COLOR_BACKGROUND = 0x444444;

}