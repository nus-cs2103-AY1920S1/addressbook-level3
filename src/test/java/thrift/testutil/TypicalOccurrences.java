package thrift.testutil;

import thrift.model.clone.Occurrence;

/**
 * A utility class containing a list of {@code Occurrence} objects to be used in tests.
 */
public class TypicalOccurrences {
    public static final Occurrence NO_OCCURRENCE = new Occurrence("daily", 0);
    public static final Occurrence DAILY_OCCURRENCE = new Occurrence("daily", 5);
    public static final Occurrence WEEKLY_OCCURRENCE = new Occurrence("weekly", 2);
    public static final Occurrence MONTHLY_OCCURRENCE = new Occurrence("monthly", 12);
    public static final Occurrence YEARLY_OCCURRENCE = new Occurrence("yearly", 1);

}
