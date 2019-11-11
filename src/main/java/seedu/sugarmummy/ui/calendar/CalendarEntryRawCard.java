package seedu.sugarmummy.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays information of a {@code CalendarEntry}.
 */
public class CalendarEntryRawCard extends UiPart<Region> {
    private static final String FXML = "CalendarEntryRawCard.fxml";
    @FXML
    private HBox rawCard;
    @FXML
    private Label index;
    @FXML
    private Label type;
    @FXML
    private Label description;

    public CalendarEntryRawCard(CalendarEntry calendarEntry, int index) {
        super(FXML);
        this.index.setText(String.valueOf(index));
        type.setText(calendarEntry.getClass().getSimpleName());
        type.setStyle("-fx-background-radius: 0.2em; -fx-background-color: #818A9E; "
                + "-fx-text-fill: white; -fx-padding: 2");
        description.setText(calendarEntry.toString());
    }
}
