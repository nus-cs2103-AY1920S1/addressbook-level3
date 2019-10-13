package seedu.ichifund.model.analytics;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;

/**
 * Represents a report for balance trends.
 */
public class BalanceTrendReport extends TrendReport {

    /**
     * Constructs a {@code BalanceTrendReport}.
     *
     * @param date A date.
     */
    public BalanceTrendReport(Date date) {
        super(date);
    }

    /**
     * Fetches the relevant transaction information to generate the balance trend report.
     *
     * @param model {@code Model} which the report should be based on.
     */
    @Override
    public void fetch(Model model, Date date) {
        // TO-DO: Get balance trends by getting income trends and subtracting expenditure trends
        // Hence, fetcher should be separate class
        // Another param: Date date
    }
}
