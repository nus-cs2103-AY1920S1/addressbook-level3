package seedu.address.logic.calendar.commands;

import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;
import seedu.address.model.calendar.task.Task;
import seedu.address.testutil.TaskBuilder;


/**
 * Contains integration tests (interaction with the CalendarModel) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private CalendarModel calendarModel;

    @BeforeEach
    public void setUp() {
        calendarModel = new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());
    }

    @Test
    public void execute_newTask_success() {
        Task validTask = new TaskBuilder().build();

        CalendarModel expectedModel = new CalendarModelManager(calendarModel.getCalendarAddressBook(),
                new CalendarUserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(new AddCommand(validTask), calendarModel,
                String.format(AddCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Task personInList = calendarModel.getCalendarAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), calendarModel, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
