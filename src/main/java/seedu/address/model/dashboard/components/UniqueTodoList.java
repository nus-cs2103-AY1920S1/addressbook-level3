package seedu.address.model.dashboard.components;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.diary.exceptions.DiaryNotFoundException;
import seedu.address.model.diary.exceptions.DuplicateDiaryException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A to-do is considered unique by comparing using {@code Todo#isSameTodo(Todo)}. As such, adding and updating of
 * persons uses Todo#isSameTodo(Todo) for equality so as to ensure that the diary being added or updated is
 * unique in terms of identity in the UniqueTodoList. However, the removal of a to-do uses Todo#equals(Object) so
 * as to ensure that the diary with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Todo#isSameTodo(Todo)
 */
public class UniqueTodoList implements Iterable<Todo> {

    private final ObservableList<Todo> internalList = FXCollections.observableArrayList();
    private final ObservableList<Todo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent diary as the given argument.
     */
    public boolean contains(Todo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTodo);
    }

    /**
     * Adds a diary to the list.
     * The diary must not already exist in the list.
     */
    public void add(Todo toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDiaryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in the list.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the list.
     */
    public void setDiary(Todo target, Todo editedTodo) {
        requireAllNonNull(target, editedTodo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DiaryNotFoundException();
        }

        if (!target.isSameTodo(editedTodo) && contains(editedTodo)) {
            throw new DuplicateDiaryException();
        }

        internalList.set(index, editedTodo);
    }

    /**
     * Removes the equivalent to-do from the list.
     * The to-do must exist in the list.
     */
    public void remove(Todo toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DiaryNotFoundException();
        }
    }

    public void setTodos(UniqueTodoList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Todo> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Todo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTodoList // instanceof handles nulls
                && internalList.equals(((UniqueTodoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code diaries} contains only unique diaries.
     */
    private boolean todosAreUnique(List<Todo> todos) {
        for (int i = 0; i < todos.size() - 1; i++) {
            for (int j = i + 1; j < todos.size(); j++) {
                if (todos.get(i).isSameTodo(todos.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
