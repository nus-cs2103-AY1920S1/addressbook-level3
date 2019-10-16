package seedu.address.ics;

import seedu.address.model.events.EventList;
import seedu.address.model.events.EventSource;

import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

public class IcsExporter {
    private EventList eventList;

    public IcsExporter(EventList eventList) {
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

    private String generateIcsFileContent() {
        StringBuilder stringBuilder = new StringBuilder("BEGIN:VCALENDAR");
        String prodId = "-//Horo//Exported Calendar// v1.0//EN";
        stringBuilder.append("\n").append(prodId);
        for (EventSource eventSource : eventList.getReadOnlyList()) {
            stringBuilder.append("\n").append(eventSource.toIcsString());
        }
        stringBuilder.append("\n").append("END:VCALENDAR");
        return stringBuilder.toString();
    }


}
