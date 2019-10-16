package seedu.address.model.calendar.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.calendar.CalendarCalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.person.Task;
import seedu.address.model.calendar.person.TaskDescription;
import seedu.address.model.calendar.person.TaskPlace;
import seedu.address.model.calendar.person.TaskTime;
import seedu.address.model.calendar.person.TaskTitle;
import seedu.address.model.calendar.tag.TaskTag;

/**
 * Contains utility methods for populating {@code CalendarCalendarAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {
            new Task(new TaskTitle("CS2100 Assignment"), new TaskTime("31 December 2019"),
                    new TaskDescription("Hardcopy only"),
                    new TaskPlace("Blk 30 Geylang Street 29, #06-40"), getTagSet("CS2100"))
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
