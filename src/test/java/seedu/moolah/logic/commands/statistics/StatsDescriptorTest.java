package seedu.moolah.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EARLIER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_LATER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_DESCRIPTOR;

import org.junit.jupiter.api.Test;

import seedu.moolah.testutil.StatsDescriptorBuilder;

class StatsDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        StatsDescriptor descriptorWithSameValues = new StatsDescriptor(VALID_STATS_DESCRIPTOR);
        assertTrue(VALID_STATS_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VALID_STATS_DESCRIPTOR.equals(VALID_STATS_DESCRIPTOR));

        // null -> returns false
        assertFalse(VALID_STATS_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(VALID_STATS_DESCRIPTOR.equals(5));

        // different start date -> returns false
        StatsDescriptor editedStatsDescriptor = new StatsDescriptorBuilder(VALID_STATS_DESCRIPTOR)
                .withStartDate(VALID_EARLIER_TIMESTAMP).build();
        assertFalse(VALID_STATS_DESCRIPTOR.equals(editedStatsDescriptor));

        // different end date -> returns false
        editedStatsDescriptor = new StatsDescriptorBuilder(VALID_STATS_DESCRIPTOR)
                .withEndDate(VALID_LATER_TIMESTAMP).build();
        assertFalse(VALID_STATS_DESCRIPTOR.equals(editedStatsDescriptor));

    }
}
