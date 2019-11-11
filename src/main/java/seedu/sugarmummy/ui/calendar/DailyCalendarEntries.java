package seedu.sugarmummy.ui.calendar;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays calendar entries on a day.
 */
public class DailyCalendarEntries extends UiPart<Region> {
    private static final String FXML = "DailyCalendarEntriesList.fxml";

    @FXML
    private Label day;
    @FXML
    private ListView<CalendarEntry> dailyCalendarEntryList;

    public DailyCalendarEntries(LocalDate date, ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        this.getRoot().setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: #818A90");
        day.setText(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + date.getDayOfMonth() + " "
                + date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        day.setWrapText(true);
        day.setStyle("-fx-background-radius: 0.2em; -fx-background-color: #818A9E; "
                + "-fx-text-fill: white; -fx-padding: 2");
        dailyCalendarEntryList.setItems(calendarEntries
                .filtered(calendarEntry -> calendarEntry.isOnDate(date)).sorted());
        dailyCalendarEntryList.setCellFactory(listView -> new CalendarListViewCell());
        dailyCalendarEntryList.prefHeightProperty().bind(Bindings.size(
                calendarEntries.filtered(calendarEntry -> calendarEntry.isOnDate(date)))
                .multiply(54));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEntry} using a {@code CalnedarEntryCard}.
     */
    class CalendarListViewCell extends ListCell<CalendarEntry> {
        @Override
        protected void updateItem(CalendarEntry calendarEntry, boolean empty) {
            super.updateItem(calendarEntry, empty);

            if (empty || calendarEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarEntryListCard(calendarEntry).getRoot());
            }
        }
    }
}
