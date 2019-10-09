package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * Jackson-friendly version of {@link seedu.address.model.studyplan.StudyPlan}.
 */
class JsonAdaptedStudyPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Study Plan's %s field is missing!";

    private final String title;
    private final int index;
    private final boolean isActive;
    private final List<JsonAdaptedSemester> semesters = new ArrayList<>();

    // each study plan also keeps track of a unique module list and a unique tag list
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudyPlan} with the given StudyPlan details.
     */
    @JsonCreator
    public JsonAdaptedStudyPlan(@JsonProperty("title") String title, @JsonProperty("index") int index,
                                @JsonProperty("isActive") boolean isActive,
                                @JsonProperty("semesters") List<JsonAdaptedSemester> semesters,
                                @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.title = title;
        this.index = index;
        this.isActive = isActive;
        if (semesters != null) {
            this.semesters.addAll(semesters);
        }
        if (modules != null) {
            this.modules.addAll(modules);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code StudyPlan} into this class for Jackson use.
     */
    public JsonAdaptedStudyPlan(StudyPlan source) {
        title = source.getTitle().toString();
        index = source.getIndex();
        isActive = source.isActive();

        Iterator<Semester> semesterIterator = source.getSemesters().iterator();
        while (semesterIterator.hasNext()) {
            Semester semesterToAdd = semesterIterator.next();
            semesters.add(new JsonAdaptedSemester(semesterToAdd));
        }

        Iterator<Module> moduleIterator = source.getModules().iterator();
        while (moduleIterator.hasNext()) {
            Module module = moduleIterator.next();
            modules.add(new JsonAdaptedModule(module));
        }

        Iterator<Tag> tagIterator = source.getTags().iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            tags.add(new JsonAdaptedTag(tag));
        }
    }

    /**
     * Converts this Jackson-friendly adapted StudyPlan object into the model's {@code StudyPlan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted StudyPlan.
     */
    public StudyPlan toModelType() throws IllegalValueException {
        final List<Semester> studyPlanSemesters = new ArrayList<>();
        for (JsonAdaptedSemester semester : semesters) {
            studyPlanSemesters.add(semester.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        // if (!Name.isValidName(name)) {
        //    throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        // }
        final Title modelTitle = new Title(title);

        if (index == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "index"));
        }
        final int modelIndex = index;

        // if (isActive == null) {
        //     throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
        //     Email.class.getSimpleName()));
        // }
        final boolean modelIsActive = isActive;

        final List<Semester> modelSemesters = new ArrayList<>();
        for (JsonAdaptedSemester semester : semesters) {
            modelSemesters.add(semester.toModelType());
        }

        final UniqueModuleList modelModules = new UniqueModuleList();
        for (JsonAdaptedModule module : modules) {
            modelModules.add(module.toModelType());
        }

        final UniqueTagList modelTags = new UniqueTagList();
        for (JsonAdaptedTag tag : tags) {
            modelTags.addUserTag(tag.toModelType());
        }

        return new StudyPlan(modelTitle, modelIndex, modelIsActive, modelSemesters, modelModules, modelTags);
    }
}
