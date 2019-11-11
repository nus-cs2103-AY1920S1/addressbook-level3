package seedu.billboard.model.statistics.formats;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javafx.util.Pair;
import seedu.billboard.model.expense.Expense;


/**
 * Represents a grouping that expenses can be partitioned by.
 */
public enum ExpenseGrouping {
    TAG("tag", expenses -> expenses.stream()
            .flatMap(expense -> expense.getTags()
                    .stream()
                    .map(tag -> new Pair<>(tag, expense)))
            .collect(groupingBy(pair -> pair.getKey().tagName,
                    mapping(Pair::getValue, toList())))),

    MONTH("month", expenses -> expenses.stream()
            .collect(groupingBy(expense -> expense.getCreated().dateTime
                    .getMonth()
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault())))),

    DAY_OF_WEEK("day-of-week", expenses -> expenses.stream()
            .collect(groupingBy(expense -> expense.getCreated().dateTime
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault())))),

    NONE("none", expenses -> Map.of("All expenses", expenses));

    private static final Map<String, ExpenseGrouping> nameToGroupingMap;

    private final String name;
    private final GroupingFunction<Expense> groupingFunction;

    static {
        nameToGroupingMap = new HashMap<>();
        for (var grouping : values()) {
            nameToGroupingMap.put(grouping.getName(), grouping);
        }
    }

    ExpenseGrouping(String name, GroupingFunction<Expense> groupingFunction) {
        this.name = name;
        this.groupingFunction = groupingFunction;
    }

    public static Optional<ExpenseGrouping> groupingFromName(String name) {
        return Optional.ofNullable(nameToGroupingMap.get(name));
    }

    public String getName() {
        return name;
    }

    public GroupingFunction<Expense> getGroupingFunction() {
        return groupingFunction;
    }
}
