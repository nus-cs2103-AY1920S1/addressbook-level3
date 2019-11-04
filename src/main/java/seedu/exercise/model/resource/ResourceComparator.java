package seedu.exercise.model.resource;

import java.util.Comparator;

/**
 * Holds comparators for sorting of resource lists.
 */
public class ResourceComparator {

    public static final Comparator<Exercise> DEFAULT_EXERCISE_COMPARATOR = new Comparator<Exercise>() {
        @Override
        public int compare(Exercise exercise, Exercise t1) {
            int dateCompare = -exercise.getDate().value.compareTo(t1.getDate().value);
            if (dateCompare == 0) {
                int nameCompare = exercise.getName().fullName.compareTo(t1.getName().fullName);
                return nameCompare;
            } else {
                return dateCompare;
            }
        }
    };

    public static final Comparator<Regime> DEFAULT_REGIME_COMPARATOR =
            Comparator.comparing(o -> o.getRegimeName().toString());

    public static final Comparator<Schedule> DEFAULT_SCHEDULE_COMPARATOR =
            Comparator.comparing(o -> o.getDate().value);
}
