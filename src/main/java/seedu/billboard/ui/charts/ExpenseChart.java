package seedu.billboard.ui.charts;

import javafx.scene.layout.Region;
import seedu.billboard.ui.UiPart;

/**
 * Represents a chart showing a certain statistic for expenses.
 */
public abstract class ExpenseChart<T> extends UiPart<Region> {

    public ExpenseChart(String fxmlFilePath) {
        super(fxmlFilePath);
    }

    public abstract void onDataChange(T newData);
}
