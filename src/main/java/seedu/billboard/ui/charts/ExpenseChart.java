package seedu.billboard.ui.charts;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.ui.UiPart;


/**
 * Represents a chart showing a certain statistic for expenses.
 */
public abstract class ExpenseChart extends UiPart<Region> {


    protected final Logger logger = LogsCenter.getLogger(getClass());
    protected ObservableList<? extends Expense> expenses;

    public ExpenseChart(String fxmlFilePath, ObservableList<? extends Expense> expenses) {
        super(fxmlFilePath);
        this.expenses = expenses;
    }
}
