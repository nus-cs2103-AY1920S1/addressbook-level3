package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
//import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.classroom.UniqueClassroomList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.lesson.UniqueLessonWeekList;
import seedu.address.model.student.Student;
//import seedu.address.model.student.UniqueStudentList;

/**
 * Represents the in-memory model of the classroom data.
 */
public class Notebook implements ReadOnlyNotebook {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);


    private Classroom currentClassroom;
    private final UniqueClassroomList classrooms;
    private final UniqueLessonList lessons;
    private final UniqueLessonWeekList lessonLists;

    {
        lessons = new UniqueLessonList();
        classrooms = new UniqueClassroomList();
        lessonLists = new UniqueLessonWeekList();
        for (int i = 0; i < 7; i++) {
            lessonLists.add(new UniqueLessonList());
        }
    }

    public Notebook() {
    }

    public Notebook(ReadOnlyNotebook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the data of the notebook to the newData.
     * @param newData notebook to reset data to.
     */
    public void resetData(ReadOnlyNotebook newData) {
        requireNonNull(newData);
        setClassrooms(newData.getClassroomList());

        if (currentClassroom != null) {
            setCurrentClassroom(newData.getCurrentClassroom());
        } else if (classrooms.isEmpty()) {
            Classroom newClassroom = new Classroom();
            setCurrentClassroom(newClassroom);
        } else {
            setCurrentClassroom(newData.getCurrentClassroom());
        }

        for (int i = 0; i < 7; i++) {
            lessonLists.asUnmodifiableObservableList().get(i).setLessons(newData.getLessonWeekList().get(i));
        }
        ObservableList<Lesson> newLessonList = newData.getLessonList();
        lessons.setLessons(newLessonList);
    }

    //=========== Classroom ================================================================================

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms.setClassrooms(classrooms);
    }

    /** Sets {@code classroom} as the current classroom. */
    public void setCurrentClassroom(Classroom classroom) {
        requireNonNull(classroom);
        if (hasClassroom(classroom)) {
            currentClassroom = classrooms.get(classroom);
        }
    }

    /** Gets the current classroom of the notebook. */
    public Classroom getCurrentClassroom() {
        if (currentClassroom != null && hasClassroom(currentClassroom)) {
            return currentClassroom;
        } else {
            return getFirstClassroom();
        }
    }

    /** Gets the first classroom of the notebook. */
    public Classroom getFirstClassroom() {
        List<Classroom> classroomList = classrooms.asUnmodifiableObservableList();
        if (classroomList.isEmpty()) {
            currentClassroom = new Classroom();
        } else {
            currentClassroom = classroomList.get(0);
        }
        return currentClassroom;
    }

    public void setClassroom(ReadOnlyClassroom classroom) {
        currentClassroom.resetData(classroom);
    }


    /**
     * Returns true if the notebook has the given classroom.
     * @param classroom check whether this classroom is in the notebook.
     * @return true if the notebook has the given classroom.
     */
    public boolean hasClassroom(Classroom classroom) {
        requireNonNull(classroom);
        return classrooms.contains(classroom);
    }

    /**
     * Adds the classroom to the notebook.
     * @param classroom classroom to add to notebook.
     */
    public void addClassroom(Classroom classroom) {
        if (classrooms.isEmpty()) {
            classrooms.add(classroom);
            setCurrentClassroom(classroom);
        } else {
            classrooms.add(classroom);
        }
    }

    /**
     * Removes {@code key} from this {@code Notebook}.
     * {@code key} must exist in the notebook.
     */
    public void removeClassroom(Classroom key) {
        classrooms.remove(key);
    }

    /** Gets the {@code classroom} from the notebook. */
    public Classroom getClassroom(Classroom classroom) {
        List<Classroom> classroomList = getClassroomList();
        for (Classroom toCheck : classroomList) {
            if (toCheck.getClassroomName().equals(classroom.getClassroomName())) {
                return toCheck;
            }
        }
        return null;
    }

    //=========== Student  =============================================================================

    /**
     * Returns true if the current classroom has the given student.
     * @param student checks to see if this student is in the classroom.
     * @return true if the classroom has the student.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return currentClassroom.hasStudent(student);
    }

    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        currentClassroom.setStudent(target, editedStudent);
    }

    /** Adds a specified student to the notebook. */
    public void addStudent(Student student) {
        currentClassroom.addStudent(student);
    }

    /** Deletes a specified student from the notebook. */
    public void deleteStudent(Student target) {
        currentClassroom.removeStudent(target);
    }


    //=========== Assignment  ===========================================================================

    /**
     * Returns true if the current classroom has the given assignment.
     * @param assignment checks to see if this assignment is in the classroom.
     * @return true if the classroom has the assignment.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return currentClassroom.hasAssignment(assignment);
    }

    /** Deletes a specified assignment from the notebook. */
    public void deleteAssignment(Assignment target) {
        currentClassroom.removeAssignment(target);
    }

    /**
     * Adds an assignment to the current classroom.
     * @param assignment to add to current classroom.
     */
    public void addAssignment(Assignment assignment) {
        currentClassroom.addAssignment(assignment);
    }

    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);
        currentClassroom.setAssignment(target, editedAssignment);
    }

    //=========== Display Operations =====================================================================

    public boolean isDisplayStudents() {
        return currentClassroom.isDisplayStudents();
    }

    public void displayStudents() {
        currentClassroom.displayStudents();
    }

    public void displayAssignments() {
        currentClassroom.displayAssignments();
    }


    //=========== Lesson ===========================================================================

    /**
     * Adds a lessons to the classroom.
     * The lesson must not already exist in the classroom.
     */
    public void addLesson(Lesson p) {
        int day = p.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        dayList.add(p);
        lessons.add(p);
    }

    /**
     * Returns true if another lesson in the list exists in the same period as lesson toCheck.
     * @param toCheck Lesson object.
     * @return boolean.
     */
    public boolean checkTimingExist(Lesson toCheck) {
        return lessons.checkTimingExist(toCheck);
    }

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the classroom.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        int day = lesson.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        return dayList.contains(lesson);
    }

    /**
     * Removes {@code key} from this {@code Classroom}.
     * {@code key} must exist in the classroom.
     */
    public void removeLesson(Lesson key) {
        int day = key.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        dayList.remove(key);
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
        if (target.isSameDay(editedLesson)) {
            int day = target.getDayIndex();
            UniqueLessonList dayList = lessonLists.getDayList(day);
            dayList.setLesson(target, editedLesson);
        } else {
            removeLesson(target);
            addLesson(editedLesson);
        }
    }

    /**
     * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setAllLessons(List<UniqueLessonList> lessons) {
        this.lessonLists.setLessons(lessons);
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Classroom}.
     */
    public ObservableList<Classroom> getClassroomList() {
        return classrooms.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Lesson}.
     */
    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code UniqueLessonList}.
     */
    public ObservableList<UniqueLessonList> getLessonWeekList() {
        return lessonLists.asUnmodifiableObservableList();
    }


    //=========== Utility Operations ===================================================================

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
        return classrooms.equals(other.classrooms)
                && lessonLists.equals(other.lessonLists);
    }

}

