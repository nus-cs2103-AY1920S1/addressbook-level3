package seedu.module.logic.commands;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.module.logic.commands.DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS;
import static seedu.module.logic.commands.DeleteCommand.MESSAGE_MODULE_NOT_FOUND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.SameModuleCodePredicate;
import seedu.module.testutil.TrackedModuleBuilder;

public class DeleteCommandTest {
    private final String moduleCode = "CS2103T";
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_deleteValidModule_success() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        model.addModule(trackedModule);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, trackedModule),
                        false, false, false);
        DeleteCommand deleteCommand = new DeleteCommand(new SameModuleCodePredicate(moduleCode));
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_deleteLowerCaseModule_success() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        model.addModule(trackedModule);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, trackedModule),
                        false, false, false);
        DeleteCommand deleteCommand = new DeleteCommand(new SameModuleCodePredicate(moduleCode.toLowerCase()));
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_deleteInvalidModule_throwsCommandException() {
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBook();
        model.setModuleBook(moduleBook);
        model.addModule(trackedModule);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, trackedModule),
                        false, false, false);
        DeleteCommand deleteCommand = new DeleteCommand(new SameModuleCodePredicate("INVALID"));
        assertCommandFailure(deleteCommand, model, MESSAGE_MODULE_NOT_FOUND);
    }

}
