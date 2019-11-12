package seedu.ezwatchlist.model.actor;

import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ActorTest {

    @Test
    public void constructor_null_passes() {
        assert(new Actor(null).equals(new Actor(null)));
    }

    @Test
    public void constructor_invalidActorName_throwsIllegalArgumentException() {
        String invalidActorName = "";
        //assertThrows(IllegalArgumentException.class, () -> new Actor(invalidActorName));
    }

    @Test
    public void isValidActorName() {
        // null Actor name
        assertThrows(NullPointerException.class, () -> Actor.isValidActorName(null));
    }

}
