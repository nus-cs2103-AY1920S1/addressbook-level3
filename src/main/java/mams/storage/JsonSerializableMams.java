package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import mams.commons.exceptions.IllegalValueException;
import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.student.Student;

/**
 * An Immutable MAMS that is serializable to JSON format.
 */
@JsonRootName(value = "mams")
class JsonSerializableMams {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMams} with the given students.
     */
    @JsonCreator
    public JsonSerializableMams(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyMams} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMams}.
     */
    public JsonSerializableMams(ReadOnlyMams source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Mams} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Mams toModelType() throws IllegalValueException {
        Mams mams = new Mams();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (mams.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            mams.addStudent(student);
        }
        return mams;
    }

}
