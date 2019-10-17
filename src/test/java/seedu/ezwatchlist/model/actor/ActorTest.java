package seedu.ezwatchlist.model.actor;

import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.model.actor.Actor;

import static seedu.ezwatchlist.testutil.Assert.assertThrows;

public class ActorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Actor(null));
    }

    @Test
    public void constructor_invalidActorName_throwsIllegalArgumentException() {
        String invalidActorName = "";
        assertThrows(IllegalArgumentException.class, () -> new Actor(invalidActorName));
    }

    @Test
    public void isValidActorName() {
        // null Actor name
        assertThrows(NullPointerException.class, () -> Actor.isValidActorName(null));
    }

}
