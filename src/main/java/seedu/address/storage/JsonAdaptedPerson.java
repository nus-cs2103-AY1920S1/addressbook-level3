package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String picture;
    private final String classId;
    private final String attendance;
    private final String result;
    private final String participation;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("picture") String picture,
            @JsonProperty("classId") String classId, @JsonProperty("attendance") String attendance,
                             @JsonProperty("result") String result,
                             @JsonProperty("participation") String participation) {
        this.name = name;
        this.picture = picture;
        this.classId = classId;
        this.attendance = attendance;
        this.result = result;
        this.participation = participation;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        picture = source.getPicture().value;
        classId = source.getClassId().value;
        attendance = source.getAttendance().value;
        result = source.getResult().value;
        participation = source.getParticipation().value;
    }
    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (picture == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Picture.class.getSimpleName()));
        }
        if (!Picture.isValidPicture(picture) && !("null".equals(picture))) {
            throw new IllegalValueException(Picture.MESSAGE_CONSTRAINTS);
        }
        final Picture modelPicture = new Picture(picture);

        if (classId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassId.class.getSimpleName()));
        }
        if (!ClassId.isValidClassId(classId)) {
            throw new IllegalValueException(ClassId.MESSAGE_CONSTRAINTS);
        }
        final ClassId modelClassId = new ClassId(classId);

        if (attendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Attendance.class.getSimpleName()));
        }
        if (!Attendance.isValidAttendance(attendance)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        final Attendance modelAttendance = new Attendance(attendance);

        if (result == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Result.class.getSimpleName()));
        }
        if (!Result.isValidResult(result)) {
            throw new IllegalValueException(Result.MESSAGE_CONSTRAINTS);
        }
        final Result modelResult = new Result(result);

        if (participation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Participation.class.getSimpleName()));
        }
        if (!Participation.isValidParticipation(participation)) {
            throw new IllegalValueException(Participation.MESSAGE_CONSTRAINTS);
        }
        final Participation modelParticipation = new Participation(participation);

        Person modelPerson = new Person(modelName, modelPicture, modelClassId,
                modelAttendance, modelResult, modelParticipation);

        return modelPerson;
    }

}
