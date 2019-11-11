package seedu.moolah.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EARLIER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_LATER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_MODE_BUDGET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_TREND_DESCRIPTOR;

import org.junit.jupiter.api.Test;

import seedu.moolah.testutil.StatsTrendDescriptorBuilder;

class StatsTrendDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        StatsTrendDescriptor descriptorWithSameValues = new StatsTrendDescriptor(VALID_STATS_TREND_DESCRIPTOR);
        assertTrue(VALID_STATS_TREND_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VALID_STATS_TREND_DESCRIPTOR.equals(VALID_STATS_TREND_DESCRIPTOR));

        // null -> returns false
        assertFalse(VALID_STATS_TREND_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(VALID_STATS_TREND_DESCRIPTOR.equals(5));

        // different start date -> returns false
        StatsTrendDescriptor editedStatsTrendDescriptor = new StatsTrendDescriptorBuilder(VALID_STATS_TREND_DESCRIPTOR)
                .withStartDate(VALID_EARLIER_TIMESTAMP).build();
        assertFalse(VALID_STATS_TREND_DESCRIPTOR.equals(editedStatsTrendDescriptor));

        // different end date -> returns false
        editedStatsTrendDescriptor = new StatsTrendDescriptorBuilder(VALID_STATS_TREND_DESCRIPTOR)
                .withEndDate(VALID_LATER_TIMESTAMP).build();
        assertFalse(VALID_STATS_TREND_DESCRIPTOR.equals(editedStatsTrendDescriptor));

        // different mode -> returns false
        editedStatsTrendDescriptor = new StatsTrendDescriptorBuilder(VALID_STATS_TREND_DESCRIPTOR)
                .withMode(VALID_MODE_BUDGET).build();
        assertFalse(VALID_STATS_TREND_DESCRIPTOR.equals(editedStatsTrendDescriptor));

    }
}
