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

    private String commitMessage;
    private JsonAdaptedStudyPlan studyPlan;

    /**
     * Constructs a {@code JsonAdaptedCommit} with the given {@code index} and {@code JsonAdaptedStudyPlan}.
     */
    @JsonCreator
    public JsonAdaptedCommit(@JsonProperty("studyPlan") JsonAdaptedStudyPlan studyPlan,
                             @JsonProperty("commitMessage") String commitMessage) {
        this.studyPlan = studyPlan;
        this.commitMessage = commitMessage;
    }

    /**
     * Converts a given {@code Commit} into this class for Jackson use.
     */
    public JsonAdaptedCommit(Commit source) {
        studyPlan = new JsonAdaptedStudyPlan(source.getStudyPlan());
        commitMessage = source.getCommitMessage();
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public JsonAdaptedStudyPlan getStudyPlan() {
        return studyPlan;
    }

    /**
     * Converts this Jackson-friendly adapted commit object into the model's {@code Commit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Commit.
     */
    public Commit toModelType() throws IllegalValueException {
        if (studyPlan == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudyPlan.class.getSimpleName()));
        }
        StudyPlan modelStudyPlan = studyPlan.toModelType();

        if (commitMessage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "commit message"));
        }
        String modelCommitMessage = commitMessage;

        return new Commit(modelStudyPlan, modelCommitMessage);
    }
}
