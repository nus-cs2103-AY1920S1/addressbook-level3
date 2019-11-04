package seedu.address.ui;

import static seedu.address.model.statistics.Statistics.BELOW_FIFTY;
import static seedu.address.model.statistics.Statistics.EIGHTY_AND_ABOVE;
import static seedu.address.model.statistics.Statistics.FIFTY_TO_FIFTY_NINE;
import static seedu.address.model.statistics.Statistics.SEVENTY_TO_SEVENTY_NINE;
import static seedu.address.model.statistics.Statistics.SIXTY_TO_SIXTY_NINE;

import java.util.ArrayList;
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
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
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
    private StackPane q1Pane;
    @FXML
    private StackPane q2Pane;
    @FXML
    private StackPane q3Pane;
    @FXML
    private StackPane q4Pane;
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
    private Label firstQuartile;
    @FXML
    private Label secondQuartile;
    @FXML
    private Label thirdQuartile;
    @FXML
    private Label fourthQuartile;
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
        postQuartile();
    }

    /**
     * Updates the UI elements with the processed statistics.
     * @param statList
     */
    public void postStat(ObservableList<Statistics> statList) {
        stat = statList.get(0);
        totalStudents.setText(String.valueOf(stat.getTotalStudents()));
        mean.setText((String.format("%.2f", stat.getMean())));
        median.setText((String.format("%.2f", stat.getMedian())));
        min.setText((String.format("%.2f", stat.getMin())));
        max.setText((String.format("%.2f", stat.getMax())));
        standardDev.setText((String.format("%.2f", stat.getStandardDev())));
    }

    /**
     * Set the text of the quartile labels to the processed data.
     */
    public void postQuartile() {
        firstQuartile.setText(formatStudentLabel(stat.getFirstQuartile()));
        secondQuartile.setText(formatStudentLabel(stat.getSecondQuartile()));
        thirdQuartile.setText(formatStudentLabel(stat.getThirdQuartile()));
        fourthQuartile.setText(formatStudentLabel(stat.getFourthQuartile()));
        q1Pane.setStyle("-fx-background-color: #8EFF55; -fx-text-fill: white;");
        q2Pane.setStyle("-fx-background-color: #55FFFE; -fx-text-fill: white;");
        q3Pane.setStyle("-fx-background-color: #FFFC55; -fx-text-fill: white;");
        q4Pane.setStyle("-fx-background-color: #FF7955; -fx-text-fill: white;");
    }

    /**
     * Format the students in the specified quartile ranges into a String representation.
     */
    public String formatStudentLabel(ArrayList<Pair<String, Double>> quartiles) {
        StringBuilder sb = new StringBuilder();
        for (int i = quartiles.size() - 1; i >= 0; i--) {
            Pair<String, Double> student = quartiles.get(i);
            sb.append(student.getKey());
            sb.append(": ");
            sb.append(String.format("%.2f", student.getValue()));
            sb.append("\n");
        }
        return sb.toString().trim();
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
        distributionChart.setLegendSide(Side.RIGHT);
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
