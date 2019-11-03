package seedu.module.logic.commands;

import static seedu.module.logic.commands.AddCommand.MESSAGE_DUPLICATE_MODULE;
import static seedu.module.logic.commands.AddCommand.MESSAGE_MODULE_NOT_FOUND;
import static seedu.module.logic.commands.AddCommand.MESSAGE_SUCCESS;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.predicate.SameModuleCodePredicate;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ArchivedModuleListBuilder;
import seedu.module.testutil.ModuleBookBuilder;

public class AddCommandTest {
    private final String moduleCode = "CS2103T";
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_addValidModule_success() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBookBuilder().withArchivedModules(
            new ArchivedModuleListBuilder().withArchivedModule(archivedModule).build())
            .build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);

        TrackedModule trackedModule = new TrackedModule(archivedModule);
        expectedModel.addModule(trackedModule);
        expectedModel.showAllTrackedModules();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, trackedModule),
                false, false, false);
        AddCommand addCommand = new AddCommand(new SameModuleCodePredicate(moduleCode));
        assertCommandSuccess(addCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_addLowerCaseModule_success() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBookBuilder().withArchivedModules(
            new ArchivedModuleListBuilder().withArchivedModule(archivedModule).build())
            .build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);

        TrackedModule trackedModule = new TrackedModule(archivedModule);
        expectedModel.addModule(trackedModule);
        expectedModel.showAllTrackedModules();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, trackedModule),
                false, false, false);
        AddCommand addCommand = new AddCommand(new SameModuleCodePredicate(moduleCode.toLowerCase()));
        assertCommandSuccess(addCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_addInvalidModule_throwsCommandException() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList listOfArchivedModules = new ArchivedModuleList();
        listOfArchivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBook(listOfArchivedModules);
        model.setModuleBook(moduleBook);

        AddCommand addCommand = new AddCommand(new SameModuleCodePredicate("INVALID"));
        assertCommandFailure(addCommand, model, MESSAGE_MODULE_NOT_FOUND);
    }

    @Test
    public void execute_addDuplicateModule_throwsCommandException() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList listOfArchivedModules = new ArchivedModuleList();
        listOfArchivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBook(listOfArchivedModules);
        model.setModuleBook(moduleBook);
        TrackedModule trackedModule = new TrackedModule(archivedModule);
        model.addModule(trackedModule);

        AddCommand addCommand = new AddCommand(new SameModuleCodePredicate(archivedModule.getModuleCode()));
        assertCommandFailure(addCommand, model, MESSAGE_DUPLICATE_MODULE);
    }

}
