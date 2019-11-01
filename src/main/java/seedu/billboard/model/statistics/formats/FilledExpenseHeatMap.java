package seedu.billboard.model.statistics.formats;

import java.util.EnumMap;
import java.util.List;
import java.time.DayOfWeek;

import seedu.billboard.model.expense.Amount;


/**
 * Represents a concrete implementation of {@code ExpenseHeatMap}.
 */
public class FilledExpenseHeatMap implements ExpenseHeatMap {

    private List<EnumMap<DayOfWeek, Amount>> heatMapValues;

    public FilledExpenseHeatMap(List<EnumMap<DayOfWeek, Amount>> heatMapValues) {
        this.heatMapValues = heatMapValues;
    }

    @Override
    public List<EnumMap<DayOfWeek, Amount>> getHeatMapValues() {
        return heatMapValues;
    }
}
