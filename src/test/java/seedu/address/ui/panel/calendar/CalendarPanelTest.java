package seedu.address.ui.panel.calendar;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;

import seedu.address.MainApp;
import seedu.address.ui.UiParser;

public class CalendarPanelTest {

    private LocalDate date = LocalDate.parse("2020-07-21");
    private Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();

    @BeforeAll
    static void setUpTest() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(MainApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Test
    void changeTimelineDateTest() {
        CalendarPanel calendarPanel = new CalendarPanel(new UiParser());
        try {
            calendarPanel.changeTimelineDate(instant);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void changeCalendarScreenDate() {
        CalendarPanel calendarPanel = new CalendarPanel(new UiParser());
        try {
            calendarPanel.changeCalendarScreenDate(instant);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
