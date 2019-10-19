package seedu.address.storage.bio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bio.Goal;

/**
 * Jackson-friendly version of {@link Goal}.
 */
class JsonAdaptedGoals {

    private final String goals;

    /**
     * Constructs a {@code JsonAdaptedGoal} with the given {@code goals}.
     */
    @JsonCreator
    public JsonAdaptedGoals(String goals) {
        this.goals = goals;
    }

    /**
     * Converts a given {@code Goal} into this class for Jackson use.
     */
    public JsonAdaptedGoals(Goal source) {
        goals = source.goal;
    }

    @JsonValue
    public String getGoals() {
        return goals;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Goal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted goal.
     */
    public Goal toModelType() throws IllegalValueException {
        if (!Goal.isValidGoal(goals)) {
            throw new IllegalValueException(Goal.MESSAGE_CONSTRAINTS);
        }
        return new Goal(goals);
    }

}
