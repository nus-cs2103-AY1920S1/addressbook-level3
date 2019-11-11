package seedu.guilttrip.ui.stats;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.ui.UiPart;

/**
 * Displays Statistics for Categories in a Table form.
 */
public class StatisticsTable extends UiPart<Region> {

    private static final String FXML = "statistics/StatisticsTable.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsTable.class);

    @FXML
    private TableView<CategoryStatistics> statsTable;

    @FXML
    private TableColumn<CategoryStatistics, String> categoryName;

    @FXML
    private TableColumn<CategoryStatistics, Double> amt;

    @FXML
    private Label totalLabel;

    public StatisticsTable(ObservableList<CategoryStatistics> statsMap, String type) {
        super(FXML);
        this.statsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        totalLabel.setText(type);
        if (type.equals("Income")) {
            totalLabel.setStyle("-fx-text-fill: MediumSeaGreen");
        }
        if (type.equals("Expense")) {
            totalLabel.setStyle("-fx-text-fill: LightSalmon");
        }

        statsMap.addListener(new ListChangeListener<CategoryStatistics>() {
            @Override
            public void onChanged(Change<? extends CategoryStatistics> change) {
                updateStatisticsTable(statsMap);
            }
        });
        updateStatisticsTable(statsMap);
    }

    /**
     * Updates the statistics table based the CategoryStatisticsList.
     * @param statsMap contains the list of statistics by category
     */
    public void updateStatisticsTable(ObservableList<CategoryStatistics> statsMap) {
        this.statsTable.setItems(statsMap);
        categoryName.setCellValueFactory(p -> p.getValue().getCategoryNameProperty());
        amt.setCellValueFactory(p -> p.getValue().getAmountCalculatedProperty().asObject());
    }
}
