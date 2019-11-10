package seedu.module.logic.commands.deadlinecommandtest;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.commons.core.Messages;
import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.deadlinecommands.AddDeadlineCommand;
import seedu.module.logic.commands.deadlinecommands.DeadlineCommand;
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

public class AddDeadlineCommandTest {

    private final Deadline deadline = new Deadline("tutorial", "22/2/2019 1800", "HIGH");
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_addValidDeadline_success() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBookBuilder().withArchivedModules(
                new ArchivedModuleListBuilder().withArchivedModule(archivedModule).build())
                .build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        model.addModule(trackedModule);
        model.setDisplayedModule(trackedModule);
        TrackedModule expectedModule = new TrackedModuleBuilder()
                .withDeadline(new ArrayList<>(Arrays.asList(deadline))).build();
        expectedModel.addModule(expectedModule);
        expectedModel.setDisplayedModule(expectedModule);
        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());
        AddDeadlineCommand command = new AddDeadlineCommand(moduleIndex, deadline);

        String message = String.format(AddDeadlineCommand.MESSAGE_ADD_DEADLINE_SUCCESS,
                trackedModule.getModuleCode() + " " + trackedModule.getTitle());
        CommandResult expectedResult = new CommandResult(message,
                false, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_addInvalidModuleIndex_doesFailure() {
        Index outOfBoundTaskIndex = Index.fromOneBased(100);

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(outOfBoundTaskIndex, deadline);
        assertCommandFailure(addDeadlineCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateTasks_addsFailure() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        model.addModule(trackedModule);
        trackedModule.addDeadline(deadline);

        Index moduleIndex = Index.fromOneBased(model.getFilteredModuleList().size());

        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(moduleIndex, deadline);
        assertCommandFailure(addDeadlineCommand, model, DeadlineCommand.MESSAGE_DUPLICATE_DEADLINE);
    }
}
