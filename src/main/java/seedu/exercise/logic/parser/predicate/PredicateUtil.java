package seedu.exercise.logic.parser.predicate;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Contains Utility methods used for generating Predicates.
 */
public class PredicateUtil {

    public static final String OPERATION_TYPE_AND = "and";
    public static final String OPERATION_TYPE_OR = "or";

    public static final String OPERATION_TYPE_CONSTRAINTS = "Operation type should either be \'" + OPERATION_TYPE_AND
            + "\' or \'" + OPERATION_TYPE_OR + "\'";

    /**
     * {@code Predicate} that always evaluate to true
     */
    public static final Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    public static final Predicate<Regime> PREDICATE_SHOW_ALL_REGIMES = unused -> true;

    public static BasePropertyPredicate predicateShowExercisesWithMuscle(Set<Muscle> targetMuscles, boolean isStrict) {
        return new ExerciseMusclePredicate(targetMuscles, isStrict);
    }

    public static BasePropertyPredicate predicateShowExerciseWithCustomProperty(
            Map<String, String> targetCustomPropertiesMap, boolean isStrict) {
        return new ExerciseCustomPropertyPredicate(targetCustomPropertiesMap, isStrict);
    }

}
