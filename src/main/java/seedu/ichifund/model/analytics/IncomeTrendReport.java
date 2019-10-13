package seedu.ichifund.model.analytics;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;

/**
 * Represents a report for income trends.
 */
public class IncomeTrendReport extends TrendReport {

    /**
     * Constructs a {@code IncomeTrendReport}.
     *
     * @param date A date.
     */
    public IncomeTrendReport(Date date) {
        super(date);
    }

    /**
     * Fetches the relevant transaction information to generate the income trend report.
     *
     * @param model {@code Model} which the report should be based on.
     */
    @Override
    public void fetch(Model model, Date date) {
        // TO-DO: Get income trends
        // Another param: Date date
    }
}
