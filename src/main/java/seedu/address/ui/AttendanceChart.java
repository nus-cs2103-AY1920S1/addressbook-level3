package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * UI component that displays a piechart of the attendance percentage for the team on a
 * specific training date.
 */

public class AttendanceChart extends UiPart<Region> {

    private static final String FXML = "AttendanceChart.fxml";
    private int present;
    private int absent;

    @FXML
    private Label chartTitle;

    @FXML
    private PieChart pieChart;

    public AttendanceChart(int present, int absent) {
        super(FXML);
        this.present = present;
        this.absent = absent;
        initialiseChartData();
    }

    private void initialiseChartData() {
        setChartContent();
        setChartTitle();
    }

    private void setChartContent() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Absent: " + absent, absent),
                        new PieChart.Data("Present: " + present, present));
        pieChart.setData(pieChartData);
    }

    private void setChartTitle() {
        chartTitle.setText("Team Attendance: " + calculateAttendancePercentage());
    }

    private String calculateAttendancePercentage() {
        double percentage = ((double) present / (present + absent)) * 100;
        percentage = Math.round(percentage * 10) / 10.0;
        return percentage + "%";
    }
}
