package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;
import seedu.address.model.statistics.Statistics;

import java.util.Comparator;

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

    public StatisticsCard(ObservableList<Statistics> stat) {
        super(FXML);
        this.stat = stat.get(0);
        totalStudents.setText(String.valueOf(this.stat.getTotalStudents()));
        mean.setText((String.format("%.2f", this.stat.getMean())));
        median.setText((String.format("%.2f",this.stat.getMedian())));
        setChart();
    }

    public void setChart() {
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

    public void updateData(ObservableList<Statistics> stat) {
        this.stat = stat.get(0);
        totalStudents.setText(String.valueOf(this.stat.getTotalStudents()));
        mean.setText((String.format("%.2f",this.stat.getMean())));
        median.setText((String.format("%.2f",this.stat.getMedian())));
        setChart();
    }
}
