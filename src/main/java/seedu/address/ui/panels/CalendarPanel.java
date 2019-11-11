package seedu.address.ui.panels;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CalendarDate;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.UiPart;

/**
 * Panel containing the calendar.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    private Agenda agenda;
    private ObservableList<Schedule> scheduleList;
    private ObservableList<Order> orderList;
    private CalendarDate calendarDate;

    @FXML
    private VBox calendarBox;

    public CalendarPanel(ObservableList<Schedule> scheduleList, ObservableList<Order> orderList,
                         CalendarDate calendarDate) {
        super(FXML);

        this.scheduleList = scheduleList;
        this.orderList = orderList;
        this.calendarDate = calendarDate;

        agenda = new Agenda();

        // disallow any non-command line actions (mouse) to alter events
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
        agenda.setEditAppointmentCallback((appointment) -> null);

        calendarBox.getChildren().add(agenda);
        populateAgenda();
        setAgendaView(Calendar.getInstance());

        // set up listener
        this.scheduleList.addListener((ListChangeListener<Schedule>) change -> populateAgenda());
        this.calendarDate.getProperty().addListener((observableValue, calendar, t1) -> setAgendaView(t1));
    }

    /**
     * To populate the agenda with the schedules in the observable list
     */

    public void populateAgenda() {
        logger.info("Populating Calendar with schedules.");
        agenda.appointments().clear();

        for (Schedule schedule: scheduleList) {
            int orderIndex = 0;
            for (Order order : orderList) {
                Optional<Schedule> s = order.getSchedule();
                if (s.isPresent() && s.get().isSameAs(schedule)) {
                    // change to 1-based
                    orderIndex = orderList.indexOf(order) + 1;
                    break;
                }
            }
            // additional check to ensure only schedules that are valid (in orders) are shown.
            if (orderIndex != 0) {
                agenda.appointments().addAll(
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(calendarToLocalDateTime(schedule.getCalendar()))
                                .withEndLocalDateTime(calendarToLocalDateTime(schedule.getCalendar()).plusHours(1))
                                .withSummary(String.format("Order %d: %s", orderIndex, schedule.getVenue().toString()))
                );
            }
        }
    }

    /**
     * Helper method to convert the Calendar object into LocalDateTime object
     */
    private LocalDateTime calendarToLocalDateTime(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        // offset to 1-based
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return LocalDateTime.of(year, month, date, hour, minute);
    }

    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Switch the agenda view to the week containing the specified date.
     */
    private void setAgendaView(Calendar calendar) {
        logger.info("Date view on Calendar changed.");
        LocalDateTime localDateTime = calendarToLocalDateTime(calendar);
        agenda.setDisplayedLocalDateTime(localDateTime);
    }

}
