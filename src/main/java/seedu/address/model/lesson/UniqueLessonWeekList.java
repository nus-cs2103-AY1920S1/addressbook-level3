package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * List of all the lessons sorted by the days of the week.
 */
public class UniqueLessonWeekList implements Iterable<UniqueLessonList> {
    private final ObservableList<UniqueLessonList> internalList = FXCollections.observableArrayList();
    private final ObservableList<UniqueLessonList> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueLessonList getDayList(int index) {
        return internalList.get(index);
    }

    public void add(UniqueLessonList uniqueLessonList) {
        internalList.add(uniqueLessonList);
    }

    public void setLessonLists(UniqueLessonWeekList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    //@@author weikiat97
    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<UniqueLessonList> uniqueLessonLists) {
        requireAllNonNull(uniqueLessonLists);

        List<UniqueLessonList> listToAdd = new ArrayList<>();
        for (UniqueLessonList uniqueLesson : uniqueLessonLists) {
            UniqueLessonList uniqueLessonToAdd = new UniqueLessonList();
            uniqueLessonToAdd.setLessons(uniqueLesson.asUnmodifiableObservableList());
            listToAdd.add(uniqueLessonToAdd);
        }
        internalList.setAll(listToAdd);
    }

    //@@author
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<UniqueLessonList> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<UniqueLessonList> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLessonWeekList // instanceof handles nulls
                && internalList.equals(((UniqueLessonWeekList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    //@@author weikiat97
    /**
     * Returns true if {@code assignments} contains only unique assignments.
     */
    private boolean lessonsAreUnique(List<UniqueLessonList> uniqueLessons) {
        for (int i = 0; i < uniqueLessons.size() - 1; i++) {
            for (int j = i + 1; j < uniqueLessons.size(); j++) {
                if (uniqueLessons.get(i).isSameUniqueLesson(uniqueLessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
