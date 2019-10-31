package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.student.Student;

/**
 * An Immutable Classroom that is serializable to JSON format.
 */
@JsonRootName(value = "classroom")
class JsonSerializableClassroom {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignments list contains duplicate assignment(s).";

    private String classroomName;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClassroom} with the given students.
     */
    @JsonCreator
    public JsonSerializableClassroom(@JsonProperty("classroomName") String classroomName,
                                     @JsonProperty("students") List<JsonAdaptedStudent> students,
                                     @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.classroomName = classroomName;
        this.students.addAll(students);
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code ReadOnlyClassroom} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClassroom}.
     */
    public JsonSerializableClassroom(ReadOnlyClassroom source) {
        classroomName = source.getClassroomName();
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
        assignments.addAll(source.getAssignmentList().stream().map(JsonAdaptedAssignment::new).collect(Collectors
                .toList()));
    }

    /**
     * Converts this classroom into the model's {@code Classroom} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Classroom toModelType() throws IllegalValueException {
        Classroom classroom = new Classroom();
        classroom.setClassroomName(classroomName);
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (classroom.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            classroom.addStudent(student);
        }
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment assignment = jsonAdaptedAssignment.toModelType();
            if (classroom.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
            classroom.addAssignment(assignment);
        }
        return classroom;
    }

}
