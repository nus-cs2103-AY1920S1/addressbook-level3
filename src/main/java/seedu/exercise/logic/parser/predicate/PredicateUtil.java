package seedu.exercise.logic.parser.predicate;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;

//@@author kwekke
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

    public static BasePropertyPredicate getBasePredicateMuscle(Set<Muscle> targetMuscles, boolean isStrict) {
        return new ExerciseMusclePredicate(targetMuscles, isStrict);
    }

    public static BasePropertyPredicate getBasePredicateCustomProperty(
            Map<String, String> targetCustomPropertiesMap, boolean isStrict) {
        return new ExerciseCustomPropertyPredicate(targetCustomPropertiesMap, isStrict);
    }

    /**
     * Returns a {@code ExercisePredicate} that correspond to
     * {@code muscles}, {@code customProperties} and {@code isStrict}
     */
    public static ExercisePredicate getExercisePredicate(
            Set<Muscle> muscles, Map<String, String> customProperties, boolean isStrict) {
        assert (!(muscles.isEmpty() && customProperties.isEmpty()));

        BasePropertyPredicate musclePredicate = getBasePredicateMuscle(muscles, isStrict);
        BasePropertyPredicate customPropertiesPredicate = getBasePredicateCustomProperty(customProperties, isStrict);

        if (muscles.isEmpty()) {
            return new ExercisePredicate(isStrict, customPropertiesPredicate);
        }

        if (customProperties.isEmpty()) {
            return new ExercisePredicate(isStrict, musclePredicate);
        }

        ExercisePredicate predicate = new ExercisePredicate(isStrict, musclePredicate, customPropertiesPredicate);
        return predicate;
    }

}
