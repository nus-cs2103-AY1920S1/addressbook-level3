package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.cards.ScheduleCard;

/**
 * Panel containing the calendar.
 */
public class CalendarPanel {

    // private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private VCalendar vCalendar;
    private ICalendarAgenda agenda;

    @FXML
    private ListView<Schedule> calendarView;

    public CalendarPanel(ObservableList<Schedule> scheduleList) {
        //super(FXML);
        //calendarView.setItems(scheduleList);
        //calendarView.setCellFactory(listView -> new CalendarPanel.CalendarListViewCell());
        vCalendar = new VCalendar();

        // to change all the schedules into VEvent
        // try dummy events

        agenda = new ICalendarAgenda(vCalendar);
    }

    public ICalendarAgenda getAgenda() {
        return agenda;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Schedule} using a {@code ScheduleCard}.
     */
    class CalendarListViewCell extends ListCell<Schedule> {
        @Override
        protected void updateItem(Schedule schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleCard(schedule, getIndex() + 1).getRoot());
            }
        }
    }

}
