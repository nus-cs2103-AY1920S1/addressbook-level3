package seedu.address.model;

import java.util.LinkedList;

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

    public String getDate() {
        return date;
    }

    public ObservableList<ObservableList<String>> getTable() {
        return table;
    }

    public Slot getInterviewSlot(String intervieweeName) {
        String timeSlot = null;
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
                    timeSlot = row.get(0);
                }
            }
        }

        if (timeSlot == null) {
            return null;
        } else {
            String[] times = timeSlot.split("-");
            String start = times[0].trim();
            String end = times[1].trim();

            return new Slot(start, end);
        }
    }

    public boolean addInterviewer(Interviewer interviewer) {
        return true;
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
}