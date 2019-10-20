package seedu.address.calendar.ui;

import javafx.scene.layout.Region;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.ui.UiPart;

public class MonthPanel extends UiPart<Region> {
    private final static String FXML = "MonthPanel.fxml";

    public MonthPanel(MonthOfYear monthOfYear) {
        super(FXML);
    }
}
