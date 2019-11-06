package seedu.scheduler.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
     * @return ArrayList of exported table for testing purposes.
     * @throws IOException when FileWriter fails.
     */
    public ArrayList<String> writeSchedulesToFile() throws IOException {
        FileWriter csvWriter = new FileWriter(destinationFile);
        List<Schedule> schedules = model.getSchedulesList();
        ArrayList<String> table = new ArrayList<>();
        for (Schedule schedule: schedules) {
            ObservableList<ObservableList<String>> rows = schedule.getObservableList();
            csvWriter.append(String.join(",", schedule.getTitles()));
            csvWriter.append("\n");

            table.add(String.join(",", schedule.getTitles()));

            for (ObservableList<String> rowData: rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");

                table.add(String.join(",", rowData));
            }
            csvWriter.append("\n").append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        return table;
    }
}
