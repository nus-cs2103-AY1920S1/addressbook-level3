package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.scheduler.Reminder;
import seedu.address.model.scheduler.UniqueReminderList;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

/**
 * Wraps all data at the classroom level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Classroom implements ReadOnlyClassroom {

    private final UniqueStudentList students;
    private final UniqueAssignmentList assignments;
    private boolean isDisplayStudents = true;
    private final UniqueLessonList lessons;
    private final UniqueReminderList reminders;

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
        lessons = new UniqueLessonList();
        reminders = new UniqueReminderList();
    }

    public Classroom() {}

    /**
     * Creates an Classroom using the Students in the {@code toBeCopied}
     */
    public Classroom(ReadOnlyClassroom toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
    }

    /**
     * Resets the existing data of this {@code Classroom} with {@code newData}.
     */
    public void resetData(ReadOnlyClassroom newData) {
        requireNonNull(newData);
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

    /**
     * Adds a lessons to the classroom.
     * The lesson must not already exist in the classroom.
     */
    public void addLesson(Lesson p) {
        lessons.add(p);
    }

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the classroom.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        return assignments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Classroom // instanceof handles nulls
                && students.equals(((Classroom) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
