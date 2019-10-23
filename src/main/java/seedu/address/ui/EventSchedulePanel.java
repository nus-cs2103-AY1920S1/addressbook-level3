package seedu.address.ui;

import java.util.Locale;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import seedu.address.commons.core.LogsCenter;

/**
 * EventSchedulePanel shows a timetable of all events saved.
 */
public class EventSchedulePanel extends UiPart<Region> {
    private static final Locale calendarLocale = Locale.UK;
    private static final String FXML = "EventSchedulePanel.fxml";
    private VCalendar vCalendar;
    private ICalendarAgenda agenda;
    private final Logger logger = LogsCenter.getLogger(EventSchedulePanel.class);

    @FXML
    private BorderPane eventScheduleBorderPane;

    public EventSchedulePanel(ObservableList<VEvent> vEventList) {
        super(FXML);
        this.vCalendar = new VCalendar();
        vCalendar.setVEvents(vEventList);
        this.agenda = new ICalendarAgenda(this.vCalendar);
        // set calendar to start week on monday
        this.agenda.setLocale(calendarLocale);
        setWeekSkin();
        eventScheduleBorderPane.setCenter(agenda);
    }

    private void setWeekSkin() {
        AgendaWeekSkin weekSkin = new AgendaWeekSkin(agenda);
        agenda.setSkin(weekSkin);
    }

    public void updateScheduler() {
        this.agenda.updateAppointments();
    }
}
