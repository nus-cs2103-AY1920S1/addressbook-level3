package seedu.address.testutil;

import seedu.address.ui.feature.Feature;

/**
 * A utility class to help with building Feature objects.
 */
public class FeatureBuilder {

    private static final String DEFAULT_NAME = "calendar";

    private String name;

    public FeatureBuilder() {
        name = DEFAULT_NAME;
    }

    /**
     * Sets the {@code name} of the {@code FeatureBuilder} that we are building.
     */
    public FeatureBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Feature build() {
        return new Feature(name);
    }
}
