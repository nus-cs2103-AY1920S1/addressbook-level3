package seedu.address.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.calendar.Method;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
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
        String res = vCalendar.toString();
    }

    private void initCalendar(ICalendarAgenda agenda) {
        setLocale(agenda, UK_LOCALE);
        setWeekSkin(this.agenda);
        disableMouseClick(this.agenda);
    }

    private void setLocale(ICalendarAgenda agenda, Locale locale) {
        agenda.setLocale(locale);
    }

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

    private void setWeekSkin(ICalendarAgenda agenda) {
        AgendaWeekSkin weekSkin = new AgendaWeekSkin(agenda);
        agenda.setSkin(weekSkin);
    }

    public void updateScheduler() {
        this.agenda.updateAppointments();
    }
}
