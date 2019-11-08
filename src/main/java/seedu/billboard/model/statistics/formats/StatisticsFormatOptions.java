package seedu.billboard.model.statistics.formats;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Expense;

/**
 * Encapsulates the changes in options that a user can select with regards to the display of the statistics.
 */
public class StatisticsFormatOptions {

    private DateInterval dateInterval;
    private Grouping grouping;

    /**
     * Private constructor to prevent instantiation.
     */
    private StatisticsFormatOptions(DateInterval dateInterval, Grouping grouping) {
        this.dateInterval = dateInterval;
        this.grouping = grouping;
    }

    /**
     * Returns a {@code StatisticsFormatOptions} object with no changes.
     */
    public static StatisticsFormatOptions emptyOption() {
        return new StatisticsFormatOptions(null, null);
    }

    public static StatisticsFormatOptions withOptions(DateInterval dateInterval, Grouping grouping) {
        return new StatisticsFormatOptions(dateInterval, grouping);
    }

    /**
     * @return an optional wrapper of the selected new date interval, if any.
     */
    public Optional<DateInterval> getNewDateInterval() {
        return Optional.ofNullable(dateInterval);
    }

    public Optional<Grouping> getGrouping() {
        return Optional.ofNullable(grouping);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof StatisticsFormatOptions) {
            return ((StatisticsFormatOptions) other).dateInterval == this.dateInterval;
        }
        return false;
    }

    /**
     * Represents a grouping that expenses can be partitioned by.
     */
    public enum Grouping {
        TAG("tag", expenses ->
                expenses.stream()
                        .flatMap(expense -> expense.getTags()
                                .stream()
                                .map(tag -> new Pair<>(tag, expense)))
                        .collect(groupingBy(pair -> pair.getKey().tagName,
                                mapping(Pair::getValue, toList())))),

        NONE("none", expenses -> Map.of("All expenses", expenses));

        private static final Map<String, Grouping> nameToGroupingMap;
        private final String name;
        private final Function<? extends List<? extends Expense>,
                Map<String, ? extends List<? extends Expense>>> groupingFunction;

        static {
            nameToGroupingMap = new HashMap<>();
            for (var grouping : values()) {
                nameToGroupingMap.put(grouping.getName(), grouping);
            }
        }

        Grouping(String name,
                 Function<? extends List<? extends Expense>,
                         Map<String, ? extends List<? extends Expense>>> groupingFunction) {
            this.name = name;
            this.groupingFunction = groupingFunction;
        }

        public static Optional<Grouping> groupingFromName(String name) {
            return Optional.ofNullable(nameToGroupingMap.get(name));
        }

        public String getName() {
            return name;
        }

        public Function<? extends List<? extends Expense>,
                Map<String, ? extends List<? extends Expense>>> getGroupingFunction() {
            return groupingFunction;
        }}
}
