package seedu.module.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ModuleBookBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_trackedModuleListIsEmpty_success() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_trackedModuleListIsFiltered_showsEverything() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        TrackedModule trackedModule = new TrackedModuleBuilder().withModule(archivedModule).build();
        ModuleBook moduleBook = new ModuleBookBuilder().withTrackedModule(trackedModule).build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);

        // Sets the filtered list to show no module. List command should set it to show all.
        model.updateFilteredModuleList(unused -> false);

        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getFilteredModuleList(), expectedModel.getFilteredModuleList());
    }
}
