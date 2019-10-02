package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.UniqueStudyPlanList;

/**
 * Wraps all data at the module planner level
 * Duplicates are not allowed (by .isSameStudyPlan comparison)
 */
public class ModulePlanner implements ReadOnlyModulePlanner {

    private final UniqueStudyPlanList studyPlans;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        studyPlans = new UniqueStudyPlanList();
    }

    public ModulePlanner() {}

    /**
     * Creates an ModulePlanner using the studyPlans in the {@code toBeCopied}
     */
    public ModulePlanner(ReadOnlyModulePlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code studyPlans}.
     * {@code studyPlans} must not contain duplicate studyPlans.
     */
    public void setStudyPlans(List<StudyPlan> studyPlans) {
        this.studyPlans.setStudyPlans(studyPlans);
    }

    /**
     * Resets the existing data of this {@code ModulePlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyModulePlanner newData) {
        requireNonNull(newData);

        setStudyPlans(newData.getStudyPlanList());
    }

    //// person-level operations


    /**
     * Returns true if a study plan with the same identity as {@code study plan} exists in the address book.
     */
    public boolean hasStudyPlan(StudyPlan studyPlan) {
        requireNonNull(studyPlan);
        return studyPlans.contains(studyPlan);
    }

    /**
     * Adds a study plan to the module planner.
     * The study plan must not already exist in the module planner.
     */
    public void addStudyPlan(StudyPlan studyPlan) {
        studyPlans.add(studyPlan);
    }

    /**
     * Replaces the given study plan {@code target} in the list with {@code editedStudyPlan}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedStudyPlan} must not be the same as another existing study plan
     * in the address book.
     */
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireNonNull(editedStudyPlan);

        studyPlans.setStudyPlan(target, editedStudyPlan);
    }

    /**
     * Removes {@code key} from this {@code ModulePlanner}.
     * {@code key} must exist in the module planner.
     */
    public void removeStudyPlan(StudyPlan key) {
        studyPlans.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return studyPlans.asUnmodifiableObservableList().size() + " studyPlans";
        // TODO: refine later
    }

    @Override
    public ObservableList<StudyPlan> getStudyPlanList() {
        return studyPlans.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModulePlanner // instanceof handles nulls
                && studyPlans.equals(((ModulePlanner) other).studyPlans));
    }

    @Override
    public int hashCode() {
        return studyPlans.hashCode();
    }
}
