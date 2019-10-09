package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.versiontracking.Commit;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.StudyPlanCommitManager;

/**
 * Jackson-friendly version of {@link seedu.address.model.versiontracking.StudyPlanCommitManager}.
 */
class JsonAdaptedStudyPlanCommitManager {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Study plan commit manager's %s field is missing!";

    private int studyPlanIndex;
    private List<JsonAdaptedCommit> commits = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudyPlanCommitManager} with the given details.
     */
    @JsonCreator
    public JsonAdaptedStudyPlanCommitManager(
            @JsonProperty("studyPlanIndex") int studyPlanIndex,
            @JsonProperty("commits") List<JsonAdaptedCommit> commits) {
        this.studyPlanIndex = studyPlanIndex;
        if (commits != null) {
            this.commits.addAll(commits);
        }
    }

    /**
     * Converts a given {@code StudyPlanCommitManager} into this class for Jackson use.
     */
    public JsonAdaptedStudyPlanCommitManager(StudyPlanCommitManager source) {
        studyPlanIndex = source.getStudyPlanIndex();
        List<Commit> commitList = source.getCommitList().getCommits();
        List<JsonAdaptedCommit> adaptedCommitList = commitList.stream()
                                                              .map(JsonAdaptedCommit::new)
                                                              .collect(Collectors.toList());
        commits = adaptedCommitList;
    }

    /**
     * Converts this Jackson-friendly adapted StudyPlanCommitManager object
     * into the model's {@code StudyPlanCommitManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted StudyPlanCommitManager.
     */
    public StudyPlanCommitManager toModelType() throws IllegalValueException {
        int modelStudyPlanIndex = studyPlanIndex;
        List<Commit> commitList = new ArrayList<>();
        for (JsonAdaptedCommit adaptedCommit : commits) {
            Commit commit = adaptedCommit.toModelType();
            commitList.add(commit);
        }
        CommitList modelCommitList = new CommitList(commitList);

        return new StudyPlanCommitManager(modelStudyPlanIndex, modelCommitList);
    }
}
