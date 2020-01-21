package seedu.algobase.model.searchrule.plansearchrule;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.algobase.model.plan.Plan;

/**
 * Tests that a {@code Plan}'s time range has overlap with the given time range.
 */
public class TimeRangePredicate implements Predicate<Plan> {

    public static final TimeRangePredicate DEFAULT_TIME_RANGE_PREDICATE =
        new TimeRangePredicate() {
            @Override
            public boolean test(Plan plan) {
                return true;
            }
        };

    private final TimeRange timeRange;

    public TimeRangePredicate(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    private TimeRangePredicate() {
        this.timeRange = null;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    @Override
    public boolean test(Plan plan) {
        assert this.timeRange != null;
        return this.timeRange.hasOverlap(new TimeRange(plan.getStartDate(), plan.getEndDate()));
    }

    @Override
    public boolean equals(Object other) {
        requireNonNull(timeRange);
        return other == this
                || (other instanceof TimeRangePredicate
                && timeRange.equals(((TimeRangePredicate) other).getTimeRange()));

    }
}
