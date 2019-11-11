package seedu.moolah.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_DESCRIPTOR;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.general.ClearCommand;

/**
 * Contains tests for StatsCommand
 */
class StatsCommandTest {

    @Test
    public void equals() {
        final StatsCommand standardCommand = new StatsCommand(VALID_STATS_DESCRIPTOR);

        // same values -> returns true
        StatsDescriptor copyDescriptor = new StatsDescriptor(VALID_STATS_DESCRIPTOR);
        StatsCommand commandWithSameValues = new StatsCommand(copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());
    }

}
