package seedu.sugarmummy.ui.calendar;

import java.time.LocalDate;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.time.Today;
import seedu.sugarmummy.ui.UiPart;

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

    public CalendarMonthDayCard(LocalDate date, ObservableList<CalendarEntry> calendarEntries, Today today) {
        super(FXML);
        BooleanBinding booleanBinding = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return today.getDateProperty().get().equals(date);
            }
        }, today.getDateProperty());
        ObservableObjectValue<Color> colorProperty = Bindings.when(booleanBinding)
                .then(Color.BLACK).otherwise(Color.WHITE);
        this.date.setWrapText(true);
        this.date.textFillProperty().bind(colorProperty);
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
}
