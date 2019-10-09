package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ORDER_SHIRTS = new TaskBuilder().withName("Order shirts from supplier")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("inventory").build();
    public static final Task PRINT_POSTERS = new TaskBuilder().withName("Print posters for student fair")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("urgent", "publicity").build();
    public static final Task GET_SPONSORS = new TaskBuilder().withName("Get sponsors for student fair")
            .withStatus(TaskStatus.DOING).build();
    public static final Task RECRUIT_MEMBERS = new TaskBuilder().withName("Recruit members for student fair")
            .withStatus(TaskStatus.DONE)
            .withTags("manpower").build();
    public static final Task ORDER_CATERING = new TaskBuilder().withName("Find caterers for snacks in student fair booth")
            .withStatus(TaskStatus.DOING)
            .withTags("inventory")
            .build();
    public static final Task BRIEFING_PREPARATION = new TaskBuilder()
            .withName("Prepare presentation briefing")
            .withStatus(TaskStatus.DONE)
            .withTags("personal")
            .build();
    public static final Task ORGANISE_STORE = new TaskBuilder()
            .withName("Organise inventory store before student fair")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("inventory", "urgent")
            .build();

    // Manually added
    public static final Task FIND_VP = new TaskBuilder()
            .withName("Find vice president of planning")
            .withStatus(TaskStatus.DOING)
            .withTags("manpower", "urgent")
            .build();
    public static final Task FAREWELL_PARTY = new TaskBuilder()
            .withStatus(TaskStatus.DONE)
            .withTags("recreation", "important")
            .withName("Ida Mueller").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task REVIEW_BUDGET = new TaskBuilder().withName(VALID_TASK_NAME_FINANCE)
            .withStatus(TaskStatus.UNBEGUN)
            .withTags(VALID_TAG_FINANCE).build();
    public static final Task BUILD_WEBSITE = new TaskBuilder().withName(VALID_TASK_NAME_PUBLICITY)
            .withStatus(TaskStatus.DOING)
            .withTags(VALID_TAG_PUBLICITY)
            .build();

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
        return new ArrayList<>(Arrays.asList(ORDER_SHIRTS, PRINT_POSTERS, GET_SPONSORS,
                RECRUIT_MEMBERS, ORDER_CATERING, BRIEFING_PREPARATION, ORGANISE_STORE));
    }
}
