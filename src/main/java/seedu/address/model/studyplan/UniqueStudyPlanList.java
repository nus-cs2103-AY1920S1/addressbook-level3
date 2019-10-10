package seedu.address.model.studyplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;


/**
 * A list of study plans that enforces uniqueness between its elements and does not allow nulls.
 * A study plan is considered unique by comparing using {@code StudyPlan#isSameStudyPlan(StudyPlan)}.
 * As such, adding and updating of study plans uses StudyPlan#isSameStudyPlan(StudyPlan) for equality so as
 * to ensure that the StudyPlan being added or updated is unique in terms of identity in the UniqueStudyPlanList.
 * However, the removal of a StudyPlan uses StudyPlan#equals (Object) so as to ensure that the study plan with
 * exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see StudyPlan#isSameStudyPlan(StudyPlan)
 */
public class UniqueStudyPlanList implements Iterable<StudyPlan> {

    private final ObservableList<StudyPlan> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudyPlan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent StudyPlan as the given argument.
     */
    public boolean contains(StudyPlan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudyPlan);
    }

    /**
     * Adds a StudyPlan to the list.
     * The StudyPlan must not already exist in the list.
     */
    public void add(StudyPlan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // TODO: implement DuplicateStudyPlanException

            // throw new DuplicateStudyPlanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the StudyPlan {@code target} in the list with {@code editedStudyPlan}.
     * {@code target} must exist in the list.
     * The StudyPlan identity of {@code editedStudyPlan} must not be the same as another existing
     * StudyPlan in the list.
     */
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireAllNonNull(target, editedStudyPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // TODO: implement StudyPlanNotFoundException
            // throw new StudyPlanNotFoundException();
        }

        if (!target.isSameStudyPlan(editedStudyPlan) && contains(editedStudyPlan)) {
            // TODO: implement DuplicateStudyPlanException

            // throw new DuplicateStudyPlanException();
        }

        internalList.set(index, editedStudyPlan);
    }

    /**
     * Removes the equivalent StudyPlan from the list.
     * The StudyPlan must exist in the list.
     */
    public void remove(StudyPlan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {

            // TODO: implement StudyPlanNotFoundException
            //throw new StudyPlanNotFoundException();
        }
    }

    public void setStudyPlans(UniqueStudyPlanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code StudyPlans}.
     * {@code StudyPlans} must not contain duplicate StudyPlans.
     */
    public void setStudyPlans(List<StudyPlan> studyPlans) {
        requireAllNonNull(studyPlans);
        if (!studyPlansAreUnique(studyPlans)) {

            // TODO: implement DuplicateStudyPlanException
            // throw new DuplicateStudyPlanException();
        }

        internalList.setAll(studyPlans);
    }

    /**
     * Returns a {@code StudyPlan} with the given index.
     */
    public StudyPlan getStudyPlanByIndex(int index) throws StudyPlanNotFoundException{
        Iterator<StudyPlan> iterator = this.iterator();
        StudyPlan result = null;
        while (iterator.hasNext()) {
            StudyPlan studyPlan = iterator.next();
            if (studyPlan.getIndex() == index) {
                result = studyPlan;
            }
        }

        if (result == null) {
            throw new StudyPlanNotFoundException();
        } else {
            return result;
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<StudyPlan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<StudyPlan> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudyPlanList // instanceof handles nulls
                        && internalList.equals(((UniqueStudyPlanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code StudyPlans} contains only unique StudyPlans.
     */
    private boolean studyPlansAreUnique(List<StudyPlan> studyPlans) {
        for (int i = 0; i < studyPlans.size() - 1; i++) {
            for (int j = i + 1; j < studyPlans.size(); j++) {
                if (studyPlans.get(i).isSameStudyPlan(studyPlans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
