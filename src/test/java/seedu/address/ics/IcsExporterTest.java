package seedu.address.ics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

//@@author marcusteh1238
public class IcsExporterTest {

    @Test
    public void boilerplateCorrectness_emptyEventAndTaskListGenerateIcsFileContent_valueAsExpected() {
        ArrayList<EventSource> eventList = new ArrayList<>();
        ArrayList<TaskSource> taskList = new ArrayList<>();
        String expected = "BEGIN:VCALENDAR\n"
                + "VERSION:2.0\n"
                + "PRODID:-//Horo//Exported Calendar// v1.0//EN\n"
                + "END:VCALENDAR";
        Pattern pattern = Pattern.compile("BEGIN:VEVENT(.*?)END:VEVENT", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(expected);
        String outcome = new IcsExporter(new ModelManager()).generateIcsFileContent(eventList, taskList);
        assertEquals(expected, outcome);
    }

    @Test
    public void checkEventsHaveBeenParsed_nonEmptyEventAndEmptyTaskListGenerateIcsFileContent_valueAsExpected() {
        ArrayList<EventSource> eventList = new ArrayList<>();
        EventSource eventSource = EventSource
                .newBuilder("Test Description", DateTime.now())
                .build();
        eventList.add(eventSource);
        ArrayList<TaskSource> taskList = new ArrayList<>();
        String outcome = new IcsExporter(new ModelManager()).generateIcsFileContent(eventList, taskList);
        assertTrue(outcome.startsWith("BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//Horo//Exported Calendar// v1.0//EN\n"));
        assertTrue(outcome.endsWith("END:VCALENDAR"));

        Pattern eventPattern = Pattern.compile("BEGIN:VEVENT(.*?)END:VEVENT", Pattern.DOTALL);
        Matcher eventMatcher = eventPattern.matcher(outcome);
        int noOfEvents = 0;
        while (eventMatcher.find()) {
            noOfEvents++;
        }
        assertEquals(1, noOfEvents);

        Pattern taskPattern = Pattern.compile("BEGIN:VTODO(.*?)END:VTODO", Pattern.DOTALL);
        Matcher taskMatcher = taskPattern.matcher(outcome);
        int noOfTasks = 0;
        while (taskMatcher.find()) {
            noOfTasks++;
        }
        assertEquals(0, noOfTasks);

    }

    @Test
    public void checkTaskHaveBeenParsed_emptyEventAndNonEmptyTaskListGenerateIcsFileContent_valueAsExpected() {
        ArrayList<EventSource> eventList = new ArrayList<>();
        ArrayList<TaskSource> taskList = new ArrayList<>();
        TaskSource taskSource = TaskSource.newBuilder("Test Description").build();
        taskList.add(taskSource);
        String outcome = new IcsExporter(new ModelManager()).generateIcsFileContent(eventList, taskList);

        assertTrue(outcome.startsWith("BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//Horo//Exported Calendar// v1.0//EN\n"));
        assertTrue(outcome.endsWith("END:VCALENDAR"));

        Pattern eventPattern = Pattern.compile("BEGIN:VEVENT(.*?)END:VEVENT", Pattern.DOTALL);
        Matcher eventMatcher = eventPattern.matcher(outcome);
        int noOfEvents = 0;
        while (eventMatcher.find()) {
            noOfEvents++;
        }
        assertEquals(0, noOfEvents);

        Pattern taskPattern = Pattern.compile("BEGIN:VTODO(.*?)END:VTODO", Pattern.DOTALL);
        Matcher taskMatcher = taskPattern.matcher(outcome);
        int noOfTasks = 0;
        while (taskMatcher.find()) {
            noOfTasks++;
        }
        assertEquals(1, noOfTasks);
    }

    @Test
    public void checkEventAndTaskHaveBeenParsed_nonEmptyEventAndTaskListGenerateIcsFileContent_valueAsExpected() {
        ArrayList<EventSource> eventList = new ArrayList<>();
        EventSource eventSource = EventSource
                .newBuilder("Test Description", DateTime.now())
                .build();
        eventList.add(eventSource);

        ArrayList<TaskSource> taskList = new ArrayList<>();
        TaskSource taskSource = TaskSource.newBuilder("Test Description").build();
        taskList.add(taskSource);

        String outcome = new IcsExporter(new ModelManager()).generateIcsFileContent(eventList, taskList);

        assertTrue(outcome.startsWith("BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//Horo//Exported Calendar// v1.0//EN\n"));
        assertTrue(outcome.endsWith("END:VCALENDAR"));

        Pattern eventPattern = Pattern.compile("BEGIN:VEVENT(.*?)END:VEVENT", Pattern.DOTALL);
        Matcher eventMatcher = eventPattern.matcher(outcome);
        int noOfEvents = 0;
        while (eventMatcher.find()) {
            noOfEvents++;
        }
        assertEquals(1, noOfEvents);

        Pattern taskPattern = Pattern.compile("BEGIN:VTODO(.*?)END:VTODO", Pattern.DOTALL);
        Matcher taskMatcher = taskPattern.matcher(outcome);
        int noOfTasks = 0;
        while (taskMatcher.find()) {
            noOfTasks++;
        }
        assertEquals(1, noOfTasks);
    }

    @Test
    public void getExportFileName_noArgumentsRequired_valueAsExpected() {
        String filename = IcsExporter.getExportFileName();
        assertNotEquals("", filename);
        assertTrue(filename.endsWith(".ics"));
        assertTrue(filename.startsWith("Horo_export_"));
    }
}
