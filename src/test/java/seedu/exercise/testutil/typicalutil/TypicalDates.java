package seedu.exercise.testutil.typicalutil;

import seedu.exercise.model.property.Date;

/**
 * Holds some date objects that can be used in testing.
 */
public class TypicalDates {

    public static final Date DATE_1 = new Date("12/12/2019");
    public static final Date DATE_2 = new Date("13/12/2019");
    public static final Date DATE_3 = new Date("14/12/2019");

    //Let's be fair here. Who will schedule their regimes at epoch date
    public static final Date DATE_EPOCH = new Date("01/01/1970");
}
