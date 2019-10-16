package seedu.address.model;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;

/**
 * Represents the interview schedule.
 * The first row of the Schedule is the column titles, with the first cell as the date of the interview schedule.
 * Subsequent rows are time slots, with the first cell of each row as the timing of all the time slots in the row.
 */
public class Schedule {
    private String date;
    private ObservableList<ObservableList<String>> table; // Include the first row which is the column titles

    public Schedule(String date, LinkedList<LinkedList<String>> list) {
        this.date = date;
        this.table = toTwoDimensionalObservableList(list);
    }

    private Schedule() {
    }

    public String getDate() {
        return date;
    }

    public ObservableList<ObservableList<String>> getObservableList() {
        return table;
    }

    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        int tableSize = table.size();

        // Exclude search in the first row as the first row is column titles
        for (int i = 1; i < tableSize; i++) {
            ObservableList<String> row = table.get(i);
            int rowSize = row.size();

            // Exclude search in the first cell as the first cell is the time slot
            for (int j = 1; j < rowSize; j++) {
                String value = row.get(j);
                if ("NA".equals(value)) {
                    continue;
                } else if (intervieweeName.equals(value)) {
                    String timeSlot = row.get(0);
                    String[] times = timeSlot.split("-");
                    String start = times[0].trim();
                    String end = times[1].trim();
                    slots.add(new Slot(date, start, end));
                }
            }
        }

        return slots;
    }

    /**
     * Returns true if an interviewer exists in the Schedule.
     */
    public boolean hasInterviewer(Interviewer interviewer) {
        String columnTitle = generateColumnTitle(interviewer);
        ObservableList<String> firstRow = table.get(0);

        boolean found = false;
        for (String title : firstRow) {
            if (title.equals(columnTitle)) {
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Returns the corresponding column title of the given interviewer.
     */
    public String generateColumnTitle(Interviewer interviewer) {
        return String.format("%s - %s", interviewer.getDepartment().toString(),
            interviewer.getName().toString());
    }

    /**
     * Adds the interviewer and his availabilities into this table.
     * The interviewer will not be added if none of the his availabilities fall in the table.
     */
    public boolean addInterviewer(Interviewer interviewer) {
        String columnTitle = generateColumnTitle(interviewer);
        List<String> availabilities = interviewer.getAvailabilities();

        boolean added = false;
        int currRowIndex = 1;
        for (String availability : availabilities) {
            if (currRowIndex > table.size()) {
                break;
            }

            List<String> dateAndTime = getDateAndTime(availability);
            String date = dateAndTime.get(0);
            String time = dateAndTime.get(1);

            if (!this.date.equals(date)) {
                continue;
            }

            // Iterate through the table rows
            int tableSize = table.size();
            int i;
            for (i = currRowIndex; i < tableSize; i++) {
                ObservableList<String> currRow = table.get(i);
                String currRowTime = currRow.get(0);

                if (!currRowTime.equals(time)) {
                    continue;
                } else {
                    currRow.add("1");
                    added = true;
                    break;
                }
            }
            currRowIndex = i;
        }

        // Add 0 to other rows to ensure that the table rows size are correct
        if (added) {
            int initialRowSize = table.get(0).size();
            table.get(0).add(columnTitle);
            for (int i = 1; i < table.size(); i++) {
                ObservableList<String> currRow = table.get(i);
                if (currRow.size() == initialRowSize) {
                    currRow.add("0");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // DD/MM/YYYY HH:MM - HH:MM (No trailing whitespace!)
    // e.g. 9/10/2019 18:00 - 18:30
    private List<String> getDateAndTime(String dateTimeString) {
        String date = dateTimeString.split(" ")[0];
        int firstWhiteSpaceIndex = dateTimeString.indexOf(" ");
        String time = dateTimeString.substring(firstWhiteSpaceIndex + 1).trim();

        List<String> dateAndTime = new LinkedList<>();
        dateAndTime.add(date);
        dateAndTime.add(time);

        return dateAndTime;
    }

    @Override
    public boolean equals(Object s) {
        if (!(s instanceof Schedule)) {
            return false;
        }
        Schedule sCasted = (Schedule) s;
        return date.equals(sCasted.date)
            && table.equals(sCasted.table);
    }

    /**
     * Returns a copy of the @code{Schedule} object given.
     *
     * @param schedule the @code{Schedule} object to be copied.
     * @return the copy of the @code{Schedule} object.
     */
    public static Schedule cloneSchedule(Schedule schedule) {
        Schedule clone = new Schedule();
        clone.date = String.valueOf(schedule.date);
        clone.table = cloneTable(schedule.table);
        return clone;
    }

    /**
     * Returns an independent copy of the table given in observable list form.
     *
     * @param table the table to copy.
     * @return the copy of the table.
     */
    private static ObservableList<ObservableList<String>> cloneTable(ObservableList<ObservableList<String>> table) {
        ObservableList<ObservableList<String>> tableClone = FXCollections.observableList(new LinkedList<>());

        for (ObservableList<String> row : table) {
            ObservableList<String> rowClone = FXCollections.observableList(new LinkedList<>());
            for (String string : row) {
                rowClone.add(String.valueOf(string));
            }
            tableClone.add(rowClone);
        }

        return tableClone;
    }

    /**
     * Convert a two-dimensional LinkedList into a two-dimensional Observable list.
     *
     * @param list a two-dimensional LinkedList
     * @return the corresponding two-dimensional Observable list
     */
    public static ObservableList<ObservableList<String>> toTwoDimensionalObservableList(
        LinkedList<LinkedList<String>> list) {
        LinkedList<ObservableList<String>> clone = new LinkedList<>();

        // Shallow copy can be used here as String is immutable.
        list.forEach(row -> {
            LinkedList<String> rowCopy = (LinkedList<String>) row.clone();
            clone.add(FXCollections.observableList(rowCopy));
        });

        return FXCollections.observableList(clone);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(450);
        for (ObservableList<String> row : table) {
            for (String value : row) {
                buffer.append(value);
                buffer.append(",");
            }
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
