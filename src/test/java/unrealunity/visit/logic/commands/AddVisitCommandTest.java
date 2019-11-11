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
public class AddVisitCommandTest {

    private static final String VALID_DATE = "12/12/2019";
    private static final String VALID_DATE_2 = "11/12/2019";


    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddVisitCommand addVisitCommand = new AddVisitCommand(outOfBoundIndex, VALID_DATE);

        CommandTestUtil.assertCommandFailure(addVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        AddVisitCommand addVisitCommand = new AddVisitCommand(outOfBoundIndex, VALID_DATE);
        CommandTestUtil.assertCommandFailure(addVisitCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddVisitCommand standardCommand = new AddVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                VALID_DATE);

        // same values -> returns true
        AddVisitCommand commandWithSameValues = new AddVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                VALID_DATE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddVisitCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                VALID_DATE)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                VALID_DATE_2)));
    }
}
