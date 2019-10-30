package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.CategoryStatistics;

/**
 * Panel containing the statisticsTable.
 */
public class StatisticsTable extends UiPart<Region> {

    private static final String FXML = "StatisticsTable.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsTable.class);

    @FXML
    private TableView<CategoryStatistics> statsTable;

    @FXML
    private TableColumn<CategoryStatistics, String> categoryName;

    @FXML
    private TableColumn<CategoryStatistics, Double> amtSpent;

    public StatisticsTable(ObservableList<CategoryStatistics> statsMap) {
        super(FXML);
        this.statsTable.setItems(statsMap);
        categoryName.setCellValueFactory(p -> p.getValue().getCategoryNameProperty());
        amtSpent.setCellValueFactory(p -> p.getValue().getAmountCalculatedProperty().asObject());
    }
}
