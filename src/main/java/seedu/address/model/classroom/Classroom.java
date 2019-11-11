package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
//import seedu.address.model.lesson.Lesson;
//import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

/**
 * Wraps all data at the classroom level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Classroom implements ReadOnlyClassroom {

    private String classroomName = "My First Classroom";
    private final UniqueStudentList students;
    private final UniqueAssignmentList assignments;
    private boolean isDisplayStudents = true;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        assignments = new UniqueAssignmentList();
    }

    public Classroom() {}

    public Classroom(String classroomName) {
        this();
        setClassroomName(classroomName);
    }

    /**
     * Creates an Classroom using the Students in the {@code toBeCopied}
     */
    public Classroom(ReadOnlyClassroom toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    @Override
    public void setClassroomName(String name) {
        this.classroomName = name;
    }
    @Override
    public String getClassroomName() {
        return this.classroomName;
    }

    public String getStudentLength() {
        return students.getLength();
    }

    public String getAssignmentLength() {
        return assignments.getLength();
    }

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
        for (Assignment assignment : assignments) {
            if (assignment.getGrades().isEmpty()) {
                assignment.initialiseGrades(getStudentNameList());
            }
        }
    }

    /**
     * Resets the existing data of this {@code Classroom} with {@code newData}.
     */
    public void resetData(ReadOnlyClassroom newData) {
        requireNonNull(newData);
        setClassroomName(newData.getClassroomName());
        setStudents(newData.getStudentList());
        setAssignments(newData.getAssignmentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the classroom.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns true if an assignment with the same identity as {@code assignment} exists in the classroom.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return assignments.contains(assignment);
    }

    /**
     * Adds a student to the classroom.
     * The student must not already exist in the classroom.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Adds an assignment to the classroom.
     * The assignment must not already exist in the classroom.
     */
    public void addAssignment(Assignment p) {
        assignments.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the classroom.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the classroom.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Replaces the given assignment {@code target} in the list with {@code editedAssignment}.
     * {@code target} must exist in the classroom.
     * The assignment identity of {@code editedAssignment} must not be the same as another existing assignment in the
     * classroom.
     */
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireNonNull(editedAssignment);

        assignments.setAssignment(target, editedAssignment);
    }

    /**
     * Removes {@code key} from this {@code Classroom}.
     * {@code key} must exist in the classroom.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Removes {@code key} from this {@code Classroom}.
     * {@code key} must exist in the classroom.
     */
    public void removeAssignment(Assignment key) {
        assignments.remove(key);
    }

    public boolean isDisplayStudents() {
        return this.isDisplayStudents;
    }

    public void displayStudents() {
        this.isDisplayStudents = true;
    }

    public void displayAssignments() {
        this.isDisplayStudents = false;
    }

    //// util methods

    @Override
    public String toString() {
        return classroomName;
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    private List<String> getStudentNameList() {
        List<String> toReturn = new ArrayList<>();
        for (Student student : getStudentList()) {
            toReturn.add(student.getName().toString());
        }
        return toReturn;
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        return assignments.asUnmodifiableObservableList();
    }

    /*
    @Override
    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }
    */

    /**
     * Returns true if both classrooms of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two classrooms.
     */
    public boolean isSameClassroom(Classroom otherClassroom) {
        if (otherClassroom == this) {
            return true;
        }

        return otherClassroom != null
                && otherClassroom.getClassroomName().equals(getClassroomName());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Classroom // instanceof handles nulls
                && classroomName.equals(((Classroom) other).classroomName)
                && students.equals(((Classroom) other).students)
                && assignments.equals(((Classroom) other).assignments));
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, assignments);
    }
}
