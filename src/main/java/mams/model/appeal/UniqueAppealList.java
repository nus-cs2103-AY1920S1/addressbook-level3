package mams.model.appeal;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mams.commons.util.CollectionUtil;
import mams.model.appeal.exceptions.DuplicateAppealException;
import mams.model.appeal.exceptions.AppealNotFoundException;

public class UniqueAppealList implements Iterable<Appeal> {

    private final ObservableList<Appeal> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appeal> internalUnmodifiedList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * returns true if this list contains an equivalent appeal as argument
     * @param toCheck
     * @return
     */
    public boolean contains(Appeal toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAppeal);
    }

    /**
     * Adds appeal to list, appeal must not already be in list
     * @param toAdd
     */
    public void add(Appeal toAdd) {
        requireNonNull(toAdd);
        if(contains(toAdd)) {
            throw new DuplicateAppealException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the student {@code target} in the list with {@code editedAppeal}.
     * {@code target} must exist in the list.
     * The appeal identity of {@code editedAppeal} must not be the same as another existing appeal in the list.
     * @param target
     * @param editedAppeal
     */
    public void setAppeal(Appeal target, Appeal editedAppeal) {
        CollectionUtil.requireAllNonNull(target, editedAppeal);

        int index = internalList.indexOf(target);
        if(index == -1){
            throw new AppealNotFoundException();
        }

        if(!target.isSameAppeal(editedAppeal) && contains(editedAppeal)){
            throw new DuplicateAppealException();
        }

        internalList.set(index, editedAppeal);
    }

    /**
     * Removes the equivalent appeal from the list.
     * The appeal must exist in the list.
     */
    public void remove(Appeal toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppealNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code appeals}.
     * {@code appeals} must not contain duplicate appeals.
     */
    public void setAppeal(UniqueAppealList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appeal> asUnmodifiedObservableList() {
        return internalUnmodifiedList;
    }

    @Override
    public Iterator<Appeal> iterator(){
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueAppealList && internalList.equals(((UniqueAppealList) other).internalList));
    }

    @Override
    public int hashCode(){
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code appeals} contains only unique appeals.
     */
    private boolean appealsAreUnique(List<Appeal> appeals) {
        for (int i = 0; i < appeals.size() - 1; i++) {
            for (int j = i + 1; j < appeals.size(); j++) {
                if (appeals.get(i).isSameAppeal(appeals.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
