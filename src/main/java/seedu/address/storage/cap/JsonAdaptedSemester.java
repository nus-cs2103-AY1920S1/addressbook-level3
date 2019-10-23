package seedu.address.storage.cap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.person.AcademicYear;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.SemesterPeriod;


/**
 * Jackson-friendly version of {@link Semester}.
 */
class JsonAdaptedSemester {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final int semesterPeriod;
    private final String academicYear;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semester") int semester,
                               @JsonProperty("academic year") String academicYear) {
        this.semesterPeriod = semester;
        this.academicYear = academicYear;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterPeriod = source.getSemesterPeriod().getSemesterPeriod();
        academicYear = source.getAcademicYear().getAcademicYear();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return a Module
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
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
