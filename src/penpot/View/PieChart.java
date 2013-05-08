/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.View;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
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
        PieDataset dataset = createDataset(recordPekanMhs);
        JFreeChart chart = createChart(dataset, "NILAI MINGGUAN");
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setPreferredSize(new java.awt.Dimension(600,470));
        setLocation(300, 100);
        setContentPane(chartpanel);        
    }
    

    private PieDataset createDataset(List<pekanMhs> lp) {
        DefaultPieDataset rs = new DefaultPieDataset();
        for(pekanMhs p : recordPekanMhs){
         rs.setValue("Pekan ke-"+p.getPekan()+" NILAI : "+Integer.parseInt(p.getNilai()), Integer.parseInt(p.getNilai()));          
        }
        return rs;
    }

    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
