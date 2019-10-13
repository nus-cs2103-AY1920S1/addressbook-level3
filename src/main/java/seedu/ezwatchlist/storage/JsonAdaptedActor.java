package seedu.ezwatchlist.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Jackson-friendly version of {@link Actor}.
 */
class JsonAdaptedActor {

    private final String actorName;

    /**
     * Constructs a {@code JsonAdaptedActor} with the given {@code actorName}.
     */
    @JsonCreator
    public JsonAdaptedActor(String actorName) {
        this.actorName = actorName;
    }

    /**
     * Converts a given {@code Actor} into this class for Jackson use.
     */
    public JsonAdaptedActor(Actor source) {
        actorName = source.actorName;
    }

    @JsonValue
    public String getActorName() {
        return actorName;
    }

    /**
     * Converts this Jackson-friendly adapted actor object into the model's {@code Actor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted actor.
     */
    public Actor toModelType() throws IllegalValueException {
        if (!Actor.isValidActorName(actorName)) {
            throw new IllegalValueException(Actor.MESSAGE_CONSTRAINTS);
        }
        return new Actor(actorName);
    }

}
