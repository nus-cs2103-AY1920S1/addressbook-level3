package seedu.billboard.model.statistics.formats;

import java.util.EnumMap;
import java.util.List;
import java.time.DayOfWeek;

import javafx.util.Pair;

import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;


/**
 * Represents a concrete implementation of {@code ExpenseHeatMap}.
 */
public class FilledExpenseHeatMap implements ExpenseHeatMap {

    private List<Pair<DateRange, EnumMap<DayOfWeek, Amount>>> heatMapValues;

    public FilledExpenseHeatMap(List<Pair<DateRange, EnumMap<DayOfWeek, Amount>>> heatMapValues) {
        this.heatMapValues = heatMapValues;
    }

    @Override
    public List<Pair<DateRange, EnumMap<DayOfWeek, Amount>>> getHeatMapValues() {
        return heatMapValues;
    }
}
