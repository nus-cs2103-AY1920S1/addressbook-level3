package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.VElement;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.scene.control.agenda.Agenda;
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
    //private VCalendar vCalendar;
    //private ICalendarAgenda agenda;
    private Agenda agenda;

    @FXML
    private ListView<Schedule> calendarView;

    public CalendarPanel(ObservableList<Schedule> scheduleList) {
        //super(FXML);
        //calendarView.setItems(scheduleList);
        //calendarView.setCellFactory(listView -> new CalendarPanel.CalendarListViewCell());
        ObservableList<VEvent> vEvents = FXCollections.observableArrayList();

        agenda = new Agenda();
        agenda.appointments().addAll(
                new Agenda.AppointmentImplLocal()
                        .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
                        .withEndLocalDateTime(LocalDate.now().atTime(6, 00))
                        .withSummary("Order 12345")
                        .withLocation("MRT")
                        .withDescription("It's time")
        );
        agenda.appointments().addAll(
                new Agenda.AppointmentImplLocal()
                        .withStartLocalDateTime(LocalDate.now().atTime(5, 00))
                        .withEndLocalDateTime(LocalDate.now().atTime(7, 00))
                        .withSummary("Order 54321")
                        .withLocation("MRT")
                        .withDescription("It's time")
        );
        /*
        vCalendar = new VCalendar();
        //try dummy events
        LinkedList<VEvent> events = new LinkedList<>();

        VEvent vEvent = new VEvent().withDateTimeStart(LocalDateTime.now().minusDays(1))
                .withDateTimeEnd(LocalDateTime.now().minusDays(1).plusHours(2))
                .withSummary("Order 12").withLocation("MRT");
        VEvent vEvent1 = new VEvent().withDateTimeStart(LocalDateTime.now().minusDays(1))
                .withDateTimeEnd(LocalDateTime.now().minusDays(1).plusHours(2))
                .withSummary("Order 14").withLocation("NUS");
        events.add(vEvent);
        events.add(vEvent1);
        vCalendar.setVEvents(events);
        agenda = new ICalendarAgenda(vCalendar);
        */
    }

    public Agenda getAgenda() {
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
