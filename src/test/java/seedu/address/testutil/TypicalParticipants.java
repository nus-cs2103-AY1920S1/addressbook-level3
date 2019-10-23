package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.ParticipantList;

/**
 * A utility class containing a list of {@code Participant} objects to be used in tests.
 */
public class TypicalParticipants {
    public static final Participant A = new Participant(new Name("Participant A"),
                                                        new Id(PrefixType.P, 1),
                                                        new Email("participantA@gmail.com"),
                                                        new Phone("91111111"));

    public static final Participant A_UPDATED = new Participant(new Name("Participant McParticipantyface"),
                                                                new Id(PrefixType.P, 1),
                                                                new Email("updated@gmail.com"),
                                                                new Phone("92121212"));

    public static final Participant A_SIMILAR = new Participant(new Name("Participant A"),
            new Id(PrefixType.P, 2),
            new Email("participantA@gmail.com"),
            new Phone("92121212"));

    public static final Participant B = new Participant(new Name("Part B"),
            new Id(PrefixType.P, 2),
                                                        new Email("participantB@hotmail.com"),
                                                        new Phone("82222222"));

    public static final Participant C = new Participant(new Name("Participant C"),
                                                        new Id(PrefixType.P, 3),
                                                        new Email("participantC@yahoo.com"),
                                                        new Phone("933331231"));

    // Manually created mentors - Mentor details found in {@code CommandTestUtil}
    public static final Participant AMY = new ParticipantBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
            .withPhone(VALID_PHONE_AMY).build();

    public static final Participant BOB = new ParticipantBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
            .withPhone(VALID_PHONE_BOB).build();

    /**
     * Gets the Typical Participants in the form of a List.
     * @return List of Typical Participants
     */
    public static List<Participant> getTypicalParticipants() {
        return new ArrayList<>(Arrays.asList(A, B, C));
    }

    /**
     * Gets the Typical Participants in the form of a ParticipantList.
     * @return ParticipantList containing Typical Participants
     * @throws AlfredModelException
     */
    public static ParticipantList getTypicalParticipantList() throws AlfredModelException {
        ParticipantList pList = new ParticipantList();
        for (Participant p: getTypicalParticipants()) {
            pList.add(p);
        }
        return pList;
    }
}
