package seedu.module.logic.commands;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.module.logic.commands.ViewCommand.MESSAGE_MODULE_NOT_FOUND;
import static seedu.module.logic.commands.ViewCommand.MESSAGE_VIEW_MODULE_SUCCESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ModuleBookBuilder;

public class ViewCommandTest {
    private Model model;
    private Model expectedModel;
    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();

    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        // Initialize and sets the modulebook with the "CS2103T" archived module.
        ModuleBook moduleBook = new ModuleBookBuilder().build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
    }

    @Test
    public void execute_viewArchivedModule_success() {
        expectedModel.setDisplayedModule(archivedModule);

        CommandResult expectedCommandResult = new CommandResult(
            String.format(MESSAGE_VIEW_MODULE_SUCCESS, archivedModule.getModuleCode()),
            false, true, false);

        assertCommandSuccess(new ViewCommand(archivedModule.getModuleCode()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewTrackedModule_success() {
        TrackedModule trackedModule = new TrackedModule(archivedModule);
        model.addModule(trackedModule);
        expectedModel.addModule(trackedModule);
        expectedModel.setDisplayedModule(trackedModule);

        CommandResult expectedCommandResult = new CommandResult(
            String.format(MESSAGE_VIEW_MODULE_SUCCESS, archivedModule.getModuleCode()),
            false, true, false);

        assertCommandSuccess(new ViewCommand(archivedModule.getModuleCode()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewArchivedModule_failure() {
        assertCommandFailure(new ViewCommand("CS9999"), model, MESSAGE_MODULE_NOT_FOUND);
    }
}
