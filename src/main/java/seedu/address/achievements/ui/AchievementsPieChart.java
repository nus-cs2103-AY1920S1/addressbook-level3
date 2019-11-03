package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information in a pie chart given {@code chartData}. See {@link PieChart}.
 */
public class AchievementsPieChart extends UiPart<Region> {

    private static final String FXML = "achievements/AchievementsPieChart.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label chartTitle;

    @FXML
    private PieChart pieChart;

    public AchievementsPieChart(String title) {
        super(FXML);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Singapore", 13),
                        new PieChart.Data("China", 25),
                        new PieChart.Data("United States", 10),
                        new PieChart.Data("Malaysia", 22),
                        new PieChart.Data("India", 30));
        this.pieChart.setData(pieChartData);
        this.pieChart.setLabelLineLength(10);
        this.chartTitle.setText(title);
    }
}
