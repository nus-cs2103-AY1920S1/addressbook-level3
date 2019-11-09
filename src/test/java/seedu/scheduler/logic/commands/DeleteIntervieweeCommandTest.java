package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertModelHasInterviewee;
import static seedu.scheduler.logic.commands.CommandTestUtil.showIntervieweeWithName;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.testutil.TypicalPersons;

class DeleteIntervieweeCommandTest {

    @Test
    public void execute_validIntervieweeUnfilteredList_success() {
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee alice = TypicalPersons.ALICE_INTERVIEWEE;
        Interviewee intervieweeToDel = model.getInterviewee(alice.getName().fullName);

        DeleteCommand deleteCommand = new DeleteIntervieweeCommand(alice.getName());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, intervieweeToDel);

        // create duplicate model and remove interviewee
        ModelManager expectedModel = new ModelManager(model.getMutableIntervieweeList(),
                model.getMutableInterviewerList(), new UserPrefs(), new LinkedList<>());
        expectedModel.deleteInterviewee(intervieweeToDel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        DeleteCommand deleteCommand = new DeleteIntervieweeCommand(new Name("Name doesnt exist"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());

        showIntervieweeWithName(model, ALICE_INTERVIEWEE.getName());

        Name invalidName = new Name("This name doesnt exist in the interviewee book");

        DeleteCommand deleteCommand = new DeleteIntervieweeCommand(invalidName);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        // pre-processing
        Model model = new ModelManager(getTypicalIntervieweeList(), new InterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee toDelete = ALICE_INTERVIEWEE;
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, toDelete);

        assertModelHasInterviewee(model, toDelete);
        showIntervieweeWithName(model, ALICE_INTERVIEWEE.getName());

        DeleteCommand deleteCommand = new DeleteIntervieweeCommand(toDelete.getName());

        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel.deleteInterviewee(toDelete);

        showAllInterviewees(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteAlice = new DeleteIntervieweeCommand(ALICE_INTERVIEWEE.getName());
        DeleteCommand deleteBenson = new DeleteIntervieweeCommand(BENSON_INTERVIEWEE.getName());

        // same object -> returns true
        assertTrue(deleteAlice.equals(deleteAlice));

        // same values -> returns true
        DeleteCommand deleteAliceCopy = new DeleteIntervieweeCommand(ALICE_INTERVIEWEE.getName());
        assertTrue(deleteAlice.equals(deleteAliceCopy));

        // different types -> returns false
        assertFalse(deleteAlice.equals(1));

        // null -> returns false
        assertFalse(deleteAlice.equals(null));

        // different person -> returns false
        assertFalse(deleteAlice.equals(deleteBenson));
    }

    /**
     * Updates {@code model}'s filtered interviewee list to show no one.
     */
    private void showNoInterviewee(Model model) {
        model.updateFilteredIntervieweeList(p -> false);
        assertTrue(model.getFilteredIntervieweeList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered interviewee list to show everyone.
     */
    private void showAllInterviewees(Model model) {
        model.updateFilteredIntervieweeList(p -> true);
    }
}
