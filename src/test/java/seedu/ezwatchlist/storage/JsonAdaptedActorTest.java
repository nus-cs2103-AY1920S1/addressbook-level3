package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;

class JsonAdaptedActorTest {

    @Test
    void getActorName() {
        JsonAdaptedActor jsonAdaptedActor = new JsonAdaptedActor("TEST NAME");
        assertEquals(jsonAdaptedActor.getActorName(), "TEST NAME");
    }

    @Test
    void toModelType() throws IllegalValueException {
        JsonAdaptedActor jsonAdaptedActor = new JsonAdaptedActor("");
        assertThrows(IllegalValueException.class, () -> jsonAdaptedActor.toModelType());

        JsonAdaptedActor jsonAdaptedActor2 = new JsonAdaptedActor("Test name");
        assertEquals(jsonAdaptedActor2.toModelType(), new Actor("Test name"));
    }
}
