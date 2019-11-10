package seedu.module.logic.commands.deadlinecommandtest;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.deadlinecommands.DeadlineCommand;
import seedu.module.logic.commands.deadlinecommands.DeleteDeadlineTaskCommand;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.TrackedModuleBuilder;

public class DeleteDeadlineTaskCommandTest {

    private final Deadline deadline = new Deadline("tutorial", "22/2/2019 1800", "HIGH");
    private Model model = new ModelManager();

    @Test
    public void execute_deleteInvalidModuleIndex_doesFailure() {
        Index outOfBoundModuleIndex = Index.fromOneBased(100);

        DeleteDeadlineTaskCommand deleteDeadlineCommand =
                new DeleteDeadlineTaskCommand(outOfBoundModuleIndex, 1);
        assertCommandFailure(deleteDeadlineCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteInvalidTaskIndex_doesFailure() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        model.addModule(trackedModule);
        trackedModule.addDeadline(deadline);

        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());

        DeleteDeadlineTaskCommand deleteDeadlineCommand =
                new DeleteDeadlineTaskCommand(moduleIndex, 100);
        assertCommandFailure(deleteDeadlineCommand, model, DeadlineCommand.MESSAGE_TASK_LIST_NUMBER_NOT_FOUND);
    }

}
