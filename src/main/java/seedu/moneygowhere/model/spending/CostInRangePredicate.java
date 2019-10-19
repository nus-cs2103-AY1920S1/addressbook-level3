package seedu.moneygowhere.model.spending;

import java.util.function.Predicate;

/**
 * Tests that a {@code Spending}'s {@code Cost} matches the given cost range.
 */
public class CostInRangePredicate implements Predicate<Spending> {
    private final double minCost;
    private final double maxCost;

    public CostInRangePredicate(Cost minCost, Cost maxCost) {
        this.minCost = Double.parseDouble(minCost.value);
        this.maxCost = Double.parseDouble(maxCost.value);

        if (this.minCost > this.maxCost) {
            throw new IllegalArgumentException("Min cost should not be larger than max cost");
        }
    }

    @Override
    public boolean test(Spending spending) {
        double cost = Double.parseDouble(spending.getCost().value);
        return cost >= minCost && cost <= maxCost;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CostInRangePredicate // instanceof handles nulls
                && minCost == ((CostInRangePredicate) other).minCost
                && maxCost == ((CostInRangePredicate) other).maxCost); // state check
    }

}
