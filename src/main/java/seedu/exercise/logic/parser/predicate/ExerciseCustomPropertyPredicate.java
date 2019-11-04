package seedu.exercise.logic.parser.predicate;

import java.util.Map;

import seedu.exercise.model.resource.Exercise;

/**
 * Tests whether an {@code Exercise} matches the {@code predicate}
 */
public class ExerciseCustomPropertyPredicate implements BasePropertyPredicate {

    private final Map<String, String> customProperties;
    private final boolean isStrict;

    public ExerciseCustomPropertyPredicate(Map<String, String> customProperties, boolean isStrict) {
        this.customProperties = customProperties;
        this.isStrict = isStrict;
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
     * Returns true if a {@code exercise} has all the {@code CustomProperty} targeted
     */
    private boolean testStrict(Exercise exercise) {
        Map<String, String> exerciseCustomProperties = exercise.getCustomPropertiesMap();
        for (String key : customProperties.keySet()) {
            if (!(customProperties.get(key).equals(exerciseCustomProperties.get(key)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a {@code exercise} has at least one {@code CustomProperty} targeted
     */
    private boolean testLoose(Exercise exercise) {
        Map<String, String> exerciseCustomProperties = exercise.getCustomPropertiesMap();
        for (String key : customProperties.keySet()) {
            if (customProperties.get(key).equals(exerciseCustomProperties.get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof ExerciseCustomPropertyPredicate //instanceof handles null
            && customProperties.equals(((ExerciseCustomPropertyPredicate) other).customProperties)
            && isStrict == ((ExerciseCustomPropertyPredicate) other).isStrict);
    }

}
