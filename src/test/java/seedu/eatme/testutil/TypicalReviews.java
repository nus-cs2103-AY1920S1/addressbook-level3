package seedu.eatme.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.model.eatery.Review;

/**
 * Contains a list of typical reviews used for testing.
 */
public class TypicalReviews {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static Date date;
    static {
        try {
            date = dateFormat.parse("22/11/2019");
        } catch (ParseException e) {
            LogsCenter.getLogger(TypicalReviews.class).warning("The date is not in the specified format (dd/MM/yyyy");
        }
    }
    public static final Review REVIEW_1 = new Review("good", 3, 4, date);
    public static final Review REVIEW_2 = new Review("bad", 4, 3, date);

    public TypicalReviews() {
    }
}
