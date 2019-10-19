package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;
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
import seedu.address.model.waste.WasteMonth;
import seedu.address.model.waste.WasteReport;
import seedu.address.model.waste.WasteStatistic;

public class ReportWasteWindow {

    private WasteReport wasteReport;
    private Stage stage;

    public ReportWasteWindow(WasteReport wasteReport) {
        requireNonNull(wasteReport);
        this.wasteReport = wasteReport;
        stage = new Stage();
        stage.setTitle("Waste Report");
        stage.setWidth(700);
        stage.setHeight(390);
    }

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

        TimeSeriesCollection weightDataset = new TimeSeriesCollection();
        weightDataset.addSeries(historicalWeightData);
        JFreeChart weightChart = createChart(weightDataset, "Wastage in kilograms", "Kilograms");
        ChartViewer weightViewer = new ChartViewer(weightChart);

        TimeSeriesCollection volumeDataset = new TimeSeriesCollection();
        volumeDataset.addSeries(historicalVolumeData);
        JFreeChart volumeChart = createChart(volumeDataset, "Wastage in litres", "Litres");
        ChartViewer volumeViewer = new ChartViewer(volumeChart);

        TimeSeriesCollection quantityDataset = new TimeSeriesCollection();
        quantityDataset.addSeries(historicalQuantityData);
        JFreeChart quantityChart = createChart(quantityDataset, "Wastage in units", "Units");
        ChartViewer quantityViewer = new ChartViewer(quantityChart);

        stage.setScene(new Scene(weightViewer));
        stage.show();



        showWeightGraph();
        showVolumeGraph();
        showQuantityGraph();
    }

    private void showQuantityGraph() {
        TimeSeries historicalData = new TimeSeries("Waste in kg");

    }

    private void showVolumeGraph() {
    }

    private void showWeightGraph() {
    }

    private static JFreeChart createChart(XYDataset dataset, String title, String valueAxisLabel) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title,               // title
                null,   // x-axis label
                valueAxisLabel,      // y-axis label
                dataset);

        String fontName = "Helvetica";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle("Source: http://www.ico.org/historical/2010-19/PDF/HIST-PRICES.pdf",
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
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f));
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, new Color(24, 123, 58));
            renderer.setSeriesPaint(2, new Color(149, 201, 136));
            renderer.setSeriesPaint(3, new Color(1, 62, 29));
            renderer.setSeriesPaint(4, new Color(81, 176, 86));
            renderer.setSeriesPaint(5, new Color(0, 55, 122));
            renderer.setSeriesPaint(6, new Color(0, 92, 165));
        }

        return chart;

    }

}
