package seedu.address.ics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

/**
 * Class responsible for exporting Horo's tasks and events into an .ics file.
 */
public class IcsExporter {

    private static final String EXPORT_ERROR_MESSAGE = "Error occurred while exporting file!";
    private static final String PROD_ID = "-//Horo//Exported Calendar// v1.0//EN";
    private static final String CALENDAR_VERSION = "2.0";

    private List<EventSource> eventList;

    public IcsExporter(ModelManager model) {
        this.eventList = model.getEventList();
        requireNonNull(this.eventList);
    }

    /**
     * Saves the events in an ics file, whose location is specified in the parameter.
     * @param filepathString the path of where the file should be made.
     * @throws IOException if the file or directory cannot be created.
     */
    public void export(String filepathString) throws IcsException {
        try {
            Path filepath = Path.of(filepathString);
            createIfMissing(filepath);
            writeToFile(filepath, generateIcsFileContent());
        } catch (IOException e) {
            throw new IcsException(EXPORT_ERROR_MESSAGE);
        }
    }

    /**
     * Generates the contents in the .ics file from the event list.
     * @return The .ics file content to be exported.
     */
    private String generateIcsFileContent() {
        StringBuilder stringBuilder = new StringBuilder("BEGIN:VCALENDAR");
        stringBuilder
                .append("\n").append("VERSION:").append(CALENDAR_VERSION)
                .append("\n").append("PRODID:").append(PROD_ID);
        for (EventSource eventSource : eventList) {
            stringBuilder.append("\n").append(eventSource.toIcsString());
        }
        stringBuilder.append("\n").append("END:VCALENDAR");
        return stringBuilder.toString();
    }

    public static String getExportFileName() {
        String timestamp = DateTime.now().toIcsString();
        return "Horo_export_" + timestamp + ".ics";
    }

}
