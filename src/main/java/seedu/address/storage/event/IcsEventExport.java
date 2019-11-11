package seedu.address.storage.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jfxtras.icalendarfx.VCalendar;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.storage.export.Exporter;

/**
 * Exports events in nJoyAssistant into a ICS File Type
 */
public class IcsEventExport extends Exporter implements EventExport {
    private static final String ICS_FILE_TYPE = ".ics";
    private static final String EVENT_SCHEDULE_FILE_NAME = "nJoy_Schedule";

    /**
     * Exports events into a ics file type. Note that this will replace any existing ics files with the same
     * absolute file path
     * @param vEventRecord Represents the VEvents to be exported
     * @return returns the absolute path of the ics file
     * @throws IOException when trying to create the new file.
     */
    @Override
    public String exportEvent(ReadOnlyVEvents vEventRecord) throws IOException {
        String absoluteFilePath = generateAbsoluteFilePath(EXPORT_DIRECTORY_PATH, EVENT_SCHEDULE_FILE_NAME,
                ICS_FILE_TYPE);
        File directory = new File(EXPORT_DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File outputFile = new File(absoluteFilePath);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        String fileBody = generateIcsFileContent(vEventRecord);

        bw.write(fileBody);
        bw.close();
        return absoluteFilePath;
    }

    /**
     * Method to generate absolute filepath
     * @param exportDirectory directory to export to
     * @param fileName desired file name of exported file
     * @param fileType file type of expected file
     * @return String representation of the absolute export path.
     */
    private String generateAbsoluteFilePath(String exportDirectory, String fileName, String fileType) {
        return exportDirectory + fileName + fileType;
    }

    /**
     * Method to generate ICS File content given ReadOnlyVEvents. Method is backed by VCalendar from jfxtras library.
     * @param vEventRecord ReadOnlyVEvents to be exported
     * @return String representation of the content to be exported.
     */
    private String generateIcsFileContent(ReadOnlyVEvents vEventRecord) {
        VCalendar vCalendar = new VCalendar();
        vCalendar.setVEvents(vEventRecord.getVEventList());
        String fileBody = vCalendar.toString().replaceAll("\n", "\r\n");
        return fileBody;
    }
}
