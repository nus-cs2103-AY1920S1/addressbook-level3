package seedu.address.model.util;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

/**
 * A sample plotting utility for 2 variable graphs
 * TODO: integrate with MainWindow for graphical view mode
 */
public class SamplePlotDataUtil {
    private static double[] x = {2, 4, 6, 8};
    private static double[] y = {2, 5, 5, 8};

    /**
     * Plots the data on a graph in a new window
     */
    public static void plotData() {
        Plot2DPanel plot = new Plot2DPanel();
        plot.addScatterPlot("X-Y", x, y);
        JFrame frame = new JFrame("Original X-Y Data");
        frame.setContentPane(plot);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public static void main (String[] args) {
        plotData();
    }
}
