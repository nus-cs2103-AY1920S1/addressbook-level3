package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
<<<<<<< HEAD
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
=======
>>>>>>> team/master
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
<<<<<<< HEAD:src/main/java/seedu/address/model/util/SampleDataUtil.java
public class SampleDataUtil {
<<<<<<< HEAD
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
=======
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Task(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Task(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Task(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Task(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Task(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
>>>>>>> team/master
                getTagSet("colleagues"))
=======
public class SampleTaskDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Review Budget"), TaskStatus.UNBEGUN, getTagSet("Finance")),
            new Task(new Name("Increase Funding"), TaskStatus.DOING, getTagSet("Finance", "Urgent")),
            new Task(new Name("Settle Claims"), TaskStatus.DOING, getTagSet("Finance")),
            new Task(new Name("Update Website"), TaskStatus.DONE, getTagSet("Branding")),
            new Task(new Name("Shirts for Freshman Open Day"), TaskStatus.DOING, getTagSet("Logistics")),
            new Task(new Name("Design Poster"), TaskStatus.UNBEGUN, getTagSet("Branding"))
>>>>>>> team/master:src/main/java/seedu/address/model/util/SampleTaskDataUtil.java
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
