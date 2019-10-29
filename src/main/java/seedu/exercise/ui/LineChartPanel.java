package seedu.exercise.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.exercise.logic.commands.statistic.Statistic;

/**
 * An UI for line chart.
 */
public class LineChartPanel extends UiPart<Region> {

    private static final String FXML = "LineChartPanel.fxml";

    private Statistic statistic;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Double> lineChart;

    public LineChartPanel(Statistic statistic) {
        super(FXML);
        this.statistic = statistic;
        display();
    }

    /**
     * Set data for line chart to be displayed.
     */
    private void display() {
        String category = statistic.getCategory();
        String startDate = statistic.getStartDate().toString();
        String endDate = statistic.getEndDate().toString();
        ArrayList<String> dates = statistic.getProperties();
        ArrayList<Double> values = statistic.getValues();

        lineChart.setAnimated(false);
        lineChart.layout();

        xAxis.setLabel(ChartTextUtil.changeFirstLetterToUpperCase("date"));
        yAxis.setLabel(ChartTextUtil.lineChartLabelFormatter(category));

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        int size = dates.size();
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        lineChart.setLegendVisible(false);
        lineChart.setTitle(ChartTextUtil.lineAndBarChartTitleFormatter(category, startDate, endDate));
        lineChart.getData().add(series);
    }
}
