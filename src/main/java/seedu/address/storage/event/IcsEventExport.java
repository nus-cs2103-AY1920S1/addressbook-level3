package seedu.address.storage.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jfxtras.icalendarfx.VCalendar;
import seedu.address.model.event.ReadOnlyVEvents;

public class IcsEventExport implements EventExport {
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String SCHEDULE_FILE_NAME = "nJoy_Event_Schedule";
    private static final String ICS_FILE_TYPE = ".ics";

    @Override
    public String exportEvent(String targetDirectory, ReadOnlyVEvents vEventRecord) throws IOException {
        VCalendar vCalendar = new VCalendar();
        vCalendar.setVEvents(vEventRecord.getVEventList());

        String fullFilePath = targetDirectory + FILE_SEPARATOR + SCHEDULE_FILE_NAME + ICS_FILE_TYPE;
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
