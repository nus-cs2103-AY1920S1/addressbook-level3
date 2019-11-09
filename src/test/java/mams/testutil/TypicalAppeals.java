package mams.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mams.model.appeal.Appeal;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalAppeals {

    public static final Appeal APPEAL1 = new Appeal("C000001" ,
            "add module", "A0156273R", "AY2019",
            20, "I want to graduate early",
            "", "", "CS1231",
            "", false, "");
    public static final Appeal APPEAL2 = new Appeal("C000002" ,
            "drop module", "A01527367W", "AY2019",
            20, "This module has timetable clash with another module of mine",
            "", "", "",
            "CS1231", false, "");
    public static final Appeal APPEAL3 = new Appeal("C000003" ,
            "increase workload", "A0180003A", "AY2019",
            20, "I want to overload",
            "", "", "",
            "", false, "");
    public static final Appeal APPEAL4 = new Appeal("C000004" ,
            "add module", "A01527367W", "AY2019",
            20, "I want to clear prerequisites",
            "", "", "cs2040",
            "", false, "");

    public static List<Appeal> getTypicalAppeals() {
        return new ArrayList<>(Arrays.asList(APPEAL1, APPEAL2, APPEAL3, APPEAL4));
    }
}
