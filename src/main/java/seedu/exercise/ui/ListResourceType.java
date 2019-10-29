package seedu.exercise.ui;

/**
 * Encapsulates the different list of resource types to be displayed in GUI.
 */
public enum ListResourceType {
    NULL,
    EXERCISE,
    REGIME,
    SCHEDULE,
    SUGGESTION;

    public static final String LIST_RESOURCE_TYPE_CONSTRAINTS =
            "List resource type should be one of the following: exercise, regime, schedule or suggestion";

}
