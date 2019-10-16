package seedu.address.testutil;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.address.model.Schedule;

/**
 * A class which gives sample Schedule data.
 */
public class SampleSchedules {
    private static String[][] sampleFilledTable =
        new String[][]{
            {"10/9/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
            {"18:00 - 18:30", "John", "Steven", "0"},
            {"18:30 - 19:00", "Alex", "Clark", "John"},
            {"19:00 - 19:30", "Alicia", "0", "charlie"},
            {"19:30 - 20:00", "Charlie", "0", "Selina"},
            {"20:00 - 20:30", "Selina", "0", "0"},
            {"20:30 - 21:00", "Natal", "0", "0"}};

    private static String[][] sampleAvailabilityTable =
        new String[][]{
            {"10/9/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
            {"18:00 - 18:30", "0", "0", "1"},
            {"18:30 - 19:00", "1", "1", "0"},
            {"19:00 - 19:30", "1", "1", "0"},
            {"19:30 - 20:00", "0", "0", "1"},
            {"20:00 - 20:30", "1", "0", "1"},
            {"20:30 - 21:00", "1", "0", "0"}};

    public static Schedule getSampleFilledSchedule() {
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = TestUtil.toTwoDimensionalLinkedList(sampleFilledTable);
        return new Schedule(date, sampleData);
    }

    public static Schedule getSampleAvailabilityTable() {
        LinkedList<LinkedList<String>> table = TestUtil.toTwoDimensionalLinkedList(sampleAvailabilityTable);
        ObservableList<ObservableList<String>> tableObservable = Schedule.toTwoDimensionalObservableList(table);
        ObservableList<String> columnTitles = tableObservable.get(0);
        String date = columnTitles.get(0);

        return new Schedule(date, table);
    }
}
