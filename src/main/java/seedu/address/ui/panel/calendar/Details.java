package seedu.address.ui.panel.calendar;

import javafx.scene.layout.Region;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays the feedback to the user.
 */
public class Details extends UiPart<Region> {

    private static final String FXML = "Details.fxml";

    public Details(UiParser uiParser) {
        super(FXML);
    }
}
