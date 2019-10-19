package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.Statistics;

import java.util.HashMap;
import java.util.Map;

/**
 * An UI component that displays information of {@code Statistics}.
 */
public class StatisticsCard extends UiPart<Region> {

    private static final String FXML = "StatisticsCard.fxml";

    public Statistics stat;

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

    public StatisticsCard(ObservableList<Statistics> stat) {
        super(FXML);
        postStat(stat);
    }

    public void postStat(ObservableList<Statistics> stat) {
        this.stat = stat.get(0);
        totalStudents.setText(String.valueOf(this.stat.getTotalStudents()));
        mean.setText((String.format("%.2f", this.stat.getMean())));
        median.setText((String.format("%.2f",this.stat.getMedian())));
        min.setText((String.format("%.2f", this.stat.getMin())));
        max.setText((String.format("%.2f", this.stat.getMax())));
        standardDev.setText((String.format("%.2f", this.stat.getStandardDev())));
        setPieChart();
        setLineChart();
    }

    public void setPieChart() {
        HashMap<String, Integer> gradeGroupings = stat.getGradeGroupings();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(">= 80", gradeGroupings.get("aboveEighty")),
                        new PieChart.Data("70 ~ 79", gradeGroupings.get("seventies")),
                        new PieChart.Data("60 ~ 69", gradeGroupings.get("sixties")),
                        new PieChart.Data("50 ~ 59", gradeGroupings.get("fifties")),
                        new PieChart.Data("< 50", gradeGroupings.get("belowFifty")));
        distributionChart.setData(pieChartData);
        distributionChart.setTitle("Weighted Grade Distribution");
        distributionChart.setLegendSide(Side.LEFT);
        distributionChart.setLabelsVisible(false);
    }

    public void setLineChart() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        yAxis.setLabel("Num Of Students(s)");
        xAxis.setLabel("Weighted Average Score");
        xAxis.setForceZeroInRange(false);

        lineChart.setTitle("Score Frequency Distribution");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data
        HashMap<Integer, Integer> frequencyDistribution = stat.getFrequencyDistribution();
        for (Map.Entry<Integer, Integer> entry : frequencyDistribution.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);
    }

}
