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
    private static final Locale UK_LOCALE = Locale.UK;
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
        initCalendar(this.agenda);
        eventScheduleBorderPane.setCenter(agenda);
    }

    private void initCalendar(ICalendarAgenda agenda) {
        setLocale(agenda, UK_LOCALE);
        setWeekSkin(this.agenda);
        disableMouseClick(this.agenda);
    }

    /**
     * Method to set locale of ICalendarAgenda. Note that the locale decides the starting day of a week.
     * @param agenda ICalendarAgenda to be set
     * @param locale desired locale to be set
     */
    private void setLocale(ICalendarAgenda agenda, Locale locale) {
        agenda.setLocale(locale);
    }

    /**
     * Disables all mouse click related actions. Scrolling is still allowed
     * @param agenda ICalendarAgenda to be disabled
     */
    private void disableMouseClick(ICalendarAgenda agenda) {
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
        agenda.setActionCallback(null);
        agenda.setEditAppointmentCallback(null);
        agenda.setNewAppointmentCallback(null);
        agenda.setSelectedOneAppointmentCallback(null);
        agenda.setNewAppointmentDrawnCallback(null);
        agenda.setAppointmentChangedCallback(null);

    }

    /**
     * Sets the calendar to week format.
     * @param agenda ICalendarAgenda object to be set to week skin / view.
     */
    private void setWeekSkin(ICalendarAgenda agenda) {
        AgendaWeekSkin weekSkin = new AgendaWeekSkin(agenda);
        agenda.setSkin(weekSkin);
    }

    /**
     * Updates the scheduler to be reflected on UI
     */
    public void updateScheduler() {
        this.agenda.updateAppointments();
    }
}
