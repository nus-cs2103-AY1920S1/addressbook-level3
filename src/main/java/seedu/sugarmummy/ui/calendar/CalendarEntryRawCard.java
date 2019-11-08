package seedu.sugarmummy.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.ui.UiPart;

public class CalendarEntryRawCard extends UiPart<Region> {
    private static final String FXML = "CalendarEntryRawCard.fxml";
    @FXML
    HBox rawCard;
    @FXML
    Label index;
    @FXML
    Label type;
    @FXML
    Label description;

    public CalendarEntryRawCard(CalendarEntry calendarEntry, int index) {
        super(FXML);
        this.index.setText(String.valueOf(index));
        type.setText(calendarEntry.getClass().getSimpleName());
        type.setStyle("-fx-background-radius: 0.2em; -fx-background-color: #818A9E; "
                + "-fx-text-fill: white; -fx-padding: 2");
        description.setText(calendarEntry.toString());
    }
}
