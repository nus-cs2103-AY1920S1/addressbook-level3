package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class IdTest {

    @Test
    public void equals_sameId_returnsTrue() {
        assertEquals(new Id(PrefixType.T, 1), new Id(PrefixType.T, 1));
    }

    @Test
    public void toId_validParameters_returnsValidId() {
        String validMentorId1 = "M-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.M, 1), Id.toId(validMentorId1)));
        String validMentorId2 = "m-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.M, 1), Id.toId(validMentorId2)));
        String validMentorId3 = "M-1234";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.M, 1234), Id.toId(validMentorId3)));

        String validParticipantId1 = "P-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.P, 1), Id.toId(validParticipantId1)));
        String validParticipantId2 = "p-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.P, 1), Id.toId(validParticipantId2)));
        String validParticipantId3 = "P-1234";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.P, 1234), Id.toId(validParticipantId3)));

        String validTeamId1 = "T-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.T, 1), Id.toId(validTeamId1)));
        String validTeamId2 = "t-1";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.T, 1), Id.toId(validTeamId2)));
        String validTeamId3 = "T-1234";
        assertDoesNotThrow(() -> assertEquals(new Id(PrefixType.T, 1234), Id.toId(validTeamId3)));
    }

    @Test
    public void toId_invalidParameters_throwsIllegalValueException() {
        String invalidId1 = "PTM-1";
        assertThrows(IllegalValueException.class, () -> Id.toId(invalidId1));

        String invalidId2 = "PP-1";
        assertThrows(IllegalValueException.class, () -> Id.toId(invalidId2));

        String invalidId3 = "P-T-M-1";
        assertThrows(IllegalValueException.class, () -> Id.toId(invalidId3));
    }
}
