package seedu.address.commons.util;

import seedu.address.model.Model;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.ReadOnlyEntityList;

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

}
