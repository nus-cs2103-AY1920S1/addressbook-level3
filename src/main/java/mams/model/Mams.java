package mams.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import mams.model.appeal.Appeal;
import mams.model.appeal.UniqueAppealList;
import mams.model.module.Module;
import mams.model.module.UniqueModuleList;
import mams.model.student.Student;
import mams.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Mams implements ReadOnlyMams {

    private final UniqueStudentList students;
    private final UniqueModuleList modules;
    private final UniqueAppealList appeals;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        modules = new UniqueModuleList();
        appeals = new UniqueAppealList();
    }

    public Mams() {}

    /**
     * Creates an Mams using the Students in the {@code toBeCopied}
     */
    public Mams(ReadOnlyMams toBeCopied) {
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

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code students} must not contain duplicate students.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the appeal list with {@code appeals}.
     * {@code appeals} must not contain duplicate appeals.
     */
    public void setAppeals(List<Appeal> appeals) {
        this.appeals.setAppeals(appeals);
    }

    /**
     * Resets the existing data of this {@code Mams} with {@code newData}.
     */
    public void resetData(ReadOnlyMams newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setModules(newData.getModuleList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in MAMS.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to MAMS.
     * The student must not already exist in MAMS.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in MAMS.
     * The student identity of {@code editedStudent} must not be the
     * same as another existing student in MAMS.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from {@code Mams}.
     * {@code key} must exist in MAMS.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }


    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in MAMS.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to MAMS.
     * The module must not already exist in MAMS.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in MAMS.
     * The student identity of {@code editedModule} must not be the
     * same as another existing module in MAMS.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from {@code Mams}.
     * {@code key} must exist in MAMS.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    //// appeal-level operations

    /**
     * Returns true if an appeal with the same identity as {@code appeal} exists in MAMS.
     */
    public boolean hasAppeal(Appeal appeal) {
        requireNonNull(appeal);
        return appeals.contains(appeal);
    }

    /**
     * Adds an appeal to MAMS.
     * The appeal must not already exist in MAMS.
     */
    public void addAppeal(Appeal appeal) {
        appeals.add(appeal);
    }

    /**
     * Replaces the given Appeal {@code target} in the list with {@code editedAppeal}.
     * {@code target} must exist in MAMS.
     * The appeal identity of {@code editedAppeal} must not be the
     * same as another existing appeal in MAMS.
     */
    public void setAppeal(Appeal target, Appeal editedAppeal) {
        requireNonNull(editedAppeal);

        appeals.setAppeal(target, editedAppeal);
    }

    /**
     * Removes {@code key} from {@code Mams}.
     * {@code key} must exist in MAMS.
     */
    public void removeAppeal(Appeal key) {
        appeals.remove(key);
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

    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    public ObservableList<Appeal> getAppealList() {
        return appeals.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mams // instanceof handles nulls
                && students.equals(((Mams) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }


}
