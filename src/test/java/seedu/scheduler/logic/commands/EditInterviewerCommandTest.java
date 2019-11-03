package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.showInterviewerWithName;
import static seedu.scheduler.logic.commands.EditInterviewerCommand.EditInterviewerDescriptor;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_NAME;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_PHONE;
import static seedu.scheduler.model.person.DefaultValues.DEFAULT_TAGS;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.testutil.InterviewerBuilder;
import seedu.scheduler.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests.
 */
class EditInterviewerCommandTest {

    // ============================================== UNIT TESTS =================================================

    @Test
    public void constructor_nullFieldsInput_failure() {
        assertThrows(NullPointerException.class, () ->
                new EditInterviewerCommand(new Name("Alice"), null));
        assertThrows(NullPointerException.class, () ->
                new EditInterviewerCommand(null, new EditInterviewerDescriptor()));
        assertThrows(NullPointerException.class, () ->
                new EditInterviewerCommand(null, null));
    }

    @Test
    public void equals() {
        final EditInterviewerDescriptor aliceDescriptor = TestUtil.getDescriptorFromInterviewer(ALICE_INTERVIEWER);
        final EditInterviewerCommand standardCommand =
                new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), aliceDescriptor);

        EditInterviewerDescriptor copyDescriptor = new EditInterviewerDescriptor(aliceDescriptor);
        EditInterviewerCommand commandWithSameValues =
                new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), copyDescriptor);
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
                new EditInterviewerCommand(BENSON_INTERVIEWER.getName(), aliceDescriptor)));

        // different descriptor -> returns false
        final EditInterviewerDescriptor bobDescriptor = TestUtil.getDescriptorFromInterviewer(BENSON_INTERVIEWER);
        assertFalse(standardCommand.equals(new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), bobDescriptor)));
    }

    // ======================================== INTEGRATION TESTS ================================================

    // Complete descriptor
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // completely changes alice_interviewer into a different interviewee (name, phone... all changed)
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewer editedInterviewer = new Interviewer.InterviewerBuilder(DEFAULT_NAME, DEFAULT_PHONE, DEFAULT_TAGS)
                .build();
        EditInterviewerDescriptor descriptor = TestUtil.getDescriptorFromInterviewer(editedInterviewer);
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedInterviewer);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        expectedModel.setInterviewer(ALICE_INTERVIEWER, editedInterviewer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // Partial descriptor
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // doesn't change alice_interviewees identity (name, phone) except non-identity details (faculty, email)
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        Interviewer editedInterviewer = new InterviewerBuilder(ALICE_INTERVIEWER)
                .withEmail("alicealice@gmail.com")
                .withDepartment("Logistics")
                .build();

        EditInterviewerDescriptor descriptor = TestUtil.getDescriptorFromInterviewer(editedInterviewer);
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedInterviewer);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        expectedModel.setInterviewer(ALICE_INTERVIEWER, editedInterviewer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // Empty descriptor
    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_success() {
        // empty descriptor
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, ALICE_INTERVIEWER);

        Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // Editing interviewer to another interviewer with same name in the interviewer list
    @Test
    public void execute_duplicateInterviewerUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EditInterviewerDescriptor descriptor = TestUtil.getDescriptorFromInterviewer(BENSON_INTERVIEWER);
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateInterviewerFilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        // filter the list to show all interviewees
        model.updateFilteredInterviewerList(Model.PREDICATE_SHOW_ALL_INTERVIEWERS);

        EditInterviewerDescriptor descriptor = TestUtil.getDescriptorFromInterviewer(BENSON_INTERVIEWER);
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    // Interviewer name doesn't exist in interviewer list
    @Test
    public void execute_invalidInterviewerNameUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EditInterviewerDescriptor descriptor = TestUtil.getDescriptorFromInterviewer(BENSON_INTERVIEWER);
        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    // Interviewer doesn't exist in filtered list, only unfiltered list.
    @Test
    public void execute_invalidInterviewerNameFilteredList_failure() {
        Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        // name is in the unfiltered list, but not in the filtered list.
        showInterviewerWithName(model, ALICE_INTERVIEWER.getName());

        // ensure alice is in the unfiltered list
        assertTrue(model.hasInterviewer(ALICE_INTERVIEWER));

        // filter the list to exclude alice
        model.updateFilteredInterviewerList(interviewer -> !interviewer.isSamePerson(ALICE_INTERVIEWER));

        EditInterviewerCommand editCommand = new EditInterviewerCommand(ALICE_INTERVIEWER.getName(),
                new EditInterviewerDescriptor());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }
}
