package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.analytics.exceptions.ReportException;
import seedu.ichifund.model.date.Date;

/**
 * Represents a report for expenditure, income or balance trends.
 */
public abstract class TrendReport extends Report {

    /**
     * Constructs a {@code TrendReport}.
     *
     * @param date A date.
     */
    public TrendReport(Date date) {
        super(date);
    }

    /**
     * Generates the report.
     *
     * @param model {@code Model} which the report should be based on.
     * @return feedback message of the operation result for display
     * @throws ReportException If an error occurs during report generation.
     */
    @Override
    public ReportException generate(Model model) throws ReportException {
        requireNonNull(model);
        throw new ReportException("TO-DO");
    }

    /**
     * Fetches the relevant transaction information to generate the trend report.
     *
     * @param model {@code Model} which the report should be based on.
     */
    public abstract void fetch(Model model, Date date);
}
