package seedu.billboard.model.statistics.generators;

import javafx.concurrent.Task;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;

import java.util.List;

public class HeatMapGenerator implements StatisticsGenerator<ExpenseHeatMap> {
    @Override
    public ExpenseHeatMap generate(List<? extends Expense> expenses) {
        return null;
    }

    @Override
    public Task<ExpenseHeatMap> generateAsync(List<? extends Expense> expenses) {
        return null;
    }
}
