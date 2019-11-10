package seedu.scheduler.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility methods for providing sample scheduled data.
 */
public class SampleDataUtil {

    private static String[][] sampleFilledTable =
            new String[][]{
                    {"10/9/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
                    {"18:00-18:30", "John", "Steven", "0"},
                    {"18:30-19:00", "Alex", "Clark", "John"},
                    {"19:00-19:30", "Alicia", "0", "charlie"},
                    {"19:30-20:00", "Charlie", "0", "Selina"},
                    {"20:00-20:30", "Selina", "0", "0"},
                    {"20:30-21:00", "Natal", "0", "0"}};

    public static List<Schedule> getSampleSchedulesList() {
        LinkedList<Schedule> sampleSchedulesList = new LinkedList<>();
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = toTwoDimensionalLinkedList(sampleFilledTable);
        sampleSchedulesList.add(new Schedule(date, sampleData));

        return sampleSchedulesList;
    }

    /**
     * Returns the given two dimensional array of strings as a two dimensional LinkedList of strings.
     */
    private static LinkedList<LinkedList<String>> toTwoDimensionalLinkedList(String[][] table) {
        LinkedList<LinkedList<String>> tableCopy = new LinkedList<>();
        for (String[] row : table) {
            LinkedList<String> rowCopy = new LinkedList<>(Arrays.asList(row));
            tableCopy.add(rowCopy);
        }
        return tableCopy;
    }
}
