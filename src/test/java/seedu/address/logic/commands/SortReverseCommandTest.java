package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertViewContactFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Filters.FIRST_FILTER;
import static seedu.address.testutil.Filters.SECOND_FILTER;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SortFilter;
import seedu.address.model.UserPrefs;


class SortReverseCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortReverseCommand(null));
    }

    @Test
    public void execute_validFilter() {

    }

    @Test
    public void execute_invalidFilter() {
        SortFilter invalidFilter = new SortFilter("invalid", 4);
        SortReverseCommand sortReverseCommand = new SortReverseCommand(invalidFilter);
        assertViewContactFailure(sortReverseCommand, model, "Filter does not exist");
    }

    @Test
    public void equals() {
        SortReverseCommand sortReverseFirstCommand = new SortReverseCommand(FIRST_FILTER);
        SortReverseCommand sortReverseSecondCommand = new SortReverseCommand(SECOND_FILTER);
        // same object -> returns true
        assertTrue(sortReverseFirstCommand.equals(sortReverseFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(FIRST_FILTER);
        assertFalse(sortReverseFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortReverseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortReverseFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(sortReverseFirstCommand.equals(sortReverseSecondCommand));
    }

}
