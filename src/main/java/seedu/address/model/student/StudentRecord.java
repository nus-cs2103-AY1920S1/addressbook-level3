package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * Wraps all data at the njoyAssistant level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class StudentRecord implements ReadOnlyStudentRecord {

    private final UniqueStudentList students;

    {
        students = new UniqueStudentList();
    }

    public StudentRecord() {
    }

    /**
     * Creates a Student Record using the Students in the {@code toBeCopied}
     */
    public StudentRecord(ReadOnlyStudentRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Student list with {@code Students}.
     * {@code Students} must not contain duplicate Students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code studentRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyStudentRecord newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// Student-level operations

    /**
     * Returns true if a Student with the same identity as {@code Student} exists in the student list.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a Student to the student list.
     * The Student must not already exist in the student list.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given Student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the student list.
     * The Student identity of {@code editedStudent} must not be the same as another
     * existing Student in the student list.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code studentRecord}.
     * {@code key} must exist in the student list.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Returns student with specific index number
     *
     * @param index Index number of student
     * @return Student with index number.
     */
    public Student getStudent(int index) {
        return students.getStudent(index);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " Students";
        // TODO: refine later
    }

    /**
     * Gets an unmodifiable, observable list of all the students.
     *
     * @return A list of all the students.
     */
    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    /**
     * Gets a list of all the students.
     *
     * @return A list of all the students.
     */
    public String getStudentSummary() {
        return students.getStudentList();
    }

    /**
     * Gets the index of a student in the list if present.
     *
     * @param student Student who's index we want to find out.
     * @return Index of the student if present, else Optional.Empty().
     */
    public Optional<Index> getIndex(Student student) {
        requireNonNull(student);
        return students.getIndex(student);
    }

    /**
     * Adds the student with a specific index to the student list.
     *
     * @param index   Index of student that is specified.
     * @param student Student to be added.
     */
    public void setStudentWithIndex(Index index, Student student) {
        requireAllNonNull(index, student);
        students.setStudent(index, student);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentRecord // instanceof handles nulls
                && students.equals(((StudentRecord) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
