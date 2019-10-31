package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueLessonWeekList implements Iterable<UniqueLessonList> {
    private final ObservableList<UniqueLessonList> internalList = FXCollections.observableArrayList();
    private final ObservableList<UniqueLessonList> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueLessonList getList(int index) {
        return internalList.get(index);
    }

    public void setLessonLists(UniqueLessonWeekList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<UniqueLessonList> lessonLists) {
        requireAllNonNull(lessonLists);

        internalList.setAll(lessonLists);
    }

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

}
