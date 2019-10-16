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
            "add module", "A0180000A", "AY2019",
            20, "I want to graduate early",
            "", "", "CS2100",
            "", false, "");
    public static final Appeal APPEAL2 = new Appeal("C000002" ,
            "drop module", "A0180001A", "AY2019",
            20, "I want to take another module",
            "", "", "",
            "CS2103", false, "");
    public static final Appeal APPEAL3 = new Appeal("C000003" ,
            "increase workload", "A0180003A", "AY2019",
            20, "I want to overload",
            "", "", "",
            "", false, "");
    public static final Appeal APPEAL4 = new Appeal("C000004" ,
            "add module", "A0180002A", "AY2019",
            20, "I want to clear prerequisites",
            "", "", "CS2105",
            "", false, "");

    public static List<Appeal> getTypicalAppeals() {
        return new ArrayList<>(Arrays.asList(APPEAL1, APPEAL2, APPEAL3, APPEAL4));
    }
}
