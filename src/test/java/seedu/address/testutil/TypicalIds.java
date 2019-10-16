package seedu.address.testutil;

import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * A utility class containing a list of {@code Id} objects to be used in tests.
 */
public class TypicalIds {

    // Participant Ids
    public static final Id ID_FIRST_PARTICIPANT = new Id(PrefixType.P, 1);
    public static final Id ID_SECOND_PARTICIPANT = new Id(PrefixType.P, 2);
    public static final Id ID_THIRD_PARTICIPANT = new Id(PrefixType.P, 3);
    // Mentor Ids
    public static final Id ID_FIRST_MENTOR = new Id(PrefixType.M, 1);
    public static final Id ID_SECOND_MENTOR = new Id(PrefixType.M, 2);
    public static final Id ID_THIRD_MENTOR = new Id(PrefixType.M, 3);
    // Team Ids
    public static final Id ID_FIRST_TEAM = new Id(PrefixType.T, 1);
    public static final Id ID_SECOND_TEAM = new Id(PrefixType.T, 2);
    public static final Id ID_THIRD_TEAM = new Id(PrefixType.T, 3);

}
