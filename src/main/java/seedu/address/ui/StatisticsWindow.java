package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;


public class StatisticsWindow extends UiPart<Region> {

    private static final String FXML = "Statistics.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StatisticsTable expenseStats;
    private StatisticsTable incomeStats;

    @FXML
    private VBox expenseTablePlaceholder;

    @FXML
    private VBox incomeTablePlaceholder;

    public StatisticsWindow(Logic logic) {
        super(FXML);

        // Set dependencies
        this.logic = logic;
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.expenseStats = new StatisticsTable(logic.getListOfStatsForExpense());
        this.incomeStats = new StatisticsTable(logic.getListOfStatsForIncome());

        expenseTablePlaceholder.getChildren().add(this.expenseStats.getRoot());
        incomeTablePlaceholder.getChildren().add(this.incomeStats.getRoot());
    }


}
