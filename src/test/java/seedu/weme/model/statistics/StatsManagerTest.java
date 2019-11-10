package seedu.weme.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.TypicalMemes;

class StatsManagerTest {

    private StatsManager statsManager = new StatsManager();

    @Test
    public void constructor() {
        assertEquals(new LikeManager(), statsManager.getLikeManager());
    }

    @Test
    public void setLikeData_nullLikeData_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> statsManager.setLikeData(null));
    }

    @Test
    public void equals() {
        Stats stats = new StatsManager();
        Stats typicalStats = TypicalMemes.getTypicalStats();

        // same values -> returns true
        Stats statsCopy = new StatsManager();
        assertEquals(stats, statsCopy);
        assertEquals(typicalStats, TypicalMemes.getTypicalStats());

        // same object -> returns true
        assertEquals(stats, stats);

        // null -> returns false
        assertNotEquals(null, stats);

        // different types -> returns false
        assertNotEquals(5, stats);

        // different stats -> returns false
        Stats emptyStats = new StatsManager();
        assertNotEquals(emptyStats, TypicalMemes.getTypicalStats());

    }
}
