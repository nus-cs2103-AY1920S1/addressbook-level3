package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;


/**
 * Jackson-friendly version of {@link seedu.address.model.studyplan.StudyPlan}.
 */
class JsonAdaptedStudyPlan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Study Plan's %s field is missing!";

    private final String title;
    private final int index;
    private final boolean isActive;
    private final List<JsonAdaptedSemester> semesters = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudyPlan} with the given StudyPlan details.
     */
    @JsonCreator
    public JsonAdaptedStudyPlan(@JsonProperty("title") String title, @JsonProperty("index") int index,
                                @JsonProperty("isActive") boolean isActive,
                                @JsonProperty("semesters") List<JsonAdaptedSemester> semesters) {
        this.title = title;
        this.index = index;
        this.isActive = isActive;
        if (semesters != null) {
            this.semesters.addAll(semesters);
        }
    }

    /**
     * Converts a given {@code StudyPlan} into this class for Jackson use.
     */
    public JsonAdaptedStudyPlan(StudyPlan source) {
        title = source.getTitle().toString();
        index = source.getIndex();
        isActive = source.isActive();
        // TODO: not just name! copy modules etc also
        semesters.addAll(Arrays.asList(source.getSemesterList()).stream()
                .map(Object::toString)
                .map(JsonAdaptedSemester::new)
                .collect(Collectors.toList()));
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

        final Set<Semester> modelSemesters = new HashSet<>(studyPlanSemesters);
        return new StudyPlan(modelTitle, modelIndex, modelIsActive, modelSemesters);
    }

}
