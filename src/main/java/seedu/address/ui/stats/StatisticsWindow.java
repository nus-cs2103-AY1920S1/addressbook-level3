package seedu.address.ui.stats;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.ui.UiPart;

/**
 * Displays the Statistics of the user's activities in Table form.
 */
public class StatisticsWindow extends UiPart<Region> {

    private static final String FXML = "/statistics/Statistics.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StatisticsTable expenseStats;
    private StatisticsTable incomeStats;

    @FXML
    private GridPane gridPanePlaceHolder;

    public StatisticsWindow(Logic logic) {
        super(FXML);

        // Set dependencies
        this.logic = logic;
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {
        this.expenseStats = new StatisticsTable(logic.getListOfStatsForExpense());
        this.incomeStats = new StatisticsTable(logic.getListOfStatsForIncome());
        GridPane.setRowIndex(expenseStats.getRoot(), 0);
        GridPane.setColumnIndex(expenseStats.getRoot(), 0);
        GridPane.setRowIndex(incomeStats.getRoot(), 0);
        GridPane.setColumnIndex(incomeStats.getRoot(), 1);
        gridPanePlaceHolder.getChildren().addAll(expenseStats.getRoot(), incomeStats.getRoot());
    }


}
