package mams.testutil;

import java.util.Date;

import mams.commons.core.time.TimeStamp;

/**
 * Utility class for holding typical time-stamps used for MAMS testing.
 */
public class TypicalTimeStamps {
    public static final String VALID_TIME_STRING = "[2019-11-01|00:30:02]";
    public static final String VALID_TIME_STRING_2 = "[2019-11-30|10:30:02]";
    public static final String VALID_TIME_STRING_3 = "[2019-11-01|12:30:02]";
    public static final String VALID_TIME_STRING_4 = "[2020-12-01|00:05:02]";
    public static final String VALID_TIME_STRING_5 = "[2021-08-25|22:05:02]";
    public static final String VALID_TIME_STRING_6 = "[2100-12-11|03:05:02]";

    public static final String INVALID_TIME_STRING_7 = "wewlfkwmlgfkwmf";
    public static final String INVALID_TIME_STRING_8 = "&&@$%@$EF";
    public static final String INVALID_TIME_STRING_9 = "I love to suffer through all these tests";

    // UNIX TIME in milliseconds. Used to construct the sample TimeStamps below.
    public static final long UNIX_TIME_1 = 1572540862;
    public static final long UNIX_TIME_2 = 1572559161;
    public static final long UNIX_TIME_3 = 1572650862;
    public static final long UNIX_TIME_4 = 1572780456;
    public static final long UNIX_TIME_5 = 1572599999;
    public static final long UNIX_TIME_6 = 1572600000;

    // TimeStamp's 1-4 are the ones being used in TypicalCommandHistory
    public static final TimeStamp TIME_STAMP_1 = new TimeStamp(new Date(UNIX_TIME_1));
    public static final TimeStamp TIME_STAMP_2 = new TimeStamp(new Date(UNIX_TIME_2));
    public static final TimeStamp TIME_STAMP_3 = new TimeStamp(new Date(UNIX_TIME_3));
    public static final TimeStamp TIME_STAMP_4 = new TimeStamp(new Date(UNIX_TIME_4));

    // extra TimeStamp's that are not part of TypicalCommandHistory
    public static final TimeStamp TIME_STAMP_5 = new TimeStamp(new Date(UNIX_TIME_5));
    public static final TimeStamp TIME_STAMP_6 = new TimeStamp(new Date(UNIX_TIME_6));

    private TypicalTimeStamps() {}

}
