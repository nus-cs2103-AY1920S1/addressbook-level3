package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.deadline.Deadline;


/**
 * A utility class containing a list of {@code FlashCard} objects to be used in tests.
 */
public class TypicalDeadlines {

    public static final String VALID_TASK_STR = "Complete Deadline Scheduler.";
    public static final String VALID_TASK_STR_2 = "Complete Calendar GUI.";
    public static final String VALID_DUEDATE_STR = "01/10/2019";
    public static final String VALID_DUEDATE_STR_2 = "11/10/2019";

    public static final Deadline COMPLETE_SCHEDULER = new DeadlineBuilder().withTask(VALID_TASK_STR)
            .withDueDate(VALID_DUEDATE_STR).build();

    public static final Deadline COMPLETE_CALENDAR = new DeadlineBuilder().withTask(VALID_TASK_STR_2)
            .withDueDate(VALID_DUEDATE_STR_2).build();

    private TypicalDeadlines() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical flashcards.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Deadline deadline : getTypicalDeadlines()) {
            ab.addDeadline(deadline);
        }
        return ab;
    }

    public static List<Deadline> getTypicalDeadlines() {
        return new ArrayList<>(
                Arrays.asList(COMPLETE_SCHEDULER, COMPLETE_CALENDAR));
    }
}
