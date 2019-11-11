package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEventDayTimes.DEFAULT_DAY_TIME;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class AssignDateCommandTest {

    private Model model = new ModelManager(getTypicalEventBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        AssignDateCommand deleteEventCommand = new AssignDateCommand(outOfBoundIndex, DEFAULT_DAY_TIME);
        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        AssignDateCommand assignCommand = new AssignDateCommand(outOfBoundIndex, DEFAULT_DAY_TIME);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignDateCommand assignFirstCommand = new AssignDateCommand(INDEX_FIRST_EVENT, DEFAULT_DAY_TIME);
        AssignDateCommand assignSecondCommand = new AssignDateCommand(INDEX_SECOND_EVENT, DEFAULT_DAY_TIME);

        // same object -> returns true
        assertTrue(assignFirstCommand.equals(assignFirstCommand));

        // same values -> returns true
        AssignDateCommand assignFirstCommandCopy = new AssignDateCommand(INDEX_FIRST_EVENT, DEFAULT_DAY_TIME);
        assertTrue(assignFirstCommand.equals(assignFirstCommandCopy));

        // different types -> returns false
        assertFalse(assignFirstCommand.equals(1));

        // null -> returns false
        assertFalse(assignFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(assignFirstCommand.equals(assignSecondCommand));
    }

}
