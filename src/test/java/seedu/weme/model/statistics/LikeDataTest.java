package seedu.weme.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.TypicalMemes;

class LikeDataTest {

    private LikeData likeData = new LikeData();

    @Test
    public void equals() {
        LikeData typicalLikeData = new LikeData(TypicalMemes.getTypicalStats()
                .getLikeManager().getObservableLikeData());

        // same values -> returns true
        LikeData typicalLikeDataCopy = new LikeData(typicalLikeData);
        assertEquals(likeData, new LikeData());
        assertEquals(typicalLikeData, typicalLikeDataCopy);

        // same object -> returns true
        assertEquals(likeData, likeData);
        assertEquals(typicalLikeData, typicalLikeData);

        // null -> returns false
        assertNotEquals(null, likeData);

        // different types -> returns false
        assertNotEquals(5, likeData);

        // different stats -> returns false
        assertNotEquals(likeData, typicalLikeData);
    }
}
