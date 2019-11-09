package seedu.address.commons.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.entitylist.TeamList;

/**
 * Facilitates retrieving of different items based on {@code PrefixType} of {@code Entity}s.
 */
public class PrefixUtil {

    public static String getStringBasedOnPrefixType(
            PrefixType prefixType, String mentorString, String participantString, String teamString) {
        switch (prefixType) {
        case M:
            return mentorString;
        case P:
            return participantString;
        case T:
            return teamString;
        default:
            // Should never reach here
            throw new RuntimeException();
        }
    }

    public static ReadOnlyEntityList getEntityListBasedOnPrefixType(PrefixType prefixType, Model model) {
        switch (prefixType) {
        case M:
            return model.getMentorList();
        case P:
            return model.getParticipantList();
        case T:
            return model.getTeamList();
        default:
            // Should never reach here
            throw new RuntimeException();
        }
    }

    /**
     * Retrieves the {@code Id} from give {@code strId}.
     * If {@code strId} is invalid, generate a valid {@code Id} from respective {@code EntityList}.
     *
     * @param strId {@code String Id} to parse into {@code Id}.
     * @param prefixType {@code PrefixType} to indicate the {@code Entity} type of the {@code Id} to be generated.
     * @return Generated {@code Id}.
     */
    public static Id retrieveId(String strId, PrefixType prefixType) {
        // A valid Id can be just a number (i.e. 1, 2, 3) or a String form of an Id (i.e. M-1, P-1, T-1)
        Id entityId;
        try {
            if (!Id.isValidString(strId)) {
                strId = prefixType.toString() + "-" + strId;
            }
            entityId = Id.toId(strId);
        } catch (IllegalValueException ive) {
            switch (prefixType) {
            case M:
                entityId = MentorList.generateId();
                break;
            case P:
                entityId = ParticipantList.generateId();
                break;
            case T:
                entityId = TeamList.generateId();
                break;
            default:
                // Should never reach here
                throw new RuntimeException();
            }
        }
        return entityId;
    }

}
