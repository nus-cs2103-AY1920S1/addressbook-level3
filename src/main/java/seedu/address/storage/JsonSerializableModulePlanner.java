package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.VersionedModulePlanner;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.UniqueStudyPlanList;
import seedu.address.model.versiontracking.VersionTrackingManager;

/**
 * An Immutable ModulePlanner that is serializable to JSON format.
 */
@JsonRootName(value = "moduleplanner")
class JsonSerializableModulePlanner {

    public static final int NO_ACTIVE_STUDY_PLAN = -1;

    public static final String MESSAGE_DUPLICATE_STUDY_PLAN = "Study Plan list contains duplicate study plan(s).";

    private final List<JsonAdaptedStudyPlan> studyPlans = new ArrayList<>();
    private final int activeStudyPlanIndex;
    private final JsonAdaptedVersionTrackingManager manager;
    private final String currentSemester;

    /**
     * Constructs a {@code JsonSerializableModulePlanner} with the given study plans.
     */
    @JsonCreator
    public JsonSerializableModulePlanner(@JsonProperty("studyPlans") List<JsonAdaptedStudyPlan> studyPlans,
                                         @JsonProperty("activeStudyPlanIndex") int activeStudyPlanIndex,
                                         @JsonProperty("manager") JsonAdaptedVersionTrackingManager manager,
                                         @JsonProperty("currentSemester") String currentSemester) {
        this.studyPlans.addAll(studyPlans);
        this.activeStudyPlanIndex = activeStudyPlanIndex;
        this.manager = manager;
        this.currentSemester = currentSemester;
    }

    /**
     * Converts a given {@code ReadOnlyModulePlanner} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModulePlanner}.
     */
    public JsonSerializableModulePlanner(ReadOnlyModulePlanner source) {
        ObservableList<StudyPlan> observableList = source.getStudyPlanList();
        for (StudyPlan studyPlan : observableList) {
            studyPlans.add(new JsonAdaptedStudyPlan(studyPlan));
        }

        if (source.getActiveStudyPlan() != null) {
            activeStudyPlanIndex = source.getActiveStudyPlan().getIndex();
        } else {
            activeStudyPlanIndex = NO_ACTIVE_STUDY_PLAN;
        }

        manager = new JsonAdaptedVersionTrackingManager(source.getVersionTrackingManager());
        if (source.getCurrentSemester() == null) {
            currentSemester = "Y1S1";
        } else {
            currentSemester = source.getCurrentSemester().toString();
        }
    }

    /**
     * Converts this module planner into the model's {@code ModulePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModulePlanner toModelType(ModulesInfo modulesInfo) throws IllegalValueException {
        List<StudyPlan> modelStudyPlans = new ArrayList<>();
        for (JsonAdaptedStudyPlan adaptedStudyPlan : studyPlans) {
            StudyPlan studyPlan = adaptedStudyPlan.toModelType();
            modelStudyPlans.add(studyPlan);
        }
        UniqueStudyPlanList modelUniqueStudyPlanList = new UniqueStudyPlanList();
        modelUniqueStudyPlanList.shallowSetStudyPlans(modelStudyPlans);

        VersionTrackingManager modelManager = manager.toModelType();

        ModulePlanner modulePlanner = new VersionedModulePlanner(modelUniqueStudyPlanList,
                modulesInfo, modelManager);

        modulePlanner.activateStudyPlan(activeStudyPlanIndex);

        if (currentSemester == null) {
            modulePlanner.setCurrentSemester(SemesterName.Y1S1);
        } else {
            modulePlanner.setCurrentSemester(SemesterName.valueOf(currentSemester));
        }

        return modulePlanner;
    }
}
