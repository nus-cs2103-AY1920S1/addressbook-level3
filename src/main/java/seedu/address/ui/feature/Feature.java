package seedu.address.ui.feature;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a feature shown in the feature box. Possible features include: 1) calendar, 2)
 * attendance and 3) performance.
 */
public class Feature {

    public static final String MESSAGE_CONSTRAINTS = "You have provided an invalid feature. "
            + "Please indicate one of the following features to view: 1) calendar, 2) attendance, "
            + "3) performance, 4) records.";
    public static final String MESSAGE_NO_EVENT = "Please provide an event name to view the records for.\n"
        + "Example: view records freestyle 50m";

    private final String name;
    private String eventName;

    /**
     * Constructs a Feature for viewing calendar, attendance, or performance.
     */
    public Feature(String featureName) {
        requireNonNull(featureName);
        checkArgument(isValidFeatureName(featureName), MESSAGE_CONSTRAINTS);
        this.name = featureName;
    }

    /**
     * Constructs a Feature for viewing performance records.
     */
    public Feature(String featureName, String eventName) {
        requireNonNull(featureName);
        checkArgument(isValidFeatureName(featureName), MESSAGE_CONSTRAINTS);
        requireNonNull(eventName);
        this.name = featureName;
        this.eventName = eventName;
    }

    public String getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    /**
     * Checks if the feature name provided by the user is valid.
     * @param featureName String provided by user following the view command
     * @return boolean indicating whether the feature name is valid
     */
    public static boolean isValidFeatureName(String featureName) {
        return featureName.equals("calendar")
                | featureName.equals("attendance")
                | featureName.equals("performance")
                | featureName.equals("records");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Feature
                && name.equals(((Feature) other).name));
    }
}
