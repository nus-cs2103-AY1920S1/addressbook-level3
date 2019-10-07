package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Task(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Task(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Task(new Name("David Li"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Task(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Task(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                getTagSet("colleagues"))
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
