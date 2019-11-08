package seedu.address.model.calendar.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.ModuleTask;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;
import seedu.address.model.calendar.task.ToDoTask;

/**
 * Contains utility methods for populating {@code CalendarAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {
            new ToDoTask(new TaskTitle("Create slides"), new TaskDay("monday"),
                    new TaskDescription("Check comments"),
                    new TaskDeadline("01-09-2020"),
                    new TaskTime("12:00"), getTagSet("GER1000"), 0),
            new ToDoTask(new TaskTitle("Fix code bugs"), new TaskDay("tuesday"),
                new TaskDescription("Update documentation"),
                new TaskDeadline("20-03-2010"),
                new TaskTime("12:35"), getTagSet("BUG"), 0),
            new ModuleTask(new TaskTitle("CS2100 Lab"), new TaskDay("wednesday"),
                new TaskDescription("Prepare diagram"),
                new TaskDeadline("12-12-9999"),
                new TaskTime("15:00"), getTagSet("CS2100"), 0),
            new ToDoTask(new TaskTitle("ZZ2100 Assignment"), new TaskDay("thursday"),
                new TaskDescription("Submit hardcopy"),
                new TaskDeadline("01-01-2012"),
                new TaskTime("08:19"), getTagSet("ZZ2100"), 0),
            new ToDoTask(new TaskTitle("Buy stuff"), new TaskDay("friday"),
                new TaskDescription("Fairprice"),
                new TaskDeadline("12-10-2013"),
                new TaskTime("09:00"), getTagSet("grocery"), 0),
            new ToDoTask(new TaskTitle("CS2103 Quiz"), new TaskDay("sunday"),
                new TaskDescription("Before midnight"),
                new TaskDeadline("12-10-2014"),
                new TaskTime("09:00"), getTagSet("CS2100"), 0),
            new ModuleTask(new TaskTitle("CS2109 Lab"), new TaskDay("thursday"),
                new TaskDescription("Bring calculator"),
                new TaskDeadline("12-12-9999"),
                new TaskTime("09:00"), getTagSet("CS2109"), 0)
        };
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
