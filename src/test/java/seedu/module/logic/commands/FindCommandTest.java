package seedu.module.logic.commands;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.commons.core.Messages;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.Module;
import seedu.module.model.module.predicate.ModuleCodeContainsKeywordsPredicate;
import seedu.module.testutil.ArchivedModuleBuilder;

public class FindCommandTest {
    private final String moduleCode = "CS2103T";
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_findModule_success() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList listOfArchivedModules = new ArchivedModuleList();
        listOfArchivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBook(listOfArchivedModules);
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);

        List<String> moduleKeyword = Arrays.asList("cs");

        List<Predicate<Module>> listOfPredicates =
                Arrays.asList(new ModuleCodeContainsKeywordsPredicate(moduleKeyword));

        CommandResult expectedCommandResult = new CommandResult(String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW,
                expectedModel.getFilteredArchivedModuleList().size()),
                false, false, false);
        FindCommand findCommand = new FindCommand(listOfPredicates);
        assertCommandSuccess(findCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_findModuleNoneFound_success() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ArchivedModuleList listOfArchivedModules = new ArchivedModuleList();
        listOfArchivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBook(listOfArchivedModules);
        model.setModuleBook(moduleBook);

        List<String> moduleKeyword = Arrays.asList("ma");

        List<Predicate<Module>> listOfPredicates =
                Arrays.asList(new ModuleCodeContainsKeywordsPredicate(moduleKeyword));

        CommandResult expectedCommandResult = new CommandResult(String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW,
                0),
                false, false, false);
        FindCommand findCommand = new FindCommand(listOfPredicates);
        assertCommandSuccess(findCommand, model, expectedCommandResult, expectedModel);
    }

}
