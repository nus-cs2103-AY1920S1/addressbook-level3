package seedu.address.model.calendar.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

/**
 * Contains utility methods for populating {@code CalendarAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {
            new Task(new TaskTitle("CS2100 Assignment"), new TaskDay("monday"),
                    new TaskDescription("Hardcopy only"),
                    new TaskDeadline("12-10-2012"),
                    new TaskTime("13:00"), getTagSet("CS2100")),
            new Task(new TaskTitle("Create slides"), new TaskDay("tuesday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("12:45"), getTagSet("presentation")),
            new Task(new TaskTitle("CS2102 Assignment"), new TaskDay("wednesday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("15:00"), getTagSet("CS2100")),
            new Task(new TaskTitle("CS2103 Assignment"), new TaskDay("thursday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("08:19"), getTagSet("CS2100")),
            new Task(new TaskTitle("CS2103 In Lecture Quiz"), new TaskDay("friday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("09:00"), getTagSet("CS2100")),
            new Task(new TaskTitle("CS2103 Quiz"), new TaskDay("sunday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("09:00"), getTagSet("CS2100")),
            new Task(new TaskTitle("CS2109 Lab"), new TaskDay("sunday"),
                new TaskDescription("Hardcopy only"),
                new TaskDeadline("12-10-2012"),
                new TaskTime("09:00"), getTagSet("CS2100"))
        };
    }

    public static ReadOnlyCalendarAddressBook getSampleAddressBook() {
        CalendarAddressBook sampleAb = new CalendarAddressBook();
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
