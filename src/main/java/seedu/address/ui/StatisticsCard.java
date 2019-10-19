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
        setPieChart();
        setLineChart();
    }

    public void setPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("A", 10),
                        new PieChart.Data("B", 20),
                        new PieChart.Data("C", 20),
                        new PieChart.Data("D", 20),
                        new PieChart.Data("Ungraded", 30));
        distributionChart.setData(pieChartData);
        distributionChart.setTitle("Weighted Grade Distribution");
        distributionChart.setLegendSide(Side.LEFT);
        distributionChart.setLabelsVisible(false);
    }

    public void setLineChart() {
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        xAxis.setLabel("Num Of Students(s)");
        yAxis.setLabel("Weighted Average Score");

        lineChart.setTitle("Score Frequency Distribution");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

//        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
    }

    public void updateData(ObservableList<Statistics> stat) {
        postStat(stat);
    }
}
