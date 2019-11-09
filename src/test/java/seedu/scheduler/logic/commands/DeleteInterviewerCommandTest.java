package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertModelHasInterviewer;
import static seedu.scheduler.logic.commands.CommandTestUtil.showInterviewerWithName;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.testutil.TypicalPersons;

class DeleteInterviewerCommandTest {

    @Test
    public void execute_validInterviewerUnfilteredList_success() {
        Model model = new ModelManager(new IntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewer alice = TypicalPersons.ALICE_INTERVIEWER;
        Interviewer interviewerToDel = model.getInterviewer(alice.getName().fullName);

        DeleteCommand deleteCommand = new DeleteInterviewerCommand(alice.getName());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, interviewerToDel);

        // create duplicate model and remove interviewer
        ModelManager expectedModel = new ModelManager(model.getMutableIntervieweeList(),
                model.getMutableInterviewerList(), new UserPrefs(), new LinkedList<>());
        expectedModel.deleteInterviewer(interviewerToDel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        DeleteCommand deleteCommand = new DeleteInterviewerCommand(new Name("Name doesnt exist"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Model model = new ModelManager(new IntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        showInterviewerWithName(model, ALICE_INTERVIEWER.getName());

        Name invalidName = new Name("This name doesnt exist in the interviewee book");

        DeleteCommand deleteCommand = new DeleteInterviewerCommand(invalidName);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        // pre-processing
        Model model = new ModelManager(new IntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewer toDelete = ALICE_INTERVIEWER;
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, toDelete);

        assertModelHasInterviewer(model, toDelete);
        showInterviewerWithName(model, ALICE_INTERVIEWER.getName());

        DeleteCommand deleteCommand = new DeleteInterviewerCommand(toDelete.getName());

        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel.deleteInterviewer(toDelete);

        showAllInterviewers(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteAlice = new DeleteInterviewerCommand(ALICE_INTERVIEWER.getName());
        DeleteCommand deleteBenson = new DeleteInterviewerCommand(BENSON_INTERVIEWER.getName());

        // same object -> returns true
        assertTrue(deleteAlice.equals(deleteAlice));

        // same values -> returns true
        DeleteCommand deleteAliceCopy = new DeleteInterviewerCommand(ALICE_INTERVIEWER.getName());
        assertTrue(deleteAlice.equals(deleteAliceCopy));

        // different types -> returns false
        assertFalse(deleteAlice.equals(1));

        // null -> returns false
        assertFalse(deleteAlice.equals(null));

        // different person -> returns false
        assertFalse(deleteAlice.equals(deleteBenson));
    }

    /**
     * Updates {@code model}'s filtered interviewer list to show no one.
     */
    private void showNoInterviewer(Model model) {
        model.updateFilteredInterviewerList(p -> false);

        assertTrue(model.getFilteredInterviewerList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered interviewer list to show everyone.
     */
    private void showAllInterviewers(Model model) {
        model.updateFilteredInterviewerList(p -> true);
    }
}
