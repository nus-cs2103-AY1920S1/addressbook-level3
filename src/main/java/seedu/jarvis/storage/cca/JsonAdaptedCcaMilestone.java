package seedu.jarvis.storage.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;

/**
 * Jackson-friendly version of {@link CcaMilestone}.
 */
public class JsonAdaptedCcaMilestone {
    public final String fullName;

    /**
     * Converts a given {@code CcaMilestone} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedCcaMilestone(String fullName) {
        this.fullName = fullName;
    }

    public JsonAdaptedCcaMilestone(CcaMilestone ccaMilestone) {
        fullName = ccaMilestone.fullName;
    }

    @JsonValue
    public String getFullName() {
        return fullName;
    }

    /**
     * Converts this Jackson-friendly adapted {@code CcaMilestone} object into the model's {@code CcaMilestone} object.
     *
     * @return {@code CcaMilestone} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code CcaMilestone}.
     */
    public CcaMilestone toModelType() throws IllegalValueException {
        if (!CcaMilestone.isValidCcaMilestone(fullName)) {
            throw new IllegalValueException(CcaMilestone.MESSAGE_CONSTRAINTS);
        }
        return new CcaMilestone(fullName);
    }
}
