package seedu.address.ics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.model.events.EventSource;

/**
 * Class responsible for exporting Horo's tasks and events into an .ics file.
 */
public class IcsExporter {
    private List<EventSource> eventList;

    public IcsExporter(List<EventSource> eventList) {
        requireNonNull(eventList);
        this.eventList = eventList;
    }

    /**
     * Saves the events in an ics file, whose location is specified in the parameter.
     * @param filePath the directory where the file should be made.
     * @throws IOException if the file or directory cannot be created.
     */
    public void export(Path filePath) throws IOException {
        createIfMissing(filePath);
        writeToFile(filePath, generateIcsFileContent());
    }

    /**
     * Generates the contents in the .ics file from the event list.
     * @return The .ics file content to be exported.
     */
    private String generateIcsFileContent() {
        StringBuilder stringBuilder = new StringBuilder("BEGIN:VCALENDAR");
        String prodId = "-//Horo//Exported Calendar// v1.0//EN";
        stringBuilder.append("\n").append(prodId);
        for (EventSource eventSource : eventList) {
            stringBuilder.append("\n").append(eventSource.toIcsString());
        }
        stringBuilder.append("\n").append("END:VCALENDAR");
        return stringBuilder.toString();
    }


}
