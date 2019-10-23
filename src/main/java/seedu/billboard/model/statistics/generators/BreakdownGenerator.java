package seedu.billboard.model.statistics.generators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private void combineMapAndExpense(Map<Tag, List<Expense>> map, Expense expense) {
        expense.getTags()
                .forEach(tag -> map.merge(tag, List.of(expense), this::concatList));
    }

    private <K, V> void combineMapOfLists(Map<K, List<V>> map1, Map<K, List<V>> map2) {
        map1.forEach((key, list) -> map2.merge(key, list, this::concatList));
    }

    private <T> List<T> concatList(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }
}
