package seedu.address.testutil;

import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Id INDEX_FIRST_TEAM = new Id(PrefixType.T, 1);
    public static final Id INDEX_FIRST_MENTOR = new Id(PrefixType.M, 1);
    public static final Id INDEX_THIRD_PARTICIPANT = new Id(PrefixType.P, 3);
}
