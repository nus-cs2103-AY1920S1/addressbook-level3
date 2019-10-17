package seedu.billboard.ui.charts;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Region;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.Statistics;
import seedu.billboard.ui.UiPart;


public abstract class ExpenseChart extends UiPart<Region> {

    public ExpenseChart(String fxmlFilePath) {
        super(fxmlFilePath);
    }

    public abstract void onDataChange(Statistics stats, ListChangeListener.Change<? extends Expense> change);

}
