package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medical.MedicalHistory;

/**
 * Jackson-friendly version of {@link MedicalHistory}.
 */
class JsonAdaptedMedicalHistory {

    private final String medicalHistoryName;

    /**
     * Constructs a {@code JsonAdaptedMedicalHistory} with the given {@code medicalHistoryName}.
     */
    @JsonCreator
    public JsonAdaptedMedicalHistory(String medicalHistoryName) {
        this.medicalHistoryName = medicalHistoryName;
    }

    /**
     * Converts a given {@code MedicalHistory} into this class for Jackson use.
     */
    public JsonAdaptedMedicalHistory(MedicalHistory source) {
        medicalHistoryName = source.medicalHistoryName;
    }

    @JsonValue
    public String getTagName() {
        return medicalHistoryName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code MedicalHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public MedicalHistory toModelType() throws IllegalValueException {
        if (!MedicalHistory.isValidMedicalHistoryName(medicalHistoryName)) {
            throw new IllegalValueException(MedicalHistory.MESSAGE_CONSTRAINTS);
        }
        return new MedicalHistory(medicalHistoryName);
    }

}
