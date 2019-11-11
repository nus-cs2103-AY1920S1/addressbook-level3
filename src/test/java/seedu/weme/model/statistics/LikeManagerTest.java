package seedu.weme.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.CHARMANDER;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import seedu.weme.testutil.TypicalMemes;

class LikeManagerTest {
    private LikeManager likeManager = new LikeManager();


    @Test
    public void constructor() {
        assertEquals(new HashMap<String, SimpleIntegerProperty>(), likeManager.getCopyLikeData());
    }

    @Test
    public void setLikeData_nullLikeData_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> likeManager.setLikeData(null));
    }

    @Test
    public void equals() {
        LikeManager likeManager = new LikeManager();
        LikeManager likeManagerPopulated = new LikeManager();
        likeManagerPopulated.addDefaultDislikeData(TypicalMemes.CHARMANDER);
        likeManagerPopulated.incrementMemeLikeCount(CHARMANDER);
        LikeManager typicalLikeManager = TypicalMemes.getTypicalStats().getLikeManager();

        // same values -> returns true
        LikeManager likeManagerPopulatedCopy = new LikeManager(likeManagerPopulated);
        LikeManager typicalLikeManagerCopy = TypicalMemes.getTypicalStats().getLikeManager();
        assertEquals(likeManagerPopulated, likeManagerPopulatedCopy);
        assertEquals(typicalLikeManager, typicalLikeManagerCopy);

        // same object -> returns true
        assertEquals(likeManager, likeManager);
        assertEquals(likeManagerPopulated, likeManagerPopulated);

        // null -> returns false
        assertNotEquals(null, likeManager);

        // different types -> returns false
        assertNotEquals(5, likeManager);

        // different stats -> returns false
        assertNotEquals(likeManager, likeManagerPopulated);

    }
}
