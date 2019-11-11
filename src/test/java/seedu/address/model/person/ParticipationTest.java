package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ParticipationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Participation(null));
    }

    @Test
    public void constructor_invalidParticipation_throwsIllegalArgumentException() {
        String invalidParticipation = "present";
        assertThrows(IllegalArgumentException.class, () -> new Participation(invalidParticipation));
    }

    @Test
    public void isValidParticipation() {
        assertThrows(NullPointerException.class, () -> Participation.isValidParticipation(null));

        assertFalse(Participation.isValidParticipation(""));
        assertFalse(Participation.isValidParticipation(" "));
        assertFalse(Participation.isValidParticipation("^"));
        assertFalse(Participation.isValidParticipation("here"));

        assertTrue(Participation.isValidParticipation("1"));
        assertTrue(Participation.isValidParticipation("23"));
        assertTrue(Participation.isValidParticipation("79"));
        assertTrue(Participation.isValidParticipation("99"));
    }
}
