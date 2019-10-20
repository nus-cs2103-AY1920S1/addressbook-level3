package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class PolicyPopularityHolder extends UiPart<Region> {
    private static final String FXML = "PolicyPopularityHolder.fxml";

    @FXML
    private PieChart piechart;

    public PolicyPopularityHolder() {
        super(FXML);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Iphone 5S", 13),
            new PieChart.Data("Samsung Grand", 25),
            new PieChart.Data("MOTO G", 10),
            new PieChart.Data("Nokia Lumia", 22));
        piechart = new PieChart(pieChartData);
    }
}
