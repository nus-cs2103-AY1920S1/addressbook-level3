package seedu.ifridge.ui;

import static java.util.Objects.requireNonNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteReport;
import seedu.ifridge.model.waste.WasteStatistic;

/**
 * Displays the waste report window.
 */
public class ReportWasteWindow {

    public static final String WEIGHT_TITLE = "Wastage in Kilograms";
    public static final String WEIGHT_UNIT = "Kilograms";
    public static final String VOLUME_TITLE = "Wastage in Litres";
    public static final String VOLUME_UNIT = "Litres";
    public static final String QUANTITY_TITLE = "Wastage in Units";
    public static final String QUANTITY_UNIT = "Units";

    private WasteReport wasteReport;
    private Stage stage;

    public ReportWasteWindow(WasteReport wasteReport) {
        requireNonNull(wasteReport);
        this.wasteReport = wasteReport;
        stage = new Stage();
        stage.setTitle("Waste Report");
        stage.setWidth(700);
        stage.setHeight(400);
    }

    /**
     * Sets the stage and shows the report
     */
    public void showWasteReport() {
        TimeSeries historicalWeightData = new TimeSeries("Waste in kilograms");
        TimeSeries historicalVolumeData = new TimeSeries("Volume in litres");
        TimeSeries historicalQuantityData = new TimeSeries("Quantity in units");
        for (Map.Entry<WasteMonth, WasteStatistic> entry : wasteReport) {
            Month wasteMonthInJFree = entry.getKey().toJFreeMonth();
            WasteStatistic statistic = entry.getValue();
            float totalWeight = statistic.getTotalWeight();
            float totalVolume = statistic.getTotalVolume();
            float totalQuantity = statistic.getTotalQuantity();
            historicalWeightData.add(wasteMonthInJFree, totalWeight);
            historicalVolumeData.add(wasteMonthInJFree, totalVolume);
            historicalQuantityData.add(wasteMonthInJFree, totalQuantity);
        }


        ChartViewer weightViewer = getChartViewer(historicalWeightData, WEIGHT_TITLE, WEIGHT_UNIT);
        ChartViewer volumeViewer = getChartViewer(historicalVolumeData, VOLUME_TITLE, VOLUME_UNIT);
        ChartViewer quantityViewer = getChartViewer(historicalQuantityData, QUANTITY_TITLE, QUANTITY_UNIT);


        TabPane tabPane = new TabPane();
        Tab weightTab = new Tab("Weight");
        weightTab.setContent(weightViewer);
        tabPane.getTabs().add(weightTab);

        Tab volumeTab = new Tab("Volume");
        volumeTab.setContent(volumeViewer);
        tabPane.getTabs().add(volumeTab);

        Tab quantityTab = new Tab("Quantity");
        quantityTab.setContent(quantityViewer);
        tabPane.getTabs().add(quantityTab);

        stage.setScene(new Scene(tabPane));
        stage.show();
    }

    private ChartViewer getChartViewer(TimeSeries data, String title, String xAxisLabel) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(data);
        JFreeChart chart = createChart(dataset, title, xAxisLabel);
        ChartViewer viewer = new ChartViewer(chart);
        return viewer;
    }

    /**
     * Creates a JFreeChart using the given dataset.
     * @param dataset the dataset used to generate the chart
     * @param title the title of the chart
     * @param valueAxisLabel the y axis label.
     */
    private static JFreeChart createChart(XYDataset dataset, String title, String valueAxisLabel) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, valueAxisLabel, dataset);
        String fontName = "Roboto";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle("For more details on reducing food waste, "
                + "visit https://www.thinkeatsave.org/top-tips-on-reducing-food-waste/",
                new Font(fontName, Font.PLAIN, 14)));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(true);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f));
            renderer.setSeriesPaint(0, Color.BLACK);
            renderer.setSeriesPaint(1, new Color(24, 123, 58));
            renderer.setSeriesPaint(2, new Color(149, 201, 136));
            renderer.setSeriesPaint(3, new Color(1, 62, 29));
            renderer.setSeriesPaint(4, new Color(81, 176, 86));
            renderer.setSeriesPaint(5, new Color(0, 55, 122));
            renderer.setSeriesPaint(6, new Color(0, 92, 165));
        }
        plot.setBackgroundPaint(new Color(202, 229, 230));

        return chart;

    }

}
