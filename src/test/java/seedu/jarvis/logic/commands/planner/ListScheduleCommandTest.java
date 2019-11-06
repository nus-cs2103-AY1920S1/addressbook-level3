package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.tasks.Deadline;

class ListScheduleCommandTest {

    @Test
    void getCommandWord() {
        String expected = "list-schedule";
        ListScheduleCommand command = new ListScheduleCommand();

        assertEquals(expected, command.getCommandWord());
    }

    @Test
    void hasInverseExecution() {
        ListScheduleCommand command = new ListScheduleCommand();
        assertFalse(command.hasInverseExecution());
    }

    @Test
    void execute() {
        Model model = new ModelManager();
        model.addTask(new Deadline("test", LocalDate.now()));

        Model expected = new ModelManager();
        expected.addTask(new Deadline("test", LocalDate.now()));
        expected.updateSchedule();

        ListScheduleCommand command = new ListScheduleCommand();

        assertCommandSuccess(command, model, ListScheduleCommand.MESSAGE_SUCCESS, expected);
    }

    @Test
    void executeInverse_throwsCommandException() {
        ListScheduleCommand command = new ListScheduleCommand();
        ModelManager planner = new ModelManager();
        assertThrows(CommandException.class, () -> command.executeInverse(planner));
    }

}
