package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyStudentRecord;
import seedu.address.model.StudentRecord;
import seedu.address.model.student.Student;

/**
 * An Immutable studentRecord that is serializable to JSON format.
 */
@JsonRootName(value = "studentRecord")
class JsonSerializableStudentRecord {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudentRecord} with the given students.
     */
    @JsonCreator
    public JsonSerializableStudentRecord(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyStudentRecord} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablestudentRecord}.
     */
    public JsonSerializableStudentRecord(ReadOnlyStudentRecord source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code studentRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentRecord toModelType() throws IllegalValueException {
        StudentRecord studentRecord = new StudentRecord();
        for (JsonAdaptedStudent jsonAdaptedstudent : students) {
            Student student = jsonAdaptedstudent.toModelType();
            if (studentRecord.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            studentRecord.addStudent(student);
        }
        return studentRecord;
    }

}
