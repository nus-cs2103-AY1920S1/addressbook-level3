package seedu.address.logic.calendar.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDEADLINE;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskTitleContainsKeywordsPredicate;
import seedu.address.testutil.EditTaskDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TASKTITLE_AMY = "Amy Bee";
    public static final String VALID_TASKTITLE_BOB = "Bob Choo";
    public static final String VALID_TASKTIME_AMY = "10:00";
    public static final String VALID_TASKTIME_BOB = "22:00";
    public static final String VALID_TASKDESCRIPTION_AMY = "amy@example.com";
    public static final String VALID_TASKDESCRIPTION_BOB = "bob@example.com";
    public static final String VALID_TASKDAY_AMY = "tuesday";
    public static final String VALID_TASKDAY_BOB = "monday";
    public static final String VALID_TASKDEADLINE_AMY = "10-10-2010";
    public static final String VALID_TASKDEADLINE_BOB = "12-01-2011";
    public static final int VALID_TASKWEEK_AMY = 0;
    public static final int VALID_TASKWEEK_BOB = 0;
    public static final String VALID_TASKTAG_HUSBAND = "husband";
    public static final String VALID_TASKTAG_FRIEND = "friend";

    public static final String TASKTITLE_DESC_AMY = " " + PREFIX_TASKTITLE + VALID_TASKTITLE_AMY;
    public static final String TASKTITLE_DESC_BOB = " " + PREFIX_TASKTITLE + VALID_TASKTITLE_BOB;
    public static final String TASKTIME_DESC_AMY = " " + PREFIX_TASKTIME + VALID_TASKTIME_AMY;
    public static final String TASKTIME_DESC_BOB = " " + PREFIX_TASKTIME + VALID_TASKTIME_BOB;
    public static final String TASKDESCRIPTION_DESC_AMY = " " + PREFIX_TASKDESCRIPTION + VALID_TASKDESCRIPTION_AMY;
    public static final String TASKDESCRIPTION_DESC_BOB = " " + PREFIX_TASKDESCRIPTION + VALID_TASKDESCRIPTION_BOB;
    public static final String TASKDEADLINE_DESC_AMY = " " + PREFIX_TASKDEADLINE + VALID_TASKDEADLINE_AMY;
    public static final String TASKDEADLINE_DESC_BOB = " " + PREFIX_TASKDEADLINE + VALID_TASKDEADLINE_BOB;
    public static final String TASKDAY_DESC_AMY = " " + PREFIX_TASKDAY + VALID_TASKDAY_AMY;
    public static final String TASKDAY_DESC_BOB = " " + PREFIX_TASKDAY + VALID_TASKDAY_BOB;
    public static final String TASKTAG_DESC_FRIEND = " " + PREFIX_TASKTAG + VALID_TASKTAG_FRIEND;
    public static final String TASKTAG_DESC_HUSBAND = " " + PREFIX_TASKTAG + VALID_TASKTAG_HUSBAND;

    public static final String INVALID_TASKTITLE_DESC = " " + PREFIX_TASKTITLE + "James&";
    public static final String INVALID_TASKTIME_DESC = " " + PREFIX_TASKTIME + "911a";
    public static final String INVALID_TASKDESCRIPTION_DESC = " " + PREFIX_TASKDESCRIPTION + "bob!yahoo";
    public static final String INVALID_TASKDAY_DESC = " " + PREFIX_TASKDAY;
    public static final String INVALID_TASKTAG_DESC = " " + PREFIX_TASKTAG + "hubby*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor DESC_AMY;
    public static final EditCommand.EditTaskDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTaskDescriptorBuilder().withTaskTitle(VALID_TASKTITLE_AMY)
            .withTaskTime(VALID_TASKTIME_AMY)
            .withTaskDescription(VALID_TASKDESCRIPTION_AMY)
            .withTaskDay(VALID_TASKDAY_AMY)
            .withTaskTags(VALID_TASKTAG_FRIEND).build();
        DESC_BOB = new EditTaskDescriptorBuilder().withTaskTitle(VALID_TASKTITLE_BOB)
            .withTaskTime(VALID_TASKTIME_BOB)
            .withTaskDescription(VALID_TASKDESCRIPTION_BOB)
            .withTaskDay(VALID_TASKDAY_BOB)
            .withTaskTags(VALID_TASKTAG_HUSBAND, VALID_TASKTAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, CalendarModel actualModel,
                                            CommandResult expectedCommandResult, CalendarModel expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, CalendarModel, CommandResult, CalendarModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, CalendarModel actualModel, String expectedMessage,
                                            CalendarModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered task list and selected task in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, CalendarModel actualModel, String expectedMessage) {
        // we are unable to defensively copy the calendarModel for comparison later, so we can
        // only do so by copying its components.
        CalendarAddressBook expectedAddressBook =
            new CalendarAddressBook(actualModel.getCalendarAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getCalendarAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
    }
    /**
     * Updates {@code calendarModel}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code calendarModel}'s address book.
     */
    public static void showPersonAtIndex(CalendarModel calendarModel, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < calendarModel.getFilteredTaskList().size());

        Task task = calendarModel.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getTaskTitle().fullName.split("\\s+");
        calendarModel.updateFilteredTaskList(new TaskTitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, calendarModel.getFilteredTaskList().size());
    }

}
