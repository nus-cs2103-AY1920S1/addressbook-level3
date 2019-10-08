package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTutorialCommandTest {

    public static final String VALID_MODCODE = "GET1029";
    public static final String VALID_MODCODE_ALT = "CS2040";
    public static final String VALID_TUTNAME = "WhyIsThisClassAt8am";
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        model.addTutorial(tutorialToDelete);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete);

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(outOfBoundIndex);

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
    }

    /* TODO: implement later?
    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_IN_LIST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_IN_LIST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_IN_LIST);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getApplication().getPersonList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    */

    @Test
    public void execute_multipleTutorialsOfSameName_throwsCommandException() {
        Tutorial tutorialOne = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        Tutorial tutorialTwo = new TutorialBuilder().withModCode(VALID_MODCODE_ALT).withTutName(VALID_TUTNAME).build();
        model.addTutorial(tutorialOne);
        model.addTutorial(tutorialTwo);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new TutName(VALID_TUTNAME));

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_MULTIPLE);
    }

    @Test
    public void execute_tutorialNameDoesNotExist_throwsCommandException() {
        for (Tutorial tutorial : model.getFilteredTutorialList()) {
            if (tutorial.getTutName().toString().equals(VALID_TUTNAME)) {
                model.deleteTutorial(tutorial);
            }
        }
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new TutName(VALID_TUTNAME));

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_IN_APPLICATION);
    }

    @Test
    public void equals() {
        DeleteTutorialCommand deleteFirstCommand = new DeleteTutorialCommand(INDEX_FIRST_IN_LIST);
        DeleteTutorialCommand deleteSecondCommand = new DeleteTutorialCommand(INDEX_SECOND_IN_LIST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTutorialCommand deleteFirstCommandCopy = new DeleteTutorialCommand(INDEX_FIRST_IN_LIST);
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
