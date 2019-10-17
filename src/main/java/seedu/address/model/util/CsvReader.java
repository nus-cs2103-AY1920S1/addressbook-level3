package seedu.address.model.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import seedu.address.model.Schedule;

/**
 * Helper class to read .csv files (Comma separated values).
 */
public class CsvReader {
    private String filePath;

    /**
     * Constructor for CsvReader object to read from excel.
     * @param filePath
     */
    public CsvReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads from excel and returns the corresponding string.
     * @return String
     * @throws IOException if input file is not found.
     */
    public ArrayList<Schedule> read() throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String firstLine = csvReader.readLine();
        int numberOfDays = getValue(firstLine.split(",")[0]);
        int numberOfColumns = getValue(firstLine.split(",")[1]) + 1;
        ArrayList<Schedule> schedules = new ArrayList<>();
        csvReader.readLine(); //removes next line
        for (int i = 0; i < numberOfDays; i++) {
            LinkedList<LinkedList<String>> table = new LinkedList<>();
            String row;
            boolean firstEncounter = true;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                if (data[0].equals("")) {
                    if (firstEncounter) {
                        firstEncounter = false;
                    } else {
                        break;
                    }
                } else if (numberOfColumns != 0) {
                    LinkedList<String> dataRow = new LinkedList<>();
                    for (int j = 0; j < numberOfColumns; j++) {
                        String element = data[j];
                        dataRow.add(element);
                    }
                    table.add(dataRow);
                }
            }
            String date = table.getFirst().getFirst();
            schedules.add(new Schedule(date, table));
        }
        csvReader.close();
        return schedules;
    }

    private static int getValue(String element) {
        String[] strings = element.split("= ");
        return Integer.parseInt(strings[1]);
    }

    private boolean fileExists() {
        File file = new File(this.filePath);
        return file.exists();
    }
}
