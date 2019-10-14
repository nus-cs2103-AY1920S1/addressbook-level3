package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.Commit;

/**
 * Jackson-friendly version of {@link seedu.address.model.versiontracking.Commit}.
 */
class JsonAdaptedCommit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Commit's %s field is missing!";

    private int index;
    private JsonAdaptedStudyPlan studyPlan;

    /**
     * Constructs a {@code JsonAdaptedCommit} with the given {@code index} and {@code JsonAdaptedStudyPlan}.
     */
    @JsonCreator
    public JsonAdaptedCommit(@JsonProperty("index") int index,
                             @JsonProperty("studyPlan") JsonAdaptedStudyPlan studyPlan) {
        this.index = index;
        this.studyPlan = studyPlan;
    }

    /**
     * Converts a given {@code Commit} into this class for Jackson use.
     */
    public JsonAdaptedCommit(Commit source) {
        index = source.getIndex();
        studyPlan = new JsonAdaptedStudyPlan(source.getStudyPlan());
    }

    /**
     * Converts this Jackson-friendly adapted commit object into the model's {@code Commit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Commit.
     */
    public Commit toModelType() throws IllegalValueException {
        StudyPlan modelStudyPlan = studyPlan.toModelType();
        int modelIndex = index;

        return new Commit(modelIndex, modelStudyPlan);
    }
}
