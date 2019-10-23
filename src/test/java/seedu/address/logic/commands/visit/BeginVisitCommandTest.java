package seedu.address.logic.commands.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.VisitTaskUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datetime.EndDateTime;
import seedu.address.model.datetime.StartDateTime;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Remark;
import seedu.address.model.visit.Visit;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BeginVisitCommand}.
 */
public class BeginVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BeginVisitCommand command = new BeginVisitCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_valid_success() {
        Person personToVisit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BeginVisitCommand command = new BeginVisitCommand(INDEX_FIRST_PERSON);

        Model expectedModel = new ModelManager(model.getStagedAddressBook(), new UserPrefs());
        Person expectedPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        try {
            CommandResult result = command.execute(model);

            //Need to test it this way because it uses the new Date(). The time must be exactly the same.
            StartDateTime now = model.getOngoingVisit().get().getStartDateTime();
            Visit visit = new Visit(
                    new Remark(""),
                    now,
                    EndDateTime.UNFINISHED_VISIT_END_DATE_TIME,
                    VisitTaskUtil.listFromPatient(expectedPerson), expectedPerson);

            expectedPerson.addVisit(visit);
            expectedModel.setNewOngoingVisit(visit);
            String expectedMessage = String.format(BeginVisitCommand.MESSAGE_START_VISIT_SUCCESS, expectedPerson);

            assertEquals(expectedMessage, result);
            assertEquals(expectedModel, model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void equals() {
        BeginVisitCommand firstCommand = new BeginVisitCommand(INDEX_FIRST_PERSON);
        BeginVisitCommand secondCommand = new BeginVisitCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        BeginVisitCommand firstCommandCopy = new BeginVisitCommand(INDEX_FIRST_PERSON);
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns false
        assertNotEquals(firstCommand, 1);

        // null -> returns false
        assertNotEquals(firstCommand, null);

        // different person -> returns false
        assertNotEquals(firstCommand, secondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
