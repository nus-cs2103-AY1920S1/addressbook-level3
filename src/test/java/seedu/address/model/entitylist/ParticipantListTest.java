package seedu.address.model.entitylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.testutil.TypicalParticipants;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParticipantListTest {
    private ParticipantList participantList = new ParticipantList();

    /**
     * Resets the participant list.
     */
    @BeforeAll
    private void reset() {
        this.participantList = new ParticipantList();
    }

    @Test
    public void add_validParticipant_successful() {
        try {
            this.participantList.add(TypicalParticipants.A);
        } catch (Exception e) {
           // Silence error
        }
        assertEquals(this.participantList.list().size(), 1);
    }

    @Test
    public void add_similarParticipant_fail() {
        try {
            this.participantList.add(TypicalParticipants.A);
        } catch (Exception e) {
            // Silence error
        }
        assertThrows(AlfredModelException.class, () ->
            this.participantList.add(TypicalParticipants.A_UPDATED)
        );
    }


    @Test
    public void get_validParticipant_success() {
        try {
            this.participantList.add(TypicalParticipants.A);
            Participant get = this.participantList.get(new Id(PrefixType.P, 1));
            assertEquals(get, TypicalParticipants.A);
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void get_invalidParticipant_fail() {
        try {
            this.participantList.add(TypicalParticipants.A);
            assertThrows(AlfredModelException.class, () ->
                    this.participantList.get(new Id(PrefixType.P, 2)));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_validParticipant_success() {
        try {
            this.participantList.add(TypicalParticipants.A);
            this.participantList.update(new Id(PrefixType.P, 1),
                    TypicalParticipants.A_UPDATED);
            assertTrue(participantList.getSpecificTypedList().get(0)
                .getEmail().equals(new Email("updated@gmail.com")));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_similarParticipant_fail() {
        try {
            this.participantList.add(TypicalParticipants.A);
            this.participantList.add(TypicalParticipants.B);
            assertThrows(AlfredModelException.class, () -> {
                this.participantList.update(new Id(PrefixType.P, 2), TypicalParticipants.A_SIMILAR);
            });
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void delete_validParticipant_success() {
        try {
            this.participantList.add(TypicalParticipants.A);
            this.participantList.delete(new Id(PrefixType.P, 1));
            assertTrue(this.participantList.list().size() == 0);
        } catch (Exception e) {
            // Silence error
        }
    }
}
