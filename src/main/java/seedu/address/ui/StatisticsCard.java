package seedu.address.ui;

import static seedu.address.model.statistics.Statistics.BELOW_FIFTY;
import static seedu.address.model.statistics.Statistics.EIGHTY_AND_ABOVE;
import static seedu.address.model.statistics.Statistics.FIFTY_TO_FIFTY_NINE;
import static seedu.address.model.statistics.Statistics.SEVENTY_TO_SEVENTY_NINE;
import static seedu.address.model.statistics.Statistics.SIXTY_TO_SIXTY_NINE;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.Statistics;

/**
 * An UI component that displays information of {@code Statistics}.
 */
public class StatisticsCard extends UiPart<Region> {

    private static final String FXML = "StatisticsCard.fxml";

    private static final String PIE_CHART_TITLE = "Weighted Grade Distribution";
    private static final String LINE_CHART_TITLE = "Score Frequency Distribution";
    private static final String AXIS_X_LABEL = "Weighted Average Score";
    private static final String AXIS_Y_LABEL = "Num Of Students(s)";
    private static final String DATA_SERIES_LABEL = "Class Data Series";

    private Statistics stat;

    @FXML
    private HBox cardPane;
    @FXML
    private Label totalStudents;
    @FXML
    private Label mean;
    @FXML
    private Label median;
    @FXML
    private Label min;
    @FXML
    private Label max;
    @FXML
    private Label standardDev;
    @FXML
    private PieChart distributionChart;
    @FXML
    private LineChart<Number, Number> lineChart;

    /**
     * Contains UI elements and data for the statistics report.
     * @param stat the Statistics object containing relevant processed information.
     */
    public StatisticsCard(ObservableList<Statistics> stat) {
        super(FXML);
        postStat(stat);
        populatePieChart();
        populateLineChart();
    }

    /**
     * Updates the UI elements with the processed statistics.
     * @param stat
     */
    public void postStat(ObservableList<Statistics> stat) {
        this.stat = stat.get(0);
        totalStudents.setText(String.valueOf(this.stat.getTotalStudents()));
        mean.setText((String.format("%.2f", this.stat.getMean())));
        median.setText((String.format("%.2f", this.stat.getMedian())));
        min.setText((String.format("%.2f", this.stat.getMin())));
        max.setText((String.format("%.2f", this.stat.getMax())));
        standardDev.setText((String.format("%.2f", this.stat.getStandardDev())));
    }

    /**
     * Sets the pie chart element with corresponding data.
     */
    public void populatePieChart() {
        HashMap<String, Integer> gradeGroupings = stat.getGradeGroupings();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(">= 80", gradeGroupings.get(EIGHTY_AND_ABOVE)),
                        new PieChart.Data("70 ~ 79", gradeGroupings.get(SEVENTY_TO_SEVENTY_NINE)),
                        new PieChart.Data("60 ~ 69", gradeGroupings.get(SIXTY_TO_SIXTY_NINE)),
                        new PieChart.Data("50 ~ 59", gradeGroupings.get(FIFTY_TO_FIFTY_NINE)),
                        new PieChart.Data("< 50", gradeGroupings.get(BELOW_FIFTY)));
        distributionChart.setData(pieChartData);
        distributionChart.setTitle(PIE_CHART_TITLE);
        distributionChart.setLegendSide(Side.LEFT);
        distributionChart.setLabelsVisible(false);
    }

    /**
     * Sets the line chart element with corresponding data.
     */
    public void populateLineChart() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        yAxis.setLabel(AXIS_Y_LABEL);
        xAxis.setLabel(AXIS_X_LABEL);
        xAxis.setForceZeroInRange(false);

        lineChart.setTitle(LINE_CHART_TITLE);

        XYChart.Series series = new XYChart.Series();
        series.setName(DATA_SERIES_LABEL);
        HashMap<Integer, Integer> frequencyDistribution = stat.getFrequencyDistribution();
        for (Map.Entry<Integer, Integer> entry : frequencyDistribution.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
    }

}
