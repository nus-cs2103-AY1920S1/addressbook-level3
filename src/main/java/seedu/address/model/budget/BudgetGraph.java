package seedu.address.model.budget;

import java.awt.Color;
import java.awt.BasicStroke;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class BudgetGraph extends ApplicationFrame {

    public BudgetGraph( String applicationTitle, String chartTitle ) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "Category" ,
                "Score" ,
                createDataset() ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        final XYSeries claim = new XYSeries( "Claim" );
        claim.add( 1.0 , 1.0 );
        claim.add( 2.0 , 4.0 );
        claim.add( 3.0 , 3.0 );

        final XYSeries income = new XYSeries( "Income" );
        income.add( 1.0 , 4.0 );
        income.add( 2.0 , 5.0 );
        income.add( 3.0 , 6.0 );

        final XYSeries budget = new XYSeries( "Budget" );
        budget.add( 3.0 , 4.0 );
        budget.add( 4.0 , 5.0 );
        budget.add( 5.0 , 4.0 );

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( claim );
        dataset.addSeries( income );
        dataset.addSeries( budget );
        return dataset;
    }

    public static void main( String[ ] args ) {
        BudgetGraph chart = new BudgetGraph("Budget Statistics",
                "Statistics for past Month");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}