package seedu.module.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.module.logic.commands.ViewCommand.MESSAGE_VIEW_MODULE_SUCCESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class ViewCommandTest {
    private final String moduleCode = "CS2103T";
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_viewArchivedModule_success() {
        // TODO: EXTRACT TO UTILS
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList archivedModules = new ArchivedModuleList();
        archivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBook(archivedModules);
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        expectedModel.setDisplayedModule(archivedModule);

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_VIEW_MODULE_SUCCESS, moduleCode),
            false, true, false);
        assertCommandSuccess(new ViewCommand(moduleCode), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewTrackedModule_success() {
        // TODO: EXTRACT TO UTILS
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList archivedModules = new ArchivedModuleList();
        archivedModules.add(archivedModule);
        TrackedModule trackedModule = new TrackedModule(archivedModule);
        ModuleBook moduleBook = new ModuleBook(archivedModules);
        moduleBook.addModule(trackedModule);
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        expectedModel.setDisplayedModule(trackedModule);

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_VIEW_MODULE_SUCCESS, moduleCode),
            false, true, false);
        assertCommandSuccess(new ViewCommand(moduleCode), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewArchivedModule_failure() {
        assertThrows(CommandException.class, () -> new ViewCommand(moduleCode).execute(new ModelManager()));
    }

}
