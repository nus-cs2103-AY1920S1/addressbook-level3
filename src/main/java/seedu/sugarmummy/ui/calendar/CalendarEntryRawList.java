package seedu.sugarmummy.ui.calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.ui.UiPart;

public class CalendarEntryRawList extends UiPart<Region> {
    private static final String FXML = "CalendarEntryRawList.fxml";
    @FXML
    ListView<CalendarEntry> calendarEntryListView;

    public CalendarEntryRawList(ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        calendarEntryListView.setItems(calendarEntries);
        calendarEntryListView.setCellFactory(listView -> new CalendarListViewCell());
        calendarEntryListView.prefHeightProperty().bind(Bindings.size(calendarEntries).multiply(54));
        calendarEntryListView.scrollTo(calendarEntryListView.getItems().size() - 1);
        calendarEntries.addListener(new ListChangeListener<CalendarEntry>() {
            @Override
            public void onChanged(Change<? extends CalendarEntry> c) {
                calendarEntryListView.scrollTo(calendarEntryListView.getItems().size() - 1);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEntry} using a
     * {@code CalendarEntryRawCard}.
     */
    class CalendarListViewCell extends ListCell<CalendarEntry> {
        @Override
        protected void updateItem(CalendarEntry calendarEntry, boolean empty) {
            super.updateItem(calendarEntry, empty);

            if (empty || calendarEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarEntryRawCard(calendarEntry, getIndex() + 1).getRoot());
            }
        }
    }

}
