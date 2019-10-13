package seedu.ichifund.model.analytics;

import seedu.ichifund.model.Model;

/**
 * Represents a report for balance trends.
 */
public class BalanceTrendReport extends TrendReport {

    /**
     * Fetches the relevant transaction information to generate the balance trend report.
     *
     * @param model {@code Model} which the report should be based on.
     */
    @Override
    public void fetch(Model model) {
        // TO-DO: Get balance trends by getting income trends and subtracting expenditure trends
        // Hence, fetcher should be separate class
        // Another param: Date date
    }
}