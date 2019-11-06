package seedu.scheduler.testutil;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.scheduler.model.Schedule;

/**
 * A class which gives sample Schedule data.
 */
public class SampleSchedules {
    private static String[][] sampleFilledTable =
        new String[][]{
                {"10/09/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
                {"18:00-18:30", "John", "Steven", "0"},
                {"18:30-19:00", "Alex", "Clark", "John"},
                {"19:00-19:30", "Alicia", "0", "charlie"},
                {"19:30-20:00", "Charlie", "0", "Selina"},
                {"20:00-20:30", "Selina", "0", "0"},
                {"20:30-21:00", "Natal", "0", "0"}};

    private static String[][] sampleAvailabilityTable =
        new String[][]{
                {"10/09/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
                {"18:00-18:30", "0", "0", "1"},
                {"18:30-19:00", "1", "1", "0"},
                {"19:00-19:30", "1", "1", "0"},
                {"19:30-20:00", "0", "0", "1"},
                {"20:00-20:30", "1", "0", "1"},
                {"20:30-21:00", "1", "0", "0"}};

    private static String[][] sampleAvailabilityTable2 =
            new String[][]{
                    {"10/10/2019", "Department A - Person A", "Department B - Person B", "Department C - Person C",
                     "Department D - Person D", "Department E - Person E", "Department F - Person F",
                     "Department G - Person G", "Department H - Person H", "Department I - Person I",
                     "Department J - Person J"},
                    {"10:00-10:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"10:30-11:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"11:00-11:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"11:30-12:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"12:00-12:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"12:30-13:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"13:00-13:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"13:30-14:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"14:00-14:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"14:30-15:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"15:00-15:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"15:30-16:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"16:00-16:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"16:30-17:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"17:00-17:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"17:30-18:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"18:00-18:30", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"18:30-19:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"19:00-19:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"19:30-20:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"20:00-20:30", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
                    {"20:30-21:00", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}};

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

    public static Schedule getSampleAvailabilityTable2() {
        LinkedList<LinkedList<String>> table = TestUtil.toTwoDimensionalLinkedList(sampleAvailabilityTable2);
        ObservableList<ObservableList<String>> tableObservable = Schedule.toTwoDimensionalObservableList(table);
        ObservableList<String> columnTitles = tableObservable.get(0);
        String date = columnTitles.get(0);

        return new Schedule(date, table);
    }
}
