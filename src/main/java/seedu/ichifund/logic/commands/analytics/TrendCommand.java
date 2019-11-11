package seedu.ichifund.logic.commands.analytics;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.analytics.TrendReport;
import seedu.ichifund.model.date.Year;

/**
 * Represents a report command for expenditure, income or balance trends.
 */
public abstract class TrendCommand extends Command {

    protected final Year year;

    /**
     * Constructs a {@code TrendCommand}.
     *
     * @param year A year.
     */
    public TrendCommand(Optional<Year> year) {
        requireNonNull(year);
        this.year = year.orElseGet(Year::getCurrent);
    }

    protected TrendReport createTrendReport(Year year) {
        requireNonNull(year);
        return new TrendReport(year);
    }

    public abstract CommandResult execute(Model model);
}
