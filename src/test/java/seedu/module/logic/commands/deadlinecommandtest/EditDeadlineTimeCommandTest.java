package seedu.module.logic.commands.deadlinecommandtest;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.deadlinecommands.DeadlineCommand;
import seedu.module.logic.commands.deadlinecommands.EditDeadlineDescCommand;
import seedu.module.logic.commands.deadlinecommands.EditDeadlineTimeCommand;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ArchivedModuleListBuilder;
import seedu.module.testutil.ModuleBookBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class EditDeadlineTimeCommandTest {

    private final Deadline deadline = new Deadline("tutorial", "22/2/2019 1800", "HIGH");
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_editValidDeadlineDescCommand_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBookBuilder().withArchivedModules(
                new ArchivedModuleListBuilder().withArchivedModule(archivedModule).build())
                .build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        TrackedModule trackedModule = new TrackedModuleBuilder()
                .withDeadline(new ArrayList<>(Arrays.asList(deadline))).build();
        model.addModule(trackedModule);
        model.setDisplayedModule(trackedModule);

        Deadline editedDeadline = new Deadline("tutorial 2", "12/10/2019 2359", "HIGH");
        TrackedModule expectedModule = new TrackedModuleBuilder()
                .withDeadline(new ArrayList<>(Arrays.asList(editedDeadline))).build();
        expectedModel.addModule(expectedModule);
        expectedModel.setDisplayedModule(expectedModule);
        String message = String.format(EditDeadlineDescCommand.MESSAGE_EDIT_DEADLINE_SUCCESS,
                expectedModule.getModuleCode() + " "
                        + expectedModule.getTitle());
        CommandResult expectedResult = new CommandResult(message,
                false, true, false);
        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());

        EditDeadlineTimeCommand command = new EditDeadlineTimeCommand(moduleIndex,
                "3/2/2019 1700", 1);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_editInvalidModuleIndex_doesFailure() {
        Index outOfBoundModuleIndex = Index.fromOneBased(100);

        EditDeadlineTimeCommand editDeadlineTimeCommand =
                new EditDeadlineTimeCommand(outOfBoundModuleIndex, "22/12/2019 1800", 2);
        assertCommandFailure(editDeadlineTimeCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editInvalidTaskIndex_doesFailure() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        model.addModule(trackedModule);
        trackedModule.addDeadline(deadline);

        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());

        EditDeadlineTimeCommand editDeadlineTimeCommand =
                new EditDeadlineTimeCommand(moduleIndex, "22/12/2019 1800", 100);
        assertCommandFailure(editDeadlineTimeCommand, model, DeadlineCommand.MESSAGE_TASK_LIST_NUMBER_NOT_FOUND);
    }
}
