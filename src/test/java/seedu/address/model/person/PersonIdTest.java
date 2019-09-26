package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PersonIdTest {

    PersonId personId = new PersonId(1);

    @Test
    void getIdentifier() {
        assertEquals(1, personId.getIdentifier());
    }

    @Test
    void testToString() {
        assertEquals(Integer.toString(1), personId.toString());
    }

    @Test
    void testEquals() {
        PersonId personId2 = new PersonId(1);
        assertTrue(personId.equals(personId2));
    }
}