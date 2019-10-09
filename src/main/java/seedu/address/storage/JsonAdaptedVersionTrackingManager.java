package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.versiontracking.StudyPlanCommitManager;
import seedu.address.model.versiontracking.StudyPlanCommitManagerList;
import seedu.address.model.versiontracking.VersionTrackingManager;


/**
 * Jackson-friendly version of {@link seedu.address.model.versiontracking.VersionTrackingManager}.
 */
class JsonAdaptedVersionTrackingManager {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Version tracking manager's %s field is missing!";

    private final List<JsonAdaptedStudyPlanCommitManager> managers = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedVersionTrackingManager} with the given details.
     */
    @JsonCreator
    public JsonAdaptedVersionTrackingManager(
            @JsonProperty("managers") List<JsonAdaptedStudyPlanCommitManager> managers) {
        if (managers != null) {
            this.managers.addAll(managers);
        }
    }

    /**
     * Converts a given {@code VersionTrackingManager} into this class for Jackson use.
     */
    public JsonAdaptedVersionTrackingManager(VersionTrackingManager source) {
        List<StudyPlanCommitManager> sourceManagers =
                source.getStudyPlanCommitManagerList().getStudyPlanCommitManagers();
        if (managers != null) {
            managers.addAll(sourceManagers.stream()
                                          .map(JsonAdaptedStudyPlanCommitManager::new)
                                          .collect(Collectors.toList()));
        }
    }

    /**
     * Converts this Jackson-friendly adapted VersionTrackingManager object
     * into the model's {@code VersionTrackingManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted VersionTrackingManager.
     */
    public VersionTrackingManager toModelType() throws IllegalValueException {
        List<StudyPlanCommitManager> modelManagers = new ArrayList<>();
        for (JsonAdaptedStudyPlanCommitManager adaptedManager : managers) {
            modelManagers.add(adaptedManager.toModelType());
        }
        StudyPlanCommitManagerList modelManagerList = new StudyPlanCommitManagerList(modelManagers);

        return new VersionTrackingManager(modelManagerList);
    }
}
