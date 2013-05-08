/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.View;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import penpot.Model.pekanMhs;

/**
 *
 * @author haluan
 */
public class PieChart extends JFrame {

    private List<pekanMhs> recordPekanMhs = new ArrayList<>();
    public PieChart(List<pekanMhs> lp) {
        super("STATISTIK");
        recordPekanMhs=lp;
        final CategoryDataset dataset1 = createDataset(recordPekanMhs);
        final JFreeChart chart = createChart(dataset1, null);
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setPreferredSize(new java.awt.Dimension(600,470));
        setLocation(300, 100);
        setContentPane(chartpanel);        
    }
    

    private CategoryDataset createDataset(List<pekanMhs> lp) {
        DefaultCategoryDataset rs = new DefaultCategoryDataset();
        for(pekanMhs p : recordPekanMhs){
         rs.setValue(Integer.parseInt(p.getNilai()), ""+p.getNama(), "Pekan ke-"+p.getPekan());
        }
        return rs;
    }

    private JFreeChart createChart(CategoryDataset dataset1, String title) {

        JFreeChart chart = ChartFactory.createBarChart3D(title, title, title, dataset1, PlotOrientation.VERTICAL, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);


        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        //plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
