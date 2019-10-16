package seedu.address.model.feature;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a feature shown in the feature box. Possible features include: 1) calendar, 2)
 * attendance and 3) performance.
 */
public class Feature {

    public static final String MESSAGE_CONSTRAINTS =
            "Please indicate one of the following features to view: 1) calender, 2) attendance, "
                    + "3) ";

    private final String name;

    public Feature(String featureName) {
        requireNonNull(featureName);
        checkArgument(isValidFeatureName(featureName), MESSAGE_CONSTRAINTS);
        this.name = featureName;
    }

    /**
     * Checks if the feature name provided by the user is valid.
     * @param featureName String provided by user following the view command
     * @return boolean indicating whether the feature name is valid
     */
    public static boolean isValidFeatureName(String featureName) {
        return featureName.equals("calendar")
                | featureName.equals("attendance")
                | featureName.equals("performance");
    }

    @Override
    public String toString() {
        return name;
    }
}
