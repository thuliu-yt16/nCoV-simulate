package com.lytlogic.simulate;

import java.util.*;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.xy.*;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.*;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.StandardChartTheme;
import java.awt.Font;

public class Chart {

    public JPanel panel = null;
    public XYSeries infected = new XYSeries("感染人数");
    public XYSeries exposed = new XYSeries("潜伏人数");
    public XYSeriesCollection lineDataset = new XYSeriesCollection();
    public List<World> worlds;

    public void initData() {
        lineDataset.addSeries(infected);
        lineDataset.addSeries(exposed);
    }

    public Chart(List<World> ws) {
        worlds = ws;
        initData();

        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋书", Font.PLAIN, 15));
        // 设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 10));
        // 设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 10));
        ChartFactory.setChartTheme(standardChartTheme);
        JFreeChart chart = ChartFactory.createXYLineChart("疫情模拟图", "时间", "人数", lineDataset, PlotOrientation.VERTICAL,
                true, false, false);
        XYPlot xyPlot = chart.getXYPlot();
        ValueAxis axis = xyPlot.getRangeAxis();
        axis.setRange(0, 0.1);
        xyPlot.setRangeAxis(axis);

        panel = new ChartPanel(chart);
    }

    public void update() {
        int time = worlds.get(0).day;

        double avgInf = 0;
        double avgExp = 0;
        for (World world : worlds) {
            int n = world.persons.size();
            int inf = world.nInfected;
            int exp = world.nExposed;
            double infectedP = inf * 1.0 / n;
            double exposedP = exp * 1.0 / n;
            avgInf += infectedP;
            avgExp += exposedP;
        }
        avgInf /= Constant.WORLD_INSTANCES;
        avgExp /= Constant.WORLD_INSTANCES;

        infected.add(time, avgInf);
        exposed.add(time, avgExp);
    }

}