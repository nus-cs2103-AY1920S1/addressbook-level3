package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;

import seedu.address.MainApp;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.panel.calendar.CalendarPanel;
import seedu.address.ui.panel.calendar.TimelineDayView;
import seedu.address.ui.panel.log.LogPanel;

public class UiTest {

    private static boolean threadFlag = true;

    private static List<EventSource> list = new ArrayList<>();

    private static LocalDate date = LocalDate.parse("2020-07-21");
    private static Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();

    private static LocalDate date1 = LocalDate.parse("2019-11-18");
    private static Instant instant1 = date1.atStartOfDay(ZoneId.systemDefault()).toInstant();

    private static LocalDate date2 = LocalDate.parse("2020-02-27");
    private static Instant instant2 = date2.atStartOfDay(ZoneId.systemDefault()).toInstant();

    private static LocalDate date3 = LocalDate.parse("2001-01-01");
    private static Instant instant3 = date3.atStartOfDay(ZoneId.systemDefault()).toInstant();


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

    /******************* For LogBox *************************/
    @Test
    void createLogBoxTest() {
        try {
            LogPanel logPanel = new LogPanel();
            logPanel.createLogBox("Feedback to the user", "-primaryColor");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Platform.exit();
        Thread.currentThread().interrupt();
    }

    /******************* For CalendarPanel *************************/

    @Test
    void changeTimelineDateTest() {
        CalendarPanel calendarPanel = new CalendarPanel(new UiParser());
        try {
            calendarPanel.changeTimelineDate(instant);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Platform.exit();
        Thread.currentThread().interrupt();
    }

    @Test
    void changeCalendarScreenDate() {
        CalendarPanel calendarPanel = new CalendarPanel(new UiParser());
        try {
            calendarPanel.changeCalendarScreenDate(instant);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Platform.exit();
        Thread.currentThread().interrupt();
    }

    /******************* For TimelineDayView *************************/
    @Test
    void eventChangeTest() {
        try {
            list.add(new EventSource("Test 1", new DateTime(instant1)));
            list.add(new EventSource("Test 2", new DateTime(instant2)));
            list.add(new EventSource("Test 3", new DateTime(instant3)));
            TimelineDayView timelineDayView = new TimelineDayView(1, 1, 2001, new UiParser());
            timelineDayView.eventChange(list);
        } catch (Exception e) {
            fail();
        }
        Platform.exit();
        Thread.currentThread().interrupt();
    }
}
