package seedu.sugarmummy.ui.statistics;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.ui.UiPart;

//@@author chen-xi-cx

/**
 * Represents a panel of an average graph and its legend.
 */
public class AverageGraphPane extends UiPart<Region> {
    private static final String FXML = "AverageGraphPane.fxml";

    private final AverageGraph averageGraph;

    private final LegendPane legendPane;

    private final Logger logger = LogsCenter.getLogger(AverageGraphPane.class);

    @FXML
    private ScrollPane lineChartScrollPane;

    @FXML
    private VBox lineChartVBox;

    public AverageGraphPane(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty averageType,
            SimpleStringProperty recordType) {
        super(FXML);

        this.averageGraph = new AverageGraph(averageMap, averageType, recordType);
        this.legendPane = new LegendPane(averageMap, recordType);

        lineChartVBox.getChildren().add(averageGraph.getAverageGraph());
        lineChartVBox.getChildren().add(legendPane.getRoot());
        lineChartScrollPane.setContent(lineChartVBox);
    }

}
