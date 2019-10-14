package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.person.Slot;

public class ScheduleTest {
    private static String[][] sampleFilledTable =
        new String[][]{
            {"10/9/2019(Thur)", "Welfare-Hazel", "Technical-Johnathan", "Publicity-Lucia"},
            {"6:00pm-6:30pm", "John", "Steven", "NA"},
            {"6:30pm-7:00pm", "Alex", "Clark", "Alice"},
            {"7:00pm-7:30pm", "Alicia", "NA", "Charlie"},
            {"7:30pm-8:00pm", "Bruce", "NA", "Selina"},
            {"8:00pm-8:30pm", "Barry", "NA", "NA"},
            {"8:30pm-9:00pm", "Natal", "NA", "NA"}};

    private static String[][] sampleAvailabilityTable =
        new String[][]{
            {"10/9/2019(Thur)", "Welfare-Hazel", "Technical-Johnathan", "Publicity-Lucia"},
            {"6:00pm-6:30pm", "0", "0", "1"},
            {"6:30pm-7:00pm", "1", "1", "0"},
            {"7:00pm-7:30pm", "1", "1", "0"},
            {"7:30pm-8:00pm", "0", "0", "1"},
            {"8:00pm-8:30pm", "1", "0", "1"},
            {"8:30pm-9:00pm", "1", "0", "0"}};

    /**
     * Compare the resultant Schedule object given a sample interviewer's availability table
     * with the expected Schedule object.
     */
    @Test
    public void constructor_nonEmptyAvailabilityTable_success() {
        LinkedList<LinkedList<String>> table = toTwoDimensionalLinkedList(sampleAvailabilityTable);
        ObservableList<ObservableList<String>> tableObservable = Schedule.toTwoDimensionalObservableList(table);
        ObservableList<String> columnTitles = tableObservable.get(0);
        String date = columnTitles.get(0);

        Schedule schedule = new Schedule(date, table);
        assertEquals(date, schedule.getDate());
        assertEquals(tableObservable, schedule.getTable());
    }

    @Test
    public void getInterviewsSlot_existingInterviewee_success() {
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = toTwoDimensionalLinkedList(sampleFilledTable);
        Schedule schedule = new Schedule(date, sampleData);

        Slot slot1 = new Slot("6:00pm", "6:30pm");
        Slot slot2 = new Slot("7:30pm", "8:00pm");
        Slot slot3 = new Slot("8:30pm", "9:00pm");

        assertEquals(slot1, schedule.getInterviewSlot("Steven"));
        assertEquals(slot2, schedule.getInterviewSlot("Selina"));
        assertEquals(slot3, schedule.getInterviewSlot("Natal"));
    }

    @Test
    public void getInterviewsSlot_nonExistingInterviewee_nullReturned() {
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = toTwoDimensionalLinkedList(sampleFilledTable);
        Schedule schedule = new Schedule(date, sampleData);

        assertNull(schedule.getInterviewSlot("Zoom"));
        assertNull(schedule.getInterviewSlot("NA"));
    }

    @Test
    public void toTwoDimensionalObservableList_twoDimensionalLinkedList_success() {
        LinkedList<LinkedList<String>> list = new LinkedList<>();
        IntStream.range(0, 3).forEach(i -> list.add(getRandomList(5)));
        ObservableList<ObservableList<String>> clone = Schedule.toTwoDimensionalObservableList(list);
        assertEquals(list, clone);
    }

    private LinkedList<String> getRandomList(int n) {
        LinkedList<String> list = new LinkedList<>();
        Random rand = new Random();
        IntStream.range(0, n).forEach(i -> list.add(String.valueOf(rand.nextInt(1000))));
        return list;
    }

    /**
     * // TODO: Fill the javadoc up
     * Test
     * @param table test
     * @return test
     */
    private LinkedList<LinkedList<String>> toTwoDimensionalLinkedList(String[][] table) {
        LinkedList<LinkedList<String>> tableCopy = new LinkedList<>();
        for (String[] row : table) {
            LinkedList<String> rowCopy = new LinkedList<>(Arrays.asList(row));
            tableCopy.add(rowCopy);
        }
        return tableCopy;
    }
}
