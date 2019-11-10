package seedu.moolah.ui.statistics;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

/**
 * A factory class to produce the Pie Charts for the Statistics Panel
 */
public class PieChartRegionFactory implements StatisticsRegionFactory {

    private List<String> names;

    private List<Double> percentages;

    private String title;

    public PieChartRegionFactory(List<String> names, List<Double> percentages, String title) {
        this.names = names;
        this.percentages = percentages;
        this.title = title;
    }

    @Override
    public PieChart createRegion() {

        ObservableList<PieChart.Data> details = FXCollections.observableArrayList();

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            double percentage = percentages.get(i);
            details.add(new PieChart.Data(name, percentage));
        }

        PieChart pieChart = new PieChart();
        pieChart.setData(details);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);


        pieChart.setStartAngle(90);
        pieChart.setClockwise(false);

        return pieChart;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
