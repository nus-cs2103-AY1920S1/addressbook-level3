package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;

class AddTaskCommandTest {
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_invalidProblemIndex_failure() {
        Index planIndex = Index.fromOneBased(model.getFilteredProblemList().size());
        Index outOfBoundProblemIndex = Index.fromOneBased(model.getFilteredProblemList().size() + 1);
        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, outOfBoundProblemIndex);
        AddTaskCommand addTaskCommand = new AddTaskCommand(descriptor);
        assertThrows(CommandException.class, () -> addTaskCommand.execute(model));
    }
}
