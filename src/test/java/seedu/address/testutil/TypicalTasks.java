package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withTags("friends").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withTags("owesMoney", "friends").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withTags("friends").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
