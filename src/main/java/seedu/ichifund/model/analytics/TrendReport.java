package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.analytics.exceptions.ReportException;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * Represents a report for expenditure, income or balance trends.
 */
public abstract class TrendReport extends Report {

    /**
     * Constructs a {@code TrendReport}.
     *
     * @param month A month.
     * @param year A year.
     */
    public TrendReport(Month month, Year year) {
        super(month, year);
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
     * @param year {@code year} which the report should be based on.
     */
    public abstract List<Amount> fetch(Model model, Year year);
}
