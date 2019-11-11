package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.logic.commands.DeleteTutorialCommand.MESSAGE_CONFIRM_DELETE_NONEMPTY_TUTORIAL;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTutorialCommandTest {

    public static final String VALID_MODCODE = "GET1029";
    public static final String VALID_MODCODE_ALT = "CS2040";
    public static final String SIMILAR_MODCODE = "GET1028";
    public static final String VALID_TUTNAME = "WhyIsThisClassAt8am";
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_validTutorialIndex_success() {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        model.addTutorial(tutorialToDelete);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete);

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTutorialIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(outOfBoundIndex);

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_similarTutorialModuleSuggested_promptSuggestionSelection() throws CommandException {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        Module similarModule = new ModuleBuilder().withModCode(SIMILAR_MODCODE).build();
        Tutorial validTutorial = new TutorialBuilder().withModCode(SIMILAR_MODCODE).withTutName(VALID_TUTNAME).build();
        similarModule.addTutorial(validTutorial);
        model.addModule(similarModule);

        String expectedMessage = String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial", VALID_MODCODE
                + " " + VALID_TUTNAME) + "1. " + SIMILAR_MODCODE + ", " + VALID_TUTNAME + "\n";

        CommandResult commandResult = new DeleteTutorialCommand(new ModCode(VALID_MODCODE),
                new TutName(VALID_TUTNAME)).execute(model);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validTutNameAndModCode_success() throws CommandException {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        Module moduleContainingTutorial = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        model.addTutorial(tutorialToDelete);
        model.addModule(moduleContainingTutorial);
        model.addTutorialToModule(tutorialToDelete);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new ModCode(VALID_MODCODE),
                new TutName(VALID_TUTNAME));

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete);

        assertEquals(new CommandResult(expectedMessage), deleteTutorialCommand.execute(model));
    }

    @Test
    public void execute_validTutNameOnly_success() throws CommandException {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        Module moduleContainingTutorial = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        model.addTutorial(tutorialToDelete);
        model.addModule(moduleContainingTutorial);
        model.addTutorialToModule(tutorialToDelete);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new TutName(VALID_TUTNAME));

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete);

        assertEquals(new CommandResult(expectedMessage), deleteTutorialCommand.execute(model));
    }

    @Test
    public void execute_multipleTutorialsOfName_throwsCommandException() {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialOne = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        Tutorial tutorialTwo = new TutorialBuilder().withModCode(SIMILAR_MODCODE).withTutName(VALID_TUTNAME).build();
        model.addTutorial(tutorialOne);
        model.addTutorial(tutorialTwo);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new TutName(VALID_TUTNAME));

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_TUTORIAL_MULTIPLE, () -> deleteTutorialCommand.execute(model));
    }

    @Test
    public void execute_nonEmptyTutorial_showsConfirmationPrompt() throws CommandException {
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTNAME).build();
        Module moduleContainingTutorial = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        Student studentInTutorial = new StudentBuilder().withTutName(VALID_TUTNAME).withModCode(VALID_MODCODE).build();
        model.addTutorial(tutorialToDelete);
        model.addModule(moduleContainingTutorial);
        model.addStudent(studentInTutorial);
        model.addTutorialToModule(tutorialToDelete);
        model.addStudentToTutorial(studentInTutorial);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(new TutName(VALID_TUTNAME));

        String expectedMessage = String.format(MESSAGE_CONFIRM_DELETE_NONEMPTY_TUTORIAL,
                tutorialToDelete,
                tutorialToDelete.getStudents().size());

        assertEquals(new CommandResult(expectedMessage), deleteTutorialCommand.execute(model));
    }

    /* TODO: implement later?
    @Test
    public void execute_moduleWithTutorials_delayed() {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Module module = new ModuleBuilder().withModCode(VALID_MODCODE).build();
        Tutorial tutorialToDelete = new TutorialBuilder().withModCode(VALID_MODCODE).build();
        Student student = new StudentBuilder().withModCode(VALID_MODCODE)
                .withTutName(tutorialToDelete.getTutName().tutName).build();
        module.addTutorial(tutorialToDelete);
        tutorialToDelete.addStudent(student);
        model.addModule(module);
        model.addTutorial(tutorialToDelete);

        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_CONFIRM_DELETE_NONEMPTY_TUTORIAL,
                tutorialToDelete,
                tutorialToDelete.getStudents().size());

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.storePendingCommand(deleteTutorialCommand);

        assertCommandDelayed(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

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

    */

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
