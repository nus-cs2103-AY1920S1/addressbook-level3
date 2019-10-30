package seedu.exercise.logic.parser.predicate;

import java.util.function.Predicate;

import seedu.exercise.model.resource.Exercise;

/**
 * Represents a {@code Predicate} that filters {@code Exercise} which matches the given property.
 */
public interface BasePropertyPredicate extends Predicate<Exercise> {

    boolean test(Exercise exercise);

}
