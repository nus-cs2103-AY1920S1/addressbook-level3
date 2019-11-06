package seedu.address.testutil;

import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDEADLINE_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDEADLINE_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_FRIEND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTITLE_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTITLE_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKWEEK_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKWEEK_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.task.Task;


/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withTaskTitle("Meier")
            .withTaskDay("monday")
            .withTaskDescription("Do this on paper")
            .withTaskTime("12:00")
            .withTaskDeadline("09-10-2012")
            .withTaskWeek(0)
            .withTaskTags("CS2109").build();
    public static final Task BENSON = new TaskBuilder().withTaskTitle("GER presentation")
            .withTaskDay("tuesday")
            .withTaskDescription("Add photo")
            .withTaskTime("19:00")
            .withTaskWeek(0)
            .withTaskDeadline("11-11-2011")
            .withTaskTags("photo", "GER1000").build();
    public static final Task CARL = new TaskBuilder().withTaskTitle("Carl Kurz")
            .withTaskTime("13:09")
            .withTaskDescription("heinz@example.com")
            .withTaskDay("wednesday")
            .withTaskWeek(0)
            .withTaskDeadline("10-01-2010").build();
    public static final Task DANIEL = new TaskBuilder().withTaskTitle("Daniel Meier")
            .withTaskTime("10:30")
            .withTaskDescription("cornelia@example.com")
            .withTaskDay("thursday")
            .withTaskTags("friends")
            .withTaskWeek(0)
            .withTaskDeadline("13-03-2000").build();
    public static final Task ELLE = new TaskBuilder().withTaskTitle("Elle Meyer")
            .withTaskTime("08:12")
            .withTaskWeek(0)
            .withTaskDeadline("09-08-2010")
            .withTaskWeek(0)
            .withTaskDescription("werner@example.com")
            .withTaskDay("saturday").build();
    public static final Task FIONA = new TaskBuilder().withTaskTitle("Fiona Kunz")
            .withTaskTime("09:00")
            .withTaskDescription("lydia@example.com")
            .withTaskWeek(0)
            .withTaskDeadline("09-12-2090")
            .withTaskDay("sunday").build();
    public static final Task GEORGE = new TaskBuilder().withTaskTitle("George Best")
            .withTaskTime("07:00")
            .withTaskDescription("anna@example.com")
            .withTaskDeadline("08-10-2010")
            .withTaskWeek(0)
            .withTaskDay("wednesday").build();
    // Manually added
    public static final Task HOON = new TaskBuilder().withTaskTitle("Hoon Meier")
        .withTaskTime("07:30")
        .withTaskDescription("anna@example.com")
        .withTaskDeadline("09-10-2010")
        .withTaskWeek(0)
        .withTaskDay("friday").build();
    public static final Task IDA = new TaskBuilder().withTaskTitle("Ida Mueller")
        .withTaskTime("07:20")
        .withTaskDescription("anna@example.com")
        .withTaskDeadline("11-10-2010")
        .withTaskWeek(0)
        .withTaskDay("thursday").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withTaskTitle(VALID_TASKTITLE_AMY)
        .withTaskTime(VALID_TASKTIME_AMY)
        .withTaskDescription(VALID_TASKDESCRIPTION_AMY)
        .withTaskDay(VALID_TASKDAY_AMY)
        .withTaskWeek(VALID_TASKWEEK_AMY)
        .withTaskDeadline(VALID_TASKDEADLINE_AMY)
        .withTaskTags(VALID_TASKTAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withTaskTitle(VALID_TASKTITLE_BOB)
        .withTaskTime(VALID_TASKTIME_BOB)
        .withTaskDescription(VALID_TASKDESCRIPTION_BOB)
        .withTaskDay(VALID_TASKDAY_BOB)
        .withTaskWeek(VALID_TASKWEEK_BOB)
        .withTaskDeadline(VALID_TASKDEADLINE_BOB)
        .withTaskTags(VALID_TASKTAG_HUSBAND, VALID_TASKTAG_FRIEND).build();



    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code CalendarAddressBook} with all the typical Tasks.
     */
    public static CalendarAddressBook getTypicalCalendarAddressBook() {
        CalendarAddressBook ab = new CalendarAddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
