package seedu.flashcard.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Choice;

/**
 * Jackson-friendly version of {@link Choice}.
 */
public class JsonAdaptedChoice {
    private final String choice;

    /**
     * Constructs a {@code JsonAdaptedChoice} with the given {@code choice}.
     */
    @JsonCreator
    public JsonAdaptedChoice(String choice) {
        this.choice = choice;
    }

    /**
     * Converts a given {@code Choice} into this class for Jackson use.
     */
    public JsonAdaptedChoice(Choice source) {
        choice = source.choice;
    }

    @JsonValue
    public String getChoice() {
        return choice;
    }

    /**
     * Converts this Jackson-friendly adapted Choice object into the model's {@code Choice} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Choice.
     */
    public Choice toModelType() throws IllegalValueException {
        if (!Choice.isValidChoice(choice)) {
            throw new IllegalValueException(Choice.MESSAGE_CONSTRAINTS);
        }
        return new Choice(choice);
    }
}
