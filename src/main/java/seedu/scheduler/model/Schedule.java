package seedu.scheduler.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.IntervieweeSlot;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Represents the interview schedule.
 * The first row of the Schedule is the column titles, with the first cell as the date of the interview schedule.
 * Subsequent rows are time slots, with the first cell of each row as the timing of all the time slots in the row.
 */
public class Schedule implements Comparable<Schedule> {
    private static String columnTitleFormat = "%s - %s";
    private String date;
    private ObservableList<String> titles;
    private ObservableList<ObservableList<String>> data; // EXCLUDE the first row which is the column titles

    public Schedule(String date, LinkedList<LinkedList<String>> list) {
        this.date = date;

        ObservableList<ObservableList<String>> table = toTwoDimensionalObservableList(list);
        if (table.isEmpty()) {
            this.titles = FXCollections.observableList(new LinkedList<>());
        } else {
            this.titles = table.remove(0); // separate the first row out which is the column titles.
        }

        this.data = table;
    }

    private Schedule() {
    }

    /**
     * Returns a copy of the @code{Schedule} object given.
     */
    public static Schedule cloneSchedule(Schedule schedule) {
        Schedule clone = new Schedule();
        clone.date = String.valueOf(schedule.date);
        clone.titles = cloneRow(schedule.titles);
        clone.data = cloneTable(schedule.data);
        return clone;
    }

    /**
     * Returns an independent deep copy of the table given in observable list form.
     */
    private static ObservableList<ObservableList<String>> cloneTable(ObservableList<ObservableList<String>> table) {
        ObservableList<ObservableList<String>> tableClone = FXCollections.observableList(new LinkedList<>());

        for (ObservableList<String> row : table) {
            ObservableList<String> rowClone = cloneRow(row);
            tableClone.add(rowClone);
        }

        return tableClone;
    }

    /**
     * Returns an independent deep copy of the row given in observable list form.
     */
    private static ObservableList<String> cloneRow(ObservableList<String> row) {
        ObservableList<String> rowClone = FXCollections.observableList(new LinkedList<>());
        for (String string : row) {
            rowClone.add(String.valueOf(string));
        }
        return rowClone;
    }

    /**
     * Convert a two-dimensional LinkedList into a two-dimensional Observable list.
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

    public String getDate() {
        return date;
    }

    public ObservableList<String> getTitles() {
        return titles;
    }

    public ObservableList<ObservableList<String>> getObservableList() {
        return data;
    }

    /**
     * Returns a list of slots allocated to the interviewee.
     */
    public List<Slot> getInterviewSlots(String intervieweeName) {
        List<Slot> slots = new LinkedList<>();
        int tableSize = data.size();

        for (int i = 0; i < tableSize; i++) {
            ObservableList<String> row = data.get(i);
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
     * Adds all the interviewees that the interviewer will be interviewing into the schedule. An interviewee will only
     * be added if the date and timing that it will be interviewed falls in this schedule, as well as the interviewer.
     * The slot that the interviewee to be added into must be available too, i.e. must be "1" and not "0", else a
     * ScheduleException will be thrown.
     */
    public void addAllocatedInterviewees(Interviewer interviewer, List<IntervieweeSlot> slots)
            throws ScheduleException {
        for (IntervieweeSlot intervieweeSlot : slots) {
            addAllocatedInterviewee(interviewer, intervieweeSlot.getInterviewee(), intervieweeSlot.getSlot());
        }
    }

    /**
     * Adds the given interviewee into the schedule, refer the description of @code{allAllocatedInterviewees} for more
     * details.
     */
    private void addAllocatedInterviewee(Interviewer interviewer, Interviewee interviewee, Slot slot)
            throws ScheduleException {
        if (!slot.date.equals(this.date)) {
            return;
        }

        // Locate interviewer and slot
        int columnIndex = locateInterviewer(interviewer);
        int rowIndex = locateSlot(slot);

        // If interviewer or slot is not present in the schedule
        if (columnIndex == -1 || rowIndex == -1) {
            return;
        }

        ObservableList<String> row = data.get(rowIndex);
        if (!row.get(columnIndex).equals("1")) {
            throw new ScheduleException("Slot where an interviewer is to be added is not labelled as 1,"
                    + " i.e. not available!");
        }
        row.set(columnIndex, interviewee.getName().toString());
    }

    /**
     * Returns the index of the column at which the interviewer is located if the interviewer is present in
     * the schedule, otherwise returns -1.
     */
    private int locateInterviewer(Interviewer interviewer) {
        String columnTitle = generateColumnTitle(interviewer);
        return titles.indexOf(columnTitle);
    }

    /**
     * Returns the index of the row where the slot is located based on its timing if the slot is present in the schedule
     * , otherwise returns -1.
     */
    private int locateSlot(Slot slot) {
        int rowIndex = -1;
        int size = data.size();

        // Search through the date of all the rows
        for (int i = 0; i < size; i++) {
            String currTime = data.get(i).get(0);
            if (currTime.equals(slot.getTiming())) {
                rowIndex = i;
                break;
            }
        }

        return rowIndex;
    }

    /**
     * Returns true if an interviewer exists in the Schedule.
     */
    public boolean hasInterviewer(Interviewer interviewer) {
        String columnTitle = generateColumnTitle(interviewer);

        boolean found = false;
        for (String title : titles) {
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
    private String generateColumnTitle(Interviewer interviewer) {
        return String.format(columnTitleFormat, interviewer.getDepartment().toString(),
            interviewer.getName().toString());
    }

    @Override
    public boolean equals(Object s) {
        if (!(s instanceof Schedule)) {
            return false;
        }
        Schedule sCasted = (Schedule) s;
        return date.equals(sCasted.date)
            && titles.equals(sCasted.titles)
            && data.equals(sCasted.data);
    }

    @Override
    public int compareTo(Schedule other) {
        SimpleDateFormat format = new SimpleDateFormat(Slot.DATETIME_PARSE_PATTERN);

        try {
            Date dateObject = format.parse(date);
            Date otherDateObject = format.parse(other.date);
            return dateObject.compareTo(otherDateObject);
        } catch (ParseException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(450);

        // Append the title rows
        String titleRep = rowToString(titles);
        builder.append(titleRep);

        // Append the other rows
        for (ObservableList<String> row : data) {
            String rowRep = rowToString(row);
            builder.append(rowRep);
        }

        return builder.toString();
    }

    /**
     * Convert a row to its string representation (each value separated by a comma, then the row ends with
     * a newline character.
     */
    private String rowToString(List<String> row) {
        StringBuilder builder = new StringBuilder(110);

        for (String value : row) {
            builder.append(value);
            builder.append(",");
        }

        builder.append("\n");
        return builder.toString();
    }
}
