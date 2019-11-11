package seedu.address.model.calendar.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;


/**
 * Contains utility methods for populating {@code CalendarAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {};
    }

    public static ReadOnlyCalendarAddressBook getSampleAddressBook() {
        CalendarAddressBook sampleAb = new CalendarAddressBook();
        for (Task sampleTask : getSamplePersons()) {
            sampleAb.addTask(sampleTask);
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
