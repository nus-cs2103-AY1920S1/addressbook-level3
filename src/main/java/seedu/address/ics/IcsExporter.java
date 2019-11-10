package seedu.address.ics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

//@@author marcusteh1238
/**
 * Class responsible for exporting Horo's tasks and events into an .ics file.
 */
public class IcsExporter {

    private static final String EXPORT_ERROR_MESSAGE = "Error occurred while exporting file!";
    private static final String PROD_ID = "-//Horo//Exported Calendar// v1.0//EN";
    private static final String CALENDAR_VERSION = "2.0";

    private static final Logger logger = LogsCenter.getLogger(IcsExporter.class);

    private List<EventSource> eventList;
    private List<TaskSource> taskList;

    public IcsExporter(ModelManager model) {
        this.eventList = model.getEvents();
        this.taskList = model.getTasks();
        requireNonNull(eventList);
        requireNonNull(taskList);
    }

    //@@author marcusteh1238
    /**
     * Saves the events in an ics file, whose location is specified in the parameter.
     * @param filepathString the path of where the file should be made.
     * @throws IcsException if the file or directory cannot be created.
     */
    public void export(String filepathString) throws IcsException {
        try {
            logger.info("Attempting to create file at " + filepathString);
            Path filepath = Path.of(filepathString);
            createIfMissing(filepath);

            writeToFile(filepath, generateIcsFileContent(this.eventList, this.taskList));
            logger.info("File successfully created at: " + filepathString);
        } catch (IOException e) {
            throw new IcsException(EXPORT_ERROR_MESSAGE);
        }
    }

    //@@author marcusteh1238
    /**
     * Generates the contents in the .ics file from the event list.
     * @param eventList The list of EventSource objects to be exported.
     * @param taskList The list of TaskSource objects to be exported.
     * @return The .ics file content to be exported.
     */
    protected String generateIcsFileContent(List<EventSource> eventList, List<TaskSource> taskList) {
        StringBuilder stringBuilder = new StringBuilder("BEGIN:VCALENDAR");
        stringBuilder
                .append("\n").append("VERSION:").append(CALENDAR_VERSION)
                .append("\n").append("PRODID:").append(PROD_ID);
        for (EventSource eventSource : eventList) {
            String icsEventString = IcsConverter.convertEvent(eventSource);
            stringBuilder.append("\n").append(icsEventString);
        }
        for (TaskSource taskSource : taskList) {
            String icsEventString = IcsConverter.convertTask(taskSource);
            stringBuilder.append("\n").append(icsEventString);
        }
        stringBuilder.append("\n").append("END:VCALENDAR");
        return stringBuilder.toString();
    }

    //@@author marcusteh1238
    /**
     * Generates a unique name for the exported ICS file, based on the current Instant.
     * @return The file name for the exported ICS file.
     */
    public static String getExportFileName() {
        String timestamp = DateTime.now().toIcsString();
        return "Horo_export_" + timestamp + ".ics";
    }

}
