package seedu.address.model.feature;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
