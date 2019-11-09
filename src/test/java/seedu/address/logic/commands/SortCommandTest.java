package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Filters.FIRST_FILTER;
import static seedu.address.testutil.Filters.SECOND_FILTER;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SortFilter;
import seedu.address.model.UserPrefs;

class SortCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    //    @Test
    //    public void execute_validFilter() throws CommandException {
    //        SortFilter validFilter = new SortFilter("name", 1);
    //        CommandResult commandResult = new SortCommand(validFilter).execute(model);
    //        assertEquals("Contacts List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    //    }

    @Test
    public void execute_invalidFilter() {
        SortFilter invalidFilter;
        try {
            invalidFilter = new SortFilter("invalid", 4);
        } catch (IllegalArgumentException e) {
            invalidFilter = null;
            SortFilter finalInvalidFilter = invalidFilter;
            assertThrows(NullPointerException.class, () -> new SortCommand(finalInvalidFilter));
        }
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(FIRST_FILTER);
        SortCommand sortSecondCommand = new SortCommand(SECOND_FILTER);
        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        //        // same values -> returns true
        //        SortCommand sortFirstCommandCopy = new SortCommand(FIRST_FILTER);
        //        assertEquals(sortFirstCommand, sortFirstCommandCopy);

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }
}

