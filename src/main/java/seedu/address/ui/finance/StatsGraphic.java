package seedu.address.ui.finance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.Region;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Panel showing statistical graphs.
 */
public class StatsGraphic extends UiPart<Region> {

    private static final String FXML = "StatsGraphic.fxml";

    @FXML
    private PieChart pieChart;

    public StatsGraphic(ObservableList<LogEntry> logEntries) {
        super(FXML);
        ObservableList<Data> result = FXCollections.observableArrayList();
        result.add(new PieChart.Data("A", 3));
        result.add(new PieChart.Data("B", 10));
        result.add(new PieChart.Data("C", 7));
        pieChart.setData(result);
        pieChart.setLabelsVisible(true);
    }
}
