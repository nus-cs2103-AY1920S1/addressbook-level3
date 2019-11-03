package seedu.sugarmummy.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;

/**
 * An UI component that displays information of a date.
 */
public class CalendarMonthDayCard extends UiPart<Region> {
    private static final String FXML = "CalendarMonthDayCard.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarMonthDayCard.class);

    @FXML
    private Label date;
    @FXML
    private Label entryNumber;
    @FXML
    private BorderPane dayCard;

    public CalendarMonthDayCard(LocalDate date, ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        this.date.setWrapText(true);
        this.date.setStyle("-fx-text-fill: white");
        this.date.setText(date.getDayOfMonth() + "");
        StringBinding stringBinding = Bindings.size(
                calendarEntries.filtered(calendarEntry -> calendarEntry.isOnDate(date))).asString();
        entryNumber.textProperty().bind(stringBinding);
        entryNumber.setStyle("-fx-text-fill: #FAF3DD");
    }

    public CalendarMonthDayCard() {
        super(FXML);
        this.date.setText("");
        this.entryNumber.setText("");
    }

    public CalendarMonthDayCard(LocalDate date) {
        super(FXML);
        this.date.setWrapText(true);
        this.date.setStyle("-fx-text-fill: white; -fx-font-size: 12");
        this.date.setText(date.getDayOfMonth() + "");
        this.dayCard.setBottom(null);
    }
}
