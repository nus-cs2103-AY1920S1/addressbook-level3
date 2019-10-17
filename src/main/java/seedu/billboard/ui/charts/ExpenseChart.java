package seedu.billboard.ui.charts;

import javafx.scene.layout.Region;
import seedu.billboard.ui.UiPart;


public abstract class ExpenseChart<T> extends UiPart<Region> {

    public ExpenseChart(String fxmlFilePath) {
        super(fxmlFilePath);
    }

    public abstract void onDataChange(T newData);
}
