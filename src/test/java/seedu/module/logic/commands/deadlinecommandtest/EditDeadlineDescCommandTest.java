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

public class EditDeadlineDescCommandTest {

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

        Deadline editedDeadline = new Deadline("tutorial 2", "22/2/2019 1600", "HIGH");
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

        EditDeadlineDescCommand command = new EditDeadlineDescCommand(moduleIndex,
                "tutorial 3", 1);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_editInvalidModuleIndex_doesFailure() {
        Index outOfBoundModuleIndex = Index.fromOneBased(100);

        EditDeadlineDescCommand editDeadlineDescCommand =
                new EditDeadlineDescCommand(outOfBoundModuleIndex, "description", 2);
        assertCommandFailure(editDeadlineDescCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editInvalidTaskIndex_doesFailure() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        model.addModule(trackedModule);
        trackedModule.addDeadline(deadline);

        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());

        EditDeadlineDescCommand editDeadlineDescCommand =
                new EditDeadlineDescCommand(moduleIndex, "description", 100);
        assertCommandFailure(editDeadlineDescCommand, model, DeadlineCommand.MESSAGE_TASK_LIST_NUMBER_NOT_FOUND);
    }
}
