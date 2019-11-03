package seedu.billboard.model.statistics.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.concurrent.Task;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseBreakdown;
import seedu.billboard.model.statistics.formats.FilledExpenseBreakdown;
import seedu.billboard.model.tag.Tag;

/**
 * Stateless class to generate a breakdown of expenses. Methods here are guaranteed to have no external
 * side effects, or depend on external state.
 */
public class BreakdownGenerator implements StatisticsGenerator<ExpenseBreakdown> {

    @Override
    public ExpenseBreakdown generate(List<? extends Expense> expenses) {
        Map<Tag, List<Expense>> breakdown = expenses.stream()
                .collect(HashMap::new, this::combineMapAndExpense, this::combineMapOfLists);

        return new FilledExpenseBreakdown(breakdown);
    }

    @Override
    public Task<ExpenseBreakdown> generateAsync(List<? extends Expense> expenses) {
        Task<ExpenseBreakdown> expenseBreakdownTask = new Task<>() {
            @Override
            protected ExpenseBreakdown call() {
                List<? extends Expense> copy = new ArrayList<>(expenses);
                return generate(copy);
            }
        };
        Thread thread = new Thread(expenseBreakdownTask);
        thread.setDaemon(true);
        thread.start();
        return expenseBreakdownTask;
    }

    /**
     * Adds an expense to a map of tags to lists of expenses by using its tags as keys. If there is an already
     * existing key, the expense is instead merged into the list of expenses under that key.
     */
    private void combineMapAndExpense(Map<Tag, List<Expense>> map, Expense expense) {
        expense.getTags()
                .forEach(tag -> map.merge(tag, List.of(expense), this::concatList));
    }

    /**
     * Combines the key value pairs of map2 into map1. If there are duplicate keys, the two lists are merged into
     * a new list with all the values of the old list.
     */
    private <K, V> void combineMapOfLists(Map<K, List<V>> map1, Map<K, List<V>> map2) {
        map2.forEach((key, list) -> map1.merge(key, list, this::concatList));
    }

    /**
     * Concatenates two lists and returns the result list.
     */
    private <T> List<T> concatList(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }
}
