package seedu.address.model.calendar.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.calendar.CalendarCalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.person.TaskDescription;
import seedu.address.model.calendar.person.TaskPlace;
import seedu.address.model.calendar.person.TaskTitle;
import seedu.address.model.calendar.person.Task;
import seedu.address.model.calendar.person.TaskTime;
import seedu.address.model.calendar.tag.TaskTag;

/**
 * Contains utility methods for populating {@code CalendarCalendarAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {
            new Task(new TaskTitle("Alex Yeoh"), new TaskTime("87438807"), new TaskDescription("alexyeoh@example.com"),
                new TaskPlace("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Task(new TaskTitle("Bernice Yu"), new TaskTime("99272758"), new TaskDescription("berniceyu@example.com"),
                new TaskPlace("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Task(new TaskTitle("Charlotte Oliveiro"), new TaskTime("93210283"), new TaskDescription("charlotte@example.com"),
                new TaskPlace("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Task(new TaskTitle("David Li"), new TaskTime("91031282"), new TaskDescription("lidavid@example.com"),
                new TaskPlace("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Task(new TaskTitle("Irfan Ibrahim"), new TaskTime("92492021"), new TaskDescription("irfan@example.com"),
                new TaskPlace("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Task(new TaskTitle("Roy Balakrishnan"), new TaskTime("92624417"), new TaskDescription("royb@example.com"),
                new TaskPlace("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyCalendarAddressBook getSampleAddressBook() {
        CalendarCalendarAddressBook sampleAb = new CalendarCalendarAddressBook();
        for (Task sampleTask : getSamplePersons()) {
            sampleAb.addPerson(sampleTask);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<TaskTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(TaskTag::new)
                .collect(Collectors.toSet());
    }

}
