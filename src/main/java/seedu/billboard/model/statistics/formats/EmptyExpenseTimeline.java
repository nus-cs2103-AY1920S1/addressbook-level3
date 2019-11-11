package seedu.billboard.model.statistics.formats;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;

/**
 * Class representing an {@code ExpenseTimeline} with no expenses to show.
 */
public class EmptyExpenseTimeline implements ExpenseTimeline {

    @Override
    public DateInterval getDateInterval() {
        return DateInterval.MONTH;
    }

    @Override
    public List<Pair<DateRange, Amount>> getTimelineValues() {
        return new ArrayList<>();
    }
}
