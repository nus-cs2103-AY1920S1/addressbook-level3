package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.module.Module;
import seedu.tarence.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteModuleCommandTest {

    public static final String VALID_MODCODE = "GET1029";
    public static final String VALID_MODCODE_ALT = "CS2040";
    public static final String VALID_TUTNAME = "WhyIsThisClassAt8am";
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_validModuleIndex_success() {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        Module moduleToDelete = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        model.addModule(moduleToDelete);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(outOfBoundIndex);

        assertCommandFailure(deleteModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    /* TODO: implement later?
    @Test
    public void execute_moduleWithTutorials_delayed() {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        Module moduleToDelete = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        Tutorial tutorial = new TutorialBuilder().withModCode(VALID_MODCODE).build();
        moduleToDelete.addTutorial(tutorial);
        model.addModule(moduleToDelete);

        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteModuleCommand.MESSAGE_CONFIRM_DELETE_NONEMPTY_MODULE,
                moduleToDelete,
                moduleToDelete.getTutorials().size());

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.storePendingCommand(deleteModuleCommand);

        assertCommandDelayed(deleteModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleNameDoesNotExist_throwsCommandException() {
        for (Module module : model.getFilteredModuleList()) {
            if (module.getModCode().toString().equals(VALID_MODCODE)) {
                model.deleteModule(module);
            }
        }
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(new ModCode(VALID_MODCODE));

        assertCommandFailure(deleteModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_IN_APPLICATION);
    }

    @Test
    public void equals() {
        DeleteModuleCommand deleteFirstCommand = new DeleteModuleCommand(INDEX_FIRST_IN_LIST);
        DeleteModuleCommand deleteSecondCommand = new DeleteModuleCommand(INDEX_SECOND_IN_LIST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteModuleCommand deleteFirstCommandCopy = new DeleteModuleCommand(INDEX_FIRST_IN_LIST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
