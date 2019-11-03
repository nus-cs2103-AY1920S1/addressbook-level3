package seedu.address.storage.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jfxtras.icalendarfx.VCalendar;
import seedu.address.model.event.ReadOnlyVEvents;

/**
 * Exports events in nJoyAssistant into a ICS File Type
 */
public class IcsEventExport implements EventExport {
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String SCHEDULE_FILE_NAME = "nJoy_Event_Schedule";
    private static final String ICS_FILE_TYPE = ".ics";

    /**
     * Exports events into a ics file type. Note that this will replace any existing ics files with the same
     * SCHEDULE_FILE_NAME
     * @param targetDirectory targetDirectory to export ics file to
     * @param vEventRecord Represents the VEvents to be exported
     * @return returns the absolute path of the ics file
     * @throws IOException when trying to create the new file.
     */
    @Override
    public String exportEvent(String targetDirectory, ReadOnlyVEvents vEventRecord) throws IOException {
        VCalendar vCalendar = new VCalendar();
        vCalendar.setVEvents(vEventRecord.getVEventList());

        String fullFilePath = FILE_SEPARATOR + targetDirectory + FILE_SEPARATOR + SCHEDULE_FILE_NAME + ICS_FILE_TYPE;
        String fileBody = vCalendar.toString().replaceAll("\n", "\r\n");

        File outputFile = new File(fullFilePath);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(fileBody);
        bw.close();
        return fullFilePath;
    }
}
