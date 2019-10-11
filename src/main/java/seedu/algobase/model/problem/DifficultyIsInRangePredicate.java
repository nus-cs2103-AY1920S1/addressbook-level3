package seedu.algobase.model.problem;

import java.util.function.Predicate;

/**
 * Tests that a {@code Problem}'s {@code Difficulty} is in the range [{@code lowerBound}, {@code upperBound}].
 */
public class DifficultyIsInRangePredicate implements Predicate<Problem> {

    private final double lowerBound;
    private final double upperBound;

    public DifficultyIsInRangePredicate(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean test(Problem problem) {
        Difficulty difficulty = problem.getDifficulty();
        return difficulty.value >= lowerBound && difficulty.value <= upperBound;
    }
}
