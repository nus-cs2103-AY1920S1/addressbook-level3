package unrealunity.visit.logic.commands;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;
import unrealunity.visit.model.UserPrefs;
import unrealunity.visit.testutil.TypicalIndexes;
import unrealunity.visit.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddVisitCommand.
 */
public class EditVisitCommandTest {

    private static final int EMPTY_VISITLIST_INDEX = -1;
    private static final int VISITLIST_INDEX_1 = 1;
    private static final int INVALID_REPORT_INDEX = 0;


    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditVisitCommand editVisitCommand = new EditVisitCommand(outOfBoundIndex, EMPTY_VISITLIST_INDEX);

        CommandTestUtil.assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditVisitCommand editVisitCommand = new EditVisitCommand(outOfBoundIndex, EMPTY_VISITLIST_INDEX);
        CommandTestUtil.assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidReportIndex_failure() {

        EditVisitCommand editVisitCommand = new EditVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                INVALID_REPORT_INDEX);

        EditVisitCommand editVisitCommand1 = new EditVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                model.getFilteredPersonList()
                        .get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased()).getVisitList().getRecords().size() + 1);

        CommandTestUtil.assertCommandFailure(editVisitCommand, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
        CommandTestUtil.assertCommandFailure(editVisitCommand1, model, Messages.MESSAGE_INVALID_REPORT_INDEX);
    }

    @Test
    public void equals() {
        final EditVisitCommand standardCommand = new EditVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                EMPTY_VISITLIST_INDEX);

        // same values -> returns true
        EditVisitCommand commandWithSameValues = new EditVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                EMPTY_VISITLIST_INDEX);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new EditVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                EMPTY_VISITLIST_INDEX)));

        // different report remark -> returns false
        assertFalse(standardCommand.equals(new EditVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                VISITLIST_INDEX_1)));
    }
}
