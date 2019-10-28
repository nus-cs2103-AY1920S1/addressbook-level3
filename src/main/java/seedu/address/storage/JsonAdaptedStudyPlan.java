package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link seedu.address.model.studyplan.StudyPlan}.
 */
class JsonAdaptedStudyPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Study Plan's %s field is missing!";

    private final int totalNumber; // this corresponds to the static field in StudyPlan: totalNumberOfStudyPlans
    private final String title;
    private final int index;
    private final List<JsonAdaptedSemester> semesters = new ArrayList<>();
    private final SemesterName currentSemester;
    private final List<JsonAdaptedTag> studyPlanTags = new ArrayList<>();

    // each study plan also keeps track of a unique module list and a unique tag list
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedTag> moduleTags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudyPlan} with the given StudyPlan details.
     */
    @JsonCreator
    public JsonAdaptedStudyPlan(@JsonProperty("totalNumber") int totalNumber,
                                @JsonProperty("title") String title, @JsonProperty("index") int index,
                                @JsonProperty("semesters") List<JsonAdaptedSemester> semesters,
                                @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                @JsonProperty("moduleTags") List<JsonAdaptedTag> moduleTags,
                                @JsonProperty("currentSemester") SemesterName currentSemester,
                                @JsonProperty("studyPlanTags") List<JsonAdaptedTag> studyPlanTags) {
        this.totalNumber = totalNumber;
        this.title = title;
        this.index = index;
        this.currentSemester = currentSemester;
        if (semesters != null) {
            this.semesters.addAll(semesters);
        }
        if (modules != null) {
            this.modules.addAll(modules);
        }
        if (moduleTags != null) {
            this.moduleTags.addAll(moduleTags);
        }
        if (studyPlanTags != null) {
            this.studyPlanTags.addAll(studyPlanTags);
        }
    }

    /**
     * Converts a given {@code StudyPlan} into this class for Jackson use.
     */
    public JsonAdaptedStudyPlan(StudyPlan source) {
        totalNumber = StudyPlan.getTotalNumberOfStudyPlans();
        title = source.getTitle().toString();
        index = source.getIndex();
        currentSemester = source.getCurrentSemester();

        for (Semester semesterToAdd : source.getSemesters()) {
            semesters.add(new JsonAdaptedSemester(semesterToAdd));
        }

        for (Module module : source.getModules().values()) {
            modules.add(new JsonAdaptedModule(module));
        }

        for (Tag tag : source.getModuleTags()) {
            if (!tag.isDefault()) {
                moduleTags.add(new JsonAdaptedTag(tag));
            }
        }

        for (Tag tag : source.getStudyPlanTags()) {
            studyPlanTags.add(new JsonAdaptedTag(tag));
        }
    }

    /**
     * Converts this Jackson-friendly adapted StudyPlan object into the model's {@code StudyPlan} object.
     * This will be skeletal study plan that is not yet activated.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted StudyPlan.
     */
    public StudyPlan toModelType() throws IllegalValueException {

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (index == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "index"));
        }

        final List<Semester> modelSemesters = new ArrayList<>();
        for (JsonAdaptedSemester semester : semesters) {
            modelSemesters.add(semester.toModelType());
        }

        final HashMap<String, Module> modelModules = new HashMap<>();
        for (JsonAdaptedModule module : modules) {
            modelModules.put(module.getModuleCode(), module.toModelType());
        }

        final List<Tag> modelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : moduleTags) {
            modelTags.add(tag.toModelType());
        }

        final List<Tag> modelStudyPlanTags = new ArrayList<>();
        for (JsonAdaptedTag tag : studyPlanTags) {
            modelStudyPlanTags.add(tag.toModelType());
        }

        StudyPlan result =
                new StudyPlan(modelTitle, index, modelSemesters, modelModules,
                        modelTags, currentSemester, modelStudyPlanTags);
        StudyPlan.setTotalNumberOfStudyPlans(totalNumber);

        return result;
    }
}
