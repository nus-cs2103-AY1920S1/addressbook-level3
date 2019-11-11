package seedu.billboard.model.statistics.generators;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.commons.util.CollectionUtil.checkNonEmpty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.concurrent.Task;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;
import seedu.billboard.model.statistics.formats.FilledExpenseHeatMap;

/**
 * Stateless class to generate a heatmap of aggregate expenses. Methods here are guaranteed to have no side effects,
 * or depend on external state.
 */
public class HeatMapGenerator implements StatisticsGenerator<ExpenseHeatMap> {

    /**
     * Generates an {@code ExpenseHeatMap} from the given list of expenses over a default date range of one year
     * before the latest expense in the list.
     * @return An {@code ExpenseHeatMap} representing the aggregate expenses over each day in the date range.
     */
    @Override
    public ExpenseHeatMap generate(List<? extends Expense> expenses) {
        Objects.requireNonNull(expenses);
        if (!checkNonEmpty(expenses)) {
            return new FilledExpenseHeatMap(new ArrayList<>());
        }

        List<? extends Expense> sortedExpense = sortExpensesByDate(expenses);
        LocalDate latestDate = getLatestDate(sortedExpense);

        return generate(expenses, DateRange.fromClosed(latestDate.minusYears(1), latestDate));
    }

    /**
     * Generates an {@code ExpenseHeatMap} from the given list of expenses over the specified date range.
     * @return An {@code ExpenseHeatMap} representing the aggregate expenses over each day in the date range.
     */
    public ExpenseHeatMap generate(List<? extends Expense> expenses, DateRange dateRange) {
        requireAllNonNull(expenses, dateRange);
        if (!checkNonEmpty(expenses)) {
            return new FilledExpenseHeatMap(new ArrayList<>());
        }

        List<? extends Expense> sortedExpense = sortExpensesByDate(expenses);

        List<EnumMap<DayOfWeek, Amount>> heatMapValues = dateRange
                .partitionByInterval(DateInterval.WEEK)
                .stream()
                .map(range -> new EnumMap<DayOfWeek, Amount>(DayOfWeek.class))
                .collect(Collectors.toList());

        sortedExpense.forEach(expense -> addExpenseToHeatMap(dateRange, heatMapValues, expense));

        return new FilledExpenseHeatMap(heatMapValues);
    }

    /**
     * Generates an {@code ExpenseHeatMap} asynchronously from the given list of expenses over a default date range
     * of one year before the latest expense in the list.
     * @return An {@code ExpenseHeatMap} representing the aggregate expenses over each day in the date range.
     */
    @Override
    public Task<ExpenseHeatMap> generateAsync(List<? extends Expense> expenses) {
        Objects.requireNonNull(expenses);
        if (!checkNonEmpty(expenses)) {
            return taskFrom(() -> generate(new ArrayList<>()));
        }

        List<? extends Expense> sortedExpense = sortExpensesByDate(expenses);
        LocalDate latestDate = getLatestDate(sortedExpense);

        return generateAsync(sortedExpense, DateRange.fromClosed(
                latestDate.minusYears(1), latestDate));
    }

    /**
     * Generates an {@code ExpenseHeatMap} asynchronously from the given list of expenses over the specified date range.
     * @return An {@code ExpenseHeatMap} representing the aggregate expenses over each day in the date range.
     */
    public Task<ExpenseHeatMap> generateAsync(List<? extends Expense> expenses, DateRange dateRange) {
        Objects.requireNonNull(expenses);

        return taskFrom(() -> {
            List<? extends Expense> copy = new ArrayList<>(expenses);
            return generate(copy, dateRange);
        });
    }

    private List<? extends Expense> sortExpensesByDate(List<? extends Expense> expenses) {
        return expenses.stream()
                .sorted(Comparator.comparing(expense -> expense.getCreated().dateTime))
                .collect(Collectors.toList());
    }

    /**
     * Gets the latest created date out of a list of expenses sorted by date.
     */
    private LocalDate getLatestDate(List<? extends Expense> sortedExpense) {
        return sortedExpense.get(sortedExpense.size() - 1)
                .getCreated()
                .dateTime.toLocalDate();
    }

    /**
     * Aggregates the given expense with the heat map values, if the expense lies within the accepted date range for
     * the heat map.
     */
    private void addExpenseToHeatMap(DateRange dateRange,
                                     List<EnumMap<DayOfWeek, Amount>> heatMapValues,
                                     Expense expense) {
        LocalDate createdDate = expense.getCreated().dateTime.toLocalDate();

        if (dateRange.contains(createdDate)) {
            int index = (int) ChronoUnit.WEEKS.between(
                    dateRange.getStartDate().with(DateInterval.WEEK.getAdjuster()), createdDate);

            DayOfWeek dayOfWeek = createdDate.getDayOfWeek();
            heatMapValues.get(index).merge(dayOfWeek, expense.getAmount(), Amount::add);
        }
    }

    /**
     * Creates a task from the given supplier, returning it asynchronously.
     */
    private <T> Task<T> taskFrom(Supplier<T> supplier) {
        Task<T> task = new Task<>() {
            @Override
            protected T call() {
                return supplier.get();
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task;
    }
}
