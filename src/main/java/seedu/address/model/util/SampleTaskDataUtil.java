package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleTaskDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Review Budget"), TaskStatus.UNBEGUN, getTagSet("Finance")),
            new Task(new Name("Increase Funding"), TaskStatus.DOING, getTagSet("Finance", "Urgent")),
            new Task(new Name("Settle Claims"), TaskStatus.DOING, getTagSet("Finance")),
            new Task(new Name("Update Website"), TaskStatus.DONE, getTagSet("Branding")),
            new Task(new Name("Shirts for Freshman Open Day"), TaskStatus.DOING, getTagSet("Logistics")),
            new Task(new Name("Design Poster"), TaskStatus.UNBEGUN, getTagSet("Branding"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
