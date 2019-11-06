package seedu.scheduler.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.Schedule;

/**
 * Helper class to write to .csv(Comma-Separated Values) files.
 */
public class CsvWriter {
    private String destinationFile;
    private Model model;

    /**
     * Constructor for CsvWriter.
     * @param destinationFile File to write to.
     * @param model Where data resides.
     */
    public CsvWriter(String destinationFile, Model model) {
        this.destinationFile = destinationFile;
        this.model = model;
    }

    /**
     * Writes current scheduleList from model into destination file.
     * @throws IOException when FileWriter fails.
     */
    public void writeSchedulesToFile() throws IOException {
        FileWriter csvWriter = new FileWriter(destinationFile);
        List<Schedule> schedules = model.getSchedulesList();
        for (Schedule schedule: schedules) {
            ObservableList<ObservableList<String>> rows = schedule.getObservableList();
            csvWriter.append(String.join(",", schedule.getTitles()));
            csvWriter.append("\n");
            for (ObservableList<String> rowData: rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
            csvWriter.append("\n").append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
