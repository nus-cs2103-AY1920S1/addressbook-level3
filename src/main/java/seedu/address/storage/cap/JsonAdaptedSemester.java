package seedu.address.storage.cap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.module.AcademicYear;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.SemesterPeriod;


/**
 * Jackson-friendly version of {@link Semester}.
 */
class JsonAdaptedSemester {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final int semesterPeriod;
    private final String academicYear;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given semester details.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semester") int semester,
                               @JsonProperty("academic year") String academicYear) {
        this.semesterPeriod = semester;
        this.academicYear = academicYear;
    }

    /**
     * Converts a given {@code Semester} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterPeriod = source.getSemesterPeriod().getSemesterPeriod();
        academicYear = source.getAcademicYear().getAcademicYear();
    }

    /**
     * Converts this Jackson-friendly adapted semester object into the model's {@code Semester} object.
     *
     * @return a Semester
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Semester toModelType() throws IllegalValueException {

        if (academicYear == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, AcademicYear.class.getSimpleName()));
        }

        final SemesterPeriod modelSemesterPeriod = new SemesterPeriod(semesterPeriod);
        final AcademicYear modelAcademicYear = new AcademicYear(academicYear);

        return new Semester(modelSemesterPeriod, modelAcademicYear);
    }

}
