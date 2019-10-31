package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.showIntervieweeWithName;
import static seedu.scheduler.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_NAME;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_PHONE;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_TAGS;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.testutil.IntervieweeBuilder;
import seedu.scheduler.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests.
 */
class EditIntervieweeCommandTest {

    // ============================================== UNIT TESTS =================================================

    @Test
    public void constructor_nullFieldsInput_failure() {
        assertThrows(NullPointerException.class, () ->
                new EditIntervieweeCommand(new Name("Alice"), null));
        assertThrows(NullPointerException.class, () ->
                new EditIntervieweeCommand(null, new EditIntervieweeCommand.EditIntervieweeDescriptor()));
        assertThrows(NullPointerException.class, () ->
                new EditIntervieweeCommand(null, null));
    }

    @Test
    public void equals() {

        final EditIntervieweeDescriptor aliceDescriptor = TestUtil.getDescriptorFromInterviewee(ALICE_INTERVIEWEE);
        final EditIntervieweeCommand standardCommand =
                new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), aliceDescriptor);

        EditIntervieweeDescriptor copyDescriptor = new EditIntervieweeDescriptor(aliceDescriptor);
        EditIntervieweeCommand commandWithSameValues =
                new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), copyDescriptor);
        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(
                new EditIntervieweeCommand(BOB_INTERVIEWEE_MANUAL.getName(), aliceDescriptor)));

        // different descriptor -> returns false
        final EditIntervieweeDescriptor bobDescriptor = TestUtil.getDescriptorFromInterviewee(BOB_INTERVIEWEE_MANUAL);
        assertFalse(standardCommand.equals(new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), bobDescriptor)));
    }

    // ======================================== INTEGRATION TESTS ================================================

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // completely changes alice_interviewee into a different interviewee (name, phone... all changed)
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee editedInterviewee = new Interviewee.IntervieweeBuilder(DEFAULT_NAME, DEFAULT_PHONE, DEFAULT_TAGS)
                .build();
        EditIntervieweeDescriptor descriptor = TestUtil.getDescriptorFromInterviewee(editedInterviewee);
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedInterviewee);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        expectedModel.setInterviewee(ALICE_INTERVIEWEE, editedInterviewee);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // doesn't change alice_interviewees identity (name, phone) except non-identity details (faculty, email)
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewee editedInterviewee = new IntervieweeBuilder(ALICE_INTERVIEWEE)
                .withFaculty("School of Engineering")
                .withPersonalEmail("alicealice@gmail.com")
                .build();
        EditIntervieweeDescriptor descriptor = TestUtil.getDescriptorFromInterviewee(editedInterviewee);
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedInterviewee);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        expectedModel.setInterviewee(ALICE_INTERVIEWEE, editedInterviewee);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_success() {
        // empty descriptor
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, ALICE_INTERVIEWEE);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

    }

    /**
     * Edit interviewee into an interviewee already existing in the unfiltered list (by isSamePerson check).
     */
    @Test
    public void execute_duplicateIntervieweeUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EditIntervieweeDescriptor descriptor = TestUtil.getDescriptorFromInterviewee(BENSON_INTERVIEWEE);
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateIntervieweeFilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        // filter the list to show all interviewees
        model.updateFilteredIntervieweeList(Model.PREDICATE_SHOW_ALL_INTERVIEWEES);

        EditIntervieweeDescriptor descriptor = TestUtil.getDescriptorFromInterviewee(BENSON_INTERVIEWEE);
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Interviewee name does not exist in the unfiltered list.
     */
    @Test
    public void execute_invalidIntervieweeNameUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Name invalidName = new Name("This name doesnt exist in the list");
        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(invalidName, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    /**
     * Filtered list doesn't contain the interviewee name, but Unfiltered list does.
     */
    @Test
    public void execute_invalidIntervieweeNameFilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        // name is in the unfiltered list, but not in the filtered list.
        showIntervieweeWithName(model, ALICE_INTERVIEWEE.getName());

        // ensure alice is in the unfiltered list
        assertTrue(model.hasInterviewee(ALICE_INTERVIEWEE));

        // filter the list to exclude alice
        model.updateFilteredIntervieweeList(interviewee -> !interviewee.isSamePerson(ALICE_INTERVIEWEE));

        EditIntervieweeCommand editCommand = new EditIntervieweeCommand(ALICE_INTERVIEWEE.getName(),
                new EditIntervieweeDescriptor());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }
}
