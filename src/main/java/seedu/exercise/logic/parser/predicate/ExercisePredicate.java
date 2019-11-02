package seedu.exercise.logic.parser.predicate;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.exercise.model.resource.Exercise;

/**
 * Tests whether an {@code Exercise} matches the {@code predicate}
 */
public class ExercisePredicate implements Predicate<Exercise> {
    private List<BasePropertyPredicate> predicates = new ArrayList<>();
    private boolean isStrict;

    @SafeVarargs
    public ExercisePredicate(boolean isStrict, BasePropertyPredicate... predicates) {
        requireNonNull(predicates);
        this.isStrict = isStrict;
        this.predicates = Arrays.asList(predicates);
    }


    @Override
    public boolean test(Exercise exercise) {
        if (isStrict) {
            return testStrict(exercise);
        } else {
            return testLoose(exercise);
        }
    }

    /**
     * Returns true if a {@code exercise} passes all the given {@code predicates}
     */
    private boolean testStrict(Exercise exercise) {
        for (Predicate<Exercise> predicate : predicates) {
            if (!(predicate.test(exercise))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a {@code exercise} passes at least one of the given {@code predicates}
     */
    private boolean testLoose(Exercise exercise) {
        for (Predicate<Exercise> predicate : predicates) {
            if (predicate.test(exercise)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof ExercisePredicate //instanceof handles null
                && isStrict == ((ExercisePredicate) other).isStrict
                && predicates.equals(((ExercisePredicate) other).predicates));
    }
}
