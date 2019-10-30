package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.classroom.UniqueClassroomList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.scheduler.Reminder;
import seedu.address.model.scheduler.UniqueReminderList;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the classroom data.
 */
public class Notebook implements ReadOnlyNotebook {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Classroom currentClassroom;
    //private Caretaker caretaker;
    private final UniqueClassroomList classrooms;
    private final UniqueLessonList lessons;
    private final UniqueReminderList reminders;
    //private FilteredList<Student> filteredStudents;
    //private FilteredList<Assignment> filteredAssignments;
    //private FilteredList<Lesson> filteredLessons;
    //private FilteredList<Reminder> filteredReminders;
    //private LinkedHashMap<String, Classroom> classrooms;

    {
        //this.classrooms = new LinkedHashMap<>();
        //Classroom newClassroom = new Classroom();
        //classrooms.put(newClassroom.getClassroomName(),newClassroom);
        //setCurrentClassroom(newClassroom.getClassroomName());
        //this.caretaker = new Caretaker(new Memento(currentClassroom()), this.currentClassroom());
        lessons = new UniqueLessonList();
        reminders = new UniqueReminderList();
        classrooms = new UniqueClassroomList();
        //filteredStudents = new FilteredList<>(getStudentList());
        //filteredAssignments = new FilteredList<>(getAssignmentList());
        //filteredLessons = new FilteredList<>(getLessonList());
        //filteredReminders = new FilteredList<>(getReminderList());
    }

    public Notebook() {
        /*
        if (classrooms.isEmpty()) {
            Classroom newClassroom = new Classroom();
            classrooms.add(newClassroom);
            setCurrentClassroom(newClassroom);
        } else {
            Classroom firstClassroom = getClassroomList().get(0);
            setCurrentClassroom(firstClassroom);
        }
         */
    }

    public Notebook(ReadOnlyNotebook toBeCopied) {
        this();
        resetData(toBeCopied);

        if (classrooms.isEmpty()) {
            Classroom newClassroom = new Classroom();
            setCurrentClassroom(newClassroom);
        } else {
            Classroom firstClassroom = getClassroomList().get(0);
            setCurrentClassroom(firstClassroom);
        }
    }

    public Classroom currentClassroom() {
        return classrooms.get(currentClassroom);
    }

    public void resetData(ReadOnlyNotebook newData) {
        requireNonNull(newData);
        setClassrooms(newData.getClassroomList());
        setLessons(newData.getLessonList());
    }

    //=========== Notebook ================================================================================
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms.setClassrooms(classrooms);
    }

    public void setCurrentClassroom(Classroom classroom) {
        requireNonNull(classroom);
        if (hasClassroom(classroom)) {
            this.currentClassroom = classrooms.get(classroom);
        }
    }

    public void setClassroom(ReadOnlyClassroom classroom) {
        this.currentClassroom().resetData(classroom);
    }

    public boolean hasClassroom(Classroom classroom) {
        requireNonNull(classroom);
        return classrooms.contains(classroom);
    }

    /*
    public boolean hasClassroom(String classroomName) {
        requireNonNull(classroomName);
        return classrooms.contains(classroomName);
    }
    */


    public void addClassroom(Classroom classroom) {
        if (classrooms.isEmpty()) {
            classrooms.add(classroom);
            setCurrentClassroom(classroom);
        } else {
            classrooms.add(classroom);
        }
    }

    public ReadOnlyClassroom getCurrentClassroom() {
        return currentClassroom();
    }


    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return currentClassroom().hasStudent(student);
    }


    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return currentClassroom().hasAssignment(assignment);
    }


    public void deleteStudent(Student target) {
        currentClassroom().removeStudent(target);
    }


    public void deleteAssignment(Assignment target) {
        currentClassroom().removeAssignment(target);
    }


    public void addStudent(Student student) {
        currentClassroom().addStudent(student);
    }


    public void addAssignment(Assignment assignment) {
        currentClassroom().addAssignment(assignment);
    }


    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        currentClassroom().setStudent(target, editedStudent);
    }


    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);

        currentClassroom().setAssignment(target, editedAssignment);
    }

    public boolean isDisplayStudents() {
        return currentClassroom().isDisplayStudents();
    }

    public void displayStudents() {
        currentClassroom().displayStudents();
    }

    public void displayAssignments() {
        currentClassroom().displayAssignments();
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

    /**
     * Removes {@code key} from this {@code Classroom}.
     * {@code key} must exist in the classroom.
     */
    public void removeLesson(Lesson key) {
        lessons.remove(key);
    }


    /**
     * Replaces the given lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the classroom.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the address
     * book.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireNonNull(editedLesson);
        lessons.setLesson(target, editedLesson);
    }


    /**
     * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Classroom} backed by the internal list of
     * {@code Caretaker}
     */
    public ObservableList<Classroom> getClassroomList() {
        return classrooms.asUnmodifiableObservableList();
    }

    /*
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }
    */

    public ObservableList<Student> getStudentList() {
        return currentClassroom().getStudentList();
    }

    public ObservableList<Assignment> getAssignmentList() {
        return currentClassroom().getAssignmentList();
    }

    /*
    public List<Classroom> getClassroomList() {
        ArrayList<Classroom> classroomList = new ArrayList<Classroom>(classrooms.values());
        ObservableList<Classroom>
        return classroomList;
    }
     */

    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }


    //=========== Undo and Redo Operations =============================================================

    /*
    public ReadOnlyNotebook undo() {
        return caretaker.undo();
    }

    public boolean canUndo() {
        return caretaker.canUndo();
    }

    public ReadOnlyClassroom redo() {
        return caretaker.redo();
    }

    public boolean canRedo() {
        return caretaker.canRedo();
    }

    public void saveState() {
        caretaker.saveState();
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }
    */

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Notebook)) {
            return false;
        }

        // state check
        Notebook other = (Notebook) obj;
        return classrooms.equals(other.classrooms);
    }

}

