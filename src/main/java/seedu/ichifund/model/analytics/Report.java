package seedu.ichifund.model.analytics;

import seedu.ichifund.model.analytics.exceptions.ReportException;
import seedu.ichifund.model.Model;

/**
 * Represents a report with hidden internal logic and the ability to be generated.
 */
public abstract class Report {
    // final Date date;
    // LATER: final Category category

    /**
     * Generates the report.
     *
     * @param model {@code Model} which the report should be based on.
     * @return feedback message of the operation result for display
     * @throws ReportException If an error occurs during report generation.
     */
    public abstract ReportException generate(Model model) throws ReportException;
}
