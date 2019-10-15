package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.model.course.exceptions.CourseAlreadyInListException;
import seedu.jarvis.model.course.exceptions.CourseNotInListException;

/**
 * Represents a CourseList with unique elements.
 */
public class UniqueCourseList implements Iterable<Course> {
    private final ObservableList<Course> internalList = FXCollections.observableArrayList();
    private final ObservableList<Course> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the given course has all unique elements.
     *
     * @param courses a list of courses
     * @return true if the courses are unique
     */
    private boolean coursesAreUnique(List<Course> courses) {
        return courses.stream().distinct().count()
            == courses.stream().count();
    }

    /**
     * Returns true if the given course exists in the list.
     *
     * @param toCheck the course to check
     * @return true if the given course exists in the list
     */
    public boolean contains(Course toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Appends the given course to the end of the list.
     *
     * @param toAdd course to add
     */
    public void add(Course toAdd) {
        requireNonNull(toAdd);
        add(internalList.size(), toAdd);
        assert(coursesAreUnique(internalList));
    }

    /**
     * Inserts the given course into the list based on the given index.
     *
     * @param zeroBasedIndex a zero-based index
     * @param toAdd course to add
     */
    public void add(int zeroBasedIndex, Course toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new CourseAlreadyInListException();
        }
        internalList.add(zeroBasedIndex, toAdd);
        assert(coursesAreUnique(internalList));
    }

    /**
     * Removes the given course from this list.
     *
     * @param toRemove course to remove
     */
    public void remove(Course toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CourseNotInListException();
        }
    }

    public Course get(int zeroBasedIndex) {
        return internalList.get(zeroBasedIndex);
    }

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
