package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddVisitCommand.
 */
public class EditVisitCommandTest {

    private static final int EMPTY_VISITLIST_INDEX = -1;
    private static final int VISITLIST_INDEX_1 = 1;
    private static final int INVALID_REPORT_INDEX = 0;


    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditVisitCommand editVisitCommand = new EditVisitCommand(outOfBoundIndex, EMPTY_VISITLIST_INDEX);

        assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditVisitCommand editVisitCommand = new EditVisitCommand(outOfBoundIndex, EMPTY_VISITLIST_INDEX);
        assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_InvalidReportIndex_failure() {

        EditVisitCommand editVisitCommand = new EditVisitCommand(INDEX_SECOND_PERSON,
                INVALID_REPORT_INDEX);

        EditVisitCommand editVisitCommand1 = new EditVisitCommand(INDEX_SECOND_PERSON,
                model.getFilteredPersonList()
                        .get(INDEX_SECOND_PERSON.getZeroBased()).getVisitList().getRecords().size() + 1);

        assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
        assertCommandFailure(editVisitCommand1, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
    }

    @Test
    public void equals() {
        final EditVisitCommand standardCommand = new EditVisitCommand(INDEX_FIRST_PERSON,
                EMPTY_VISITLIST_INDEX);

        // same values -> returns true
        EditVisitCommand commandWithSameValues = new EditVisitCommand(INDEX_FIRST_PERSON,
                EMPTY_VISITLIST_INDEX);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new EditVisitCommand(INDEX_SECOND_PERSON,
                EMPTY_VISITLIST_INDEX)));

        // different report remark -> returns false
        assertFalse(standardCommand.equals(new EditVisitCommand(INDEX_FIRST_PERSON,
                VISITLIST_INDEX_1)));
    }
}
