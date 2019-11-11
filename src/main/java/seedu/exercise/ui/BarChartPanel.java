package seedu.exercise.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.ui.util.ChartUtil;

/**
 * An UI for bar chart.
 */
public class BarChartPanel extends UiPart<Region> {
    private static final String FXML = "BarChartPanel.fxml";
    private static final String DEFAULT_EXERCISES = "Exercises";
    private Statistic statistic;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String, Integer> barChart;

    public BarChartPanel(Statistic statistic) {
        super(FXML);
        this.statistic = statistic;
        display();
    }

    /**
     * Set data for bar chart to be displayed.
     */
    private void display() {
        String category = statistic.getCategory();
        String startDate = statistic.getStartDate().toString();
        String endDate = statistic.getEndDate().toString();
        ArrayList<String> properties = statistic.getProperties();
        ArrayList<Integer> values = statistic.getValues();

        barChart.setAnimated(false);
        barChart.layout();

        xAxis.setLabel(DEFAULT_EXERCISES);
        yAxis.setLabel(ChartUtil.labelFormatter(statistic.getCategory()));

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        int size = properties.size();
        for (int i = 0; i < size; i++) {
            String property = ChartUtil.propertyFormatter(properties.get(i));
            series.getData().add(new XYChart.Data<>(property, values.get(i)));
        }

        barChart.setLegendVisible(false);
        barChart.setTitle(ChartUtil.lineAndBarChartTitleFormatter(category, startDate, endDate));
        barChart.getData().add(series);

        ChartUtil.installToolTipXyChart(series.getData());
    }
}
