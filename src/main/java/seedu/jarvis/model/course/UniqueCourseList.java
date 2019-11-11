package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.model.course.exceptions.CourseNotInListException;
import seedu.jarvis.model.course.exceptions.DuplicateCourseException;

/**
 * Represents a CourseList with unique elements.
 */
public class UniqueCourseList implements Iterable<Course> {
    private final ObservableList<Course> internalList = FXCollections.observableArrayList();
    private final ObservableList<Course> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns {@code true} if the given course exists in this {@code UniqueCourseList}
     *
     * @param toCheck the course to check
     * @return true if the given course exists in the list
     */
    public boolean contains(Course toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Appends the given course to the end of this {@code UniqueCourseList}
     *
     * @param toAdd course to add
     */
    public void add(Course toAdd) {
        requireNonNull(toAdd);
        add(internalList.size(), toAdd);
        assert(coursesAreUnique(internalList));
    }

    /**
     * Inserts the given course into this {@code UniqueCourseList } based on the given index.
     *
     * @param zeroBasedIndex a zero-based index
     * @param toAdd course to add
     */
    public void add(int zeroBasedIndex, Course toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCourseException();
        }
        internalList.add(zeroBasedIndex, toAdd);
        assert(coursesAreUnique(internalList));
    }

    /**
     * Removes the given course from this {@code UniqueCourseList}.
     *
     * @param toRemove course to remove
     */
    public void remove(Course toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CourseNotInListException();
        }
    }

    /**
     * Returns {@code true} if the given list has all unique elements.
     *
     * @param courses a list of courses
     * @return true if the courses are unique
     */
    private boolean coursesAreUnique(List<Course> courses) {
        return courses.stream().distinct().count() == courses.stream().count();
    }

    /**
     * Sets the courses of this {@code UniqueCourseList} as the content in courses.
     *
     * @param courses to set
     */
    public void setCourses(List<Course> courses) {
        requireAllNonNull(courses);
        if (!coursesAreUnique(courses)) {
            throw new DuplicateCourseException();
        }
        internalList.setAll(courses);
    }

    /**
     * Returns the size of this {@code UniqueCourseList}
     *
     * @return the size
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns the course located at the given zero-based index.
     *
     * @param zeroBasedIndex to get
     * @return a course at the given index
     */
    public Course get(int zeroBasedIndex) {
        return internalList.get(zeroBasedIndex);
    }

    /**
     * Returns an unmodifiable {@code ObservableList} from this {@code UniqueCourseList}
     *
     * @return an unmodifiable {@code ObservableList}
     */
    public ObservableList<Course> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueCourseList
                && internalList.equals(((UniqueCourseList) other).internalList));
    }

    @Override
    public Iterator<Course> iterator() {
        return internalList.iterator();
    }
}
