package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
 * A lesson is considered unique by comparing using {@code lesson#isSameLesson(lesson)}. As such, adding and
 * updating of lessons uses lesson#isSameLesson(lesson) for equality so as to ensure that the lesson being added
 * or updated is unique in terms of identity in the UniqueLessonList. However, the removal of a lesson uses
 * lesson#equals(Object) so as to ensure that the lesson with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Lesson#isSameLesson(Lesson)
 */
public class UniqueLessonList implements Iterable<Lesson> {
    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLesson);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(toAdd);
        internalList.sort(new LessonComparator());
    }

    /**
     * checks if there exists a lesson during the time period of lesson toCheck.
     * @param toCheck Lesson object.
     * @return boolean.
     */
    public boolean checkTimingExist(Lesson toCheck) {
        for (int i = 0; i < internalList.size(); i++) {
            Lesson lesson = internalList.get(i);
            if (lesson.checkTimingExist(toCheck)) {
                return true;
            } else if (toCheck.checkTimingExist(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the list.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the list.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.isSameLesson(editedLesson) && contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        internalList.set(index, editedLesson);
        internalList.sort(new LessonComparator());
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LessonNotFoundException();
        }
        internalList.sort(new LessonComparator());
    }

    public void setLessons(UniqueLessonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        internalList.sort(new LessonComparator());
    }

    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }
        internalList.setAll(lessons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLessonList // instanceof handles nulls
                && internalList.equals(((UniqueLessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code lessons} contains only unique lesson.
     */
    private boolean lessonsAreUnique(List<Lesson> lessons) {
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).isSameLesson(lessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    //@@author weikiat97
    /**
     * Returns true if both assignments of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameUniqueLesson(UniqueLessonList otherUniqueLessonList) {
        if (otherUniqueLessonList == this) {
            return true;
        }

        return otherUniqueLessonList != null
                && otherUniqueLessonList.asUnmodifiableObservableList().equals(asUnmodifiableObservableList());
    }

}
