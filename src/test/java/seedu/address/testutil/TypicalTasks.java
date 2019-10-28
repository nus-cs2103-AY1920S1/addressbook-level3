package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task CS2103T = new TaskBuilder().withClassId("CS2103T")
            .withMarking("Y").withTaskTimes("10/10/2019 13:00, 10/10/2019 15:00")
            .build();
    public static final Task CS2100 = new TaskBuilder().withClassId("CS2100")
            .withMarking("N").withTaskTimes("10/10/2019 13:00, 10/10/2019 15:00", "13/10/2019 15:00, 13/10/2019 16:00")
            .build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2100));
    }
}
