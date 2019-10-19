package seedu.address.storage.bio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bio.MedicalCondition;

/**
 * Jackson-friendly version of {@link MedicalCondition}.
 */
class JsonAdaptedMedicalConditions {

    private final String medicalConditions;

    /**
     * Constructs a {@code JsonAdaptedMedicalCondition} with the given {@code medicalConditions}.
     */
    @JsonCreator
    public JsonAdaptedMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    /**
     * Converts a given {@code MedicalCondition} into this class for Jackson use.
     */
    public JsonAdaptedMedicalConditions(MedicalCondition source) {
        medicalConditions = source.medicalCondition;
    }

    @JsonValue
    public String getMedicalConditions() {
        return medicalConditions;
    }

    /**
     * Converts this Jackson-friendly adapted medical condition object into the model's {@code MedicalCondition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medical condition.
     */
    public MedicalCondition toModelType() throws IllegalValueException {
        if (!MedicalCondition.isValidMedicalCondition(medicalConditions)) {
            throw new IllegalValueException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        return new MedicalCondition(medicalConditions);
    }

}
