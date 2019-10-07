package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.ExamDate;
import seedu.address.model.module.ExamDuration;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;

/**
 * Jackson-friendly version of {@link Semester}.
 */
public class JsonAdaptedSemester {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Semester's %s field is missing!";

    private final String semesterNo;
    private final ArrayList<JsonAdaptedLesson> timetable = new ArrayList<>();
    private final String examDate;
    private final String examDuration;

    /**
     * Constructs a {@code JsonAdaptedSemester} with the given semester details.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semesterNo") String semesterNo,
                               @JsonProperty("timetable") List<JsonAdaptedLesson> timetable,
                               @JsonProperty("examDate") String examDate,
                               @JsonProperty("examDuration") String examDuration) {
        this.semesterNo = semesterNo;
        this.examDate = examDate;
        this.examDuration = examDuration;
        if (timetable != null) {
            this.timetable.addAll(timetable);
        }
    }

    /**
     * Converts a given {@code Semester} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterNo = source.getSemesterNo().toString();
        examDate = source.getExamDate().toString();
        examDuration = source.getExamDuration().toString();
        timetable.addAll(source.getTimetable().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Semester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Semester toModelType() throws IllegalValueException {
        if (semesterNo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SemesterNo.class.getSimpleName()));
        }
        final SemesterNo modelSemesterNo = new SemesterNo(semesterNo);

        if (examDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExamDate.class.getSimpleName()));
        }
        final ExamDate modelExamDate = new ExamDate(examDate);

        if (examDuration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExamDuration.class.getSimpleName()));
        }
        final ExamDuration modelExamDuration = new ExamDuration(examDuration);

        final List<Lesson> modelTimetable = new ArrayList<>();
        for (JsonAdaptedLesson lesson : timetable) {
            modelTimetable.add(lesson.toModelType());
        }

        return new Semester(modelSemesterNo, modelTimetable, modelExamDate, modelExamDuration);
    }
}
