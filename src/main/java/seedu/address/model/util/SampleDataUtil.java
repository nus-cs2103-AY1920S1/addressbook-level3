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
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Alex Yeoh"), TaskStatus.UNBEGUN, getTagSet("friends")),
            new Task(new Name("Bernice Yu"), TaskStatus.DOING, getTagSet("colleagues", "friends")),
            new Task(new Name("Charlotte Oliveiro"), TaskStatus.DOING, getTagSet("neighbours")),
            new Task(new Name("David Li"), TaskStatus.DONE, getTagSet("family")),
            new Task(new Name("Irfan Ibrahim"), TaskStatus.DOING, getTagSet("classmates")),
            new Task(new Name("Roy Balakrishnan"), TaskStatus.UNBEGUN, getTagSet("colleagues"))
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
