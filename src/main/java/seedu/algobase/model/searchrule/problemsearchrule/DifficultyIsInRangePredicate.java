package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

import java.util.function.Predicate;

import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Problem;

/**
 * Tests that a {@code Problem}'s {@code Difficulty} is in the range [{@code lowerBound}, {@code upperBound}].
 */
public class DifficultyIsInRangePredicate implements Predicate<Problem> {

    public static final DifficultyIsInRangePredicate DEFAULT_DIFFICULTY_PREDICATE =
        new DifficultyIsInRangePredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    public static final String MESSAGE_CONSTRAINTS = "Both the lower and upper bound for a valid difficulty range "
        + "should be between (0,5] and lower bound should be no greater than upper bound";
    private static final double DEFAULT_BOUND = -1.0;
    private final double lowerBound;
    private final double upperBound;

    public DifficultyIsInRangePredicate(double lowerBound, double upperBound) throws IllegalArgumentException {
        checkArgument(isValidDifficultyRange(lowerBound, upperBound), MESSAGE_CONSTRAINTS);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    private DifficultyIsInRangePredicate() {
        this.lowerBound = DEFAULT_BOUND;
        this.upperBound = DEFAULT_BOUND;
    }

    /**
     * Returns true if the given lower bound and upper bound is a valid difficulty range.
     * @param lowerBound to be tested
     * @param upperBound to be tested
     */
    public static boolean isValidDifficultyRange(double lowerBound, double upperBound) {
        return lowerBound > Difficulty.DIFFICULTY_LOWER_BOUND
                && upperBound <= Difficulty.DIFFICULTY_UPPER_BOUND
                && lowerBound <= upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    @Override
    public boolean test(Problem problem) {
        requireNonNull(problem);
        Difficulty difficulty = problem.getDifficulty();
        return difficulty.value >= lowerBound && difficulty.value <= upperBound;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DifficultyIsInRangePredicate) // instanceof handles nulls
            && lowerBound == ((DifficultyIsInRangePredicate) other).getLowerBound()
            && upperBound == ((DifficultyIsInRangePredicate) other).getUpperBound(); // state check
    }
}
