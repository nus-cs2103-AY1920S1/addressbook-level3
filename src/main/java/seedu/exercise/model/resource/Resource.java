package seedu.exercise.model.resource;

import seedu.exercise.storage.resource.JsonAdaptedResource;

/**
 * Encapsulates the various resources that will be tracked by the app.
 * Resources tracked by the app are {@code Exercise}, {@code Regime} and {@code Schedule}.
 */
public abstract class Resource {

    public abstract boolean isSameResource(Resource otherResource);

    public abstract JsonAdaptedResource toJsonType();

}
