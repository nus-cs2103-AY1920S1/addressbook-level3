package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;

/**
 * Main class for the CcaTracker. Used to store all the lists related to CcaTracker
 */
public class CcaTracker {

    private final CcaList ccaList;
    private final FilteredList<Cca> filteredCcas;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ccaList = new CcaList();
        filteredCcas = new FilteredList<>(getCcaList(), CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS);
    }

    /**
     * Default constructor to be used when Jarvis is started.
     */
    public CcaTracker() {

    }

    /**
     * Constructor to be used if Cca Tracker is already present.
     *
     * @param ccaTracker
     */
    public CcaTracker(CcaTracker ccaTracker) {
        requireNonNull(ccaTracker);
        resetData(ccaTracker);
    }

    /**
     * Constructor to be used if cca list is already present.
     *
     * @param ccaList - the cca list to be copied.
     */
    public CcaTracker(CcaList ccaList) {
        requireNonNull(ccaList);
        this.ccaList.setCcas(ccaList.getInternalCcaList());
    }

    /// list overwrite operations

    public void setCcaList(List<Cca> ccaList) {
        requireNonNull(ccaList);

        this.ccaList.setCcas(ccaList);
    }

    /**
     * Resets the existing data of this {@code CcaTracker} with {@code newData}.
     */
    public void resetData(CcaTracker newData) {
        requireNonNull(newData);

        setCcaList(newData.getCcaList());
    }

    /// Cca level operations

    /**
     * Returns the cca list {@code CcaList}.
     *
     * @return the cca list.
     */
    public ObservableList<Cca> getCcaList() {
        return ccaList.asUnmodifiableObservableList();
    }

    public Cca getCca(Index index) {
        requireNonNull(index);

        return ccaList.getCca(index);
    }

    /**
     * Returns true if the list contains an equivalent cca {@code toCheck } as the given argument.
     *
     * @param toCheck
     */
    public boolean containsCca(Cca toCheck) {
        requireNonNull(toCheck);
        return ccaList.containsCca(toCheck);
    }

    /**
     * Adds a cca {@code cca} to the cca list.
     *
     * @param cca
     */
    public void addCca(Cca cca) {
        requireNonNull(cca);
        ccaList.addCca(cca);
    }

    /**
     * Removes a cca {@code cca} from the cca list.
     */
    public void removeCca(Cca cca) {
        requireNonNull(cca);
        ccaList.removeCca(cca);
    }

    /**
     * Updates a cca {@code toBeUpdatedCca} with a new {@code updatedCca}.
     *
     * @param toBeUpdatedCca
     * @param updatedCca
     */
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
        requireAllNonNull(toBeUpdatedCca, updatedCca);
        ccaList.updateCca(toBeUpdatedCca, updatedCca);
    }

    /**
     * Gets the number of {@code Cca} in the current tracker.
     *
     * @return The number of {@code Cca}.
     */
    public int getNumberOfCcas() {
        return ccaList.size();
    }

    /**
     * Adds a progress tracker to a cca.
     *
     * @param toBeUpdatedCca to be updated.
     * @param ccaMilestoneList to be added to the selected Cca.
     */
    public void addProgress(Cca toBeUpdatedCca, CcaMilestoneList ccaMilestoneList) {
        requireAllNonNull(toBeUpdatedCca, ccaMilestoneList);
        ccaList.addProgress(toBeUpdatedCca, ccaMilestoneList);
    }

    /**
     * Increases the progress of the chosen {@code Cca} by 1 {@code Milestone}.
     */
    public void increaseProgress(Index index) {
        requireNonNull(index);
        ccaList.increaseProgress(index);
    }

    /**
     * Checks if the Cca at {@code TargetIndex} contains a {@CcaProgress} already.
     *
     * @return true if the Cca at {@code TargetIndex} contains a {@CcaProgress} already.
     */
    public boolean ccaContainsProgress(Index targetIndex) {
        return ccaList.ccaContainsProgress(targetIndex);
    }

    /**
     * Checks if cca progress at {@code s} is already max.
     */
    public boolean ccaAtMaxIncrement(Index index) {
        return ccaList.ccaAtMaxIncrement(index);
    }

    /**
     * Updates {@code filtered} according to the give {@code Predicate}.
     *
     * @param predicate {@code Predicate} to be applied to filter {@code filteredCcas}.
     */
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        requireNonNull(predicate);
        filteredCcas.setPredicate(predicate);
    }

    /**
     * Removes the {@code toRemoveCcaMilestoneList} from {@code targetCca}.
     */
    public void removeCcaMilestoneList(Cca targetCca, CcaMilestoneList toRemoveCcaMilestoneList) {
        requireAllNonNull(targetCca, toRemoveCcaMilestoneList);
        ccaList.removeCcaMilestoneList(targetCca, toRemoveCcaMilestoneList);
    }

    /**
     * Checks if the {@code CcaProgress} at the {@code targetIndex} {@code Cca} is at the minimum level.
     */
    public boolean ccaProgressAtMinLevel(Index targetIndex) {
        requireNonNull(targetIndex);
        return ccaList.ccaProgressAtMinLevel(targetIndex);
    }

    /**
     * Decreases the progress at the {@code targetIndex}.
     */
    public void decreaseProgress(Index targetIndex) {
        requireNonNull(targetIndex);
        ccaList.decreaseProgress(targetIndex);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Cca} backed by its internal list.
     */
    public ObservableList<Cca> getFilteredCcaList() {
        return filteredCcas;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaTracker // instanceof handles nulls
                && ccaList.equals(((CcaTracker) other).ccaList));
    }

    @Override
    public int hashCode() {
        return ccaList.hashCode();
    }
}
