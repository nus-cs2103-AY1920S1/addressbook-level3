package seedu.billboard.model.statistics.formats;

import java.util.EnumMap;
import java.util.List;
import java.time.DayOfWeek;

import seedu.billboard.model.expense.Amount;

/**
 * Represents a aggregation of expenses per day over a period of time.
 */
public interface ExpenseHeatMap {

    /**
     * Returns a representation of aggregate expenses per day over a period of time. Each pair in the list
     * represents a week, with the {@code DateRange} specifying the dates of the start and end of the week, and the
     * {@code EnumMap} specifying the days of the week mapped to the aggregate amount spend on that day.
     */
    List<EnumMap<DayOfWeek, Amount>> getHeatMapValues();
}
