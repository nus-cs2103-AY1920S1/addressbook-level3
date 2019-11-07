package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.model.planner.predicates.TaskTypeMatchesTypePredicate;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;


class PullTaskCommandTest {

    @Test
    void getCommandWord() {
        Predicate<Task> predicate = new TaskTypeMatchesTypePredicate(TaskType.DEADLINE);
        String expected = "pull-task";
        PullTaskCommand command = new PullTaskCommand(predicate);

        assertEquals(expected, command.getCommandWord());
    }

    @Test
    void hasInverseExecution() {
        Predicate<Task> predicate = new TaskTypeMatchesTypePredicate(TaskType.DEADLINE);
        PullTaskCommand command = new PullTaskCommand(predicate);

        assertFalse(command.hasInverseExecution());
    }

    @Test
    void execute() {
        Predicate<Task> predicate = new TaskTypeMatchesTypePredicate(TaskType.TODO);
        PullTaskCommand command = new PullTaskCommand(predicate);

        Model model = new ModelManager();
        Model expected = new ModelManager();

        model.addTask(new Todo("test"));
        expected.addTask(new Todo("test"));
        expected.updateFilteredTaskList(predicate);

        String expectedString = String.format(PullTaskCommand.MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        assertCommandSuccess(command, model, expectedString, expected);
    }

    @Test
    void executeInverse_throwsCommandException() {
        Predicate<Task> predicate = new TaskTypeMatchesTypePredicate(TaskType.DEADLINE);
        PullTaskCommand command = new PullTaskCommand(predicate);

        Model model = new ModelManager();

        assertThrows(CommandException.class, () -> command.executeInverse(model));
    }
}
