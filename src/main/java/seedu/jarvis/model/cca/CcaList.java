package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.exceptions.CcaNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaException;

/**
 * Represents list of Cccas.
 */
public class CcaList {

    private ObservableList<Cca> internalCcaList = FXCollections.observableArrayList();
    private final ObservableList<Cca> internalUnmodifiableCcaList =
            FXCollections.unmodifiableObservableList(internalCcaList);

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public CcaList(){

    }

    /**
     * Constructor to be used if there already exists a list of CCAs.
     *
     * @param internalCcaList
     */
    public CcaList(ObservableList<Cca> internalCcaList) {
        requireNonNull(internalCcaList);
        this.internalCcaList = internalCcaList;
    }

    //// list overwrite operations
    public void setCcas(List<Cca> ccaList) {
        requireAllNonNull(ccaList);

        internalCcaList.setAll(ccaList);
    }

    public ObservableList<Cca> getInternalCcaList() {
        assert internalCcaList != null : "internalCcaList is null.";
        return internalCcaList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asUnmodifiableObservableList() {
        assert internalUnmodifiableCcaList != null : "internalUnmodifiableCcaList is null.";
        return internalUnmodifiableCcaList;
    }

    /// Getters and setter
    /**
     * Returns the {@Cca} based on its {@code index}
     *
     * @return the {@Cca} based on its {@code index}.
     */
    public Cca getCca(Index index) {
        requireNonNull(index);
        assert index.getZeroBased() >= 0 : "Target index is negative.";
        if (index.getZeroBased() >= internalCcaList.size()) {
            throw new CcaNotFoundException();
        }

        return internalCcaList.get(index.getZeroBased());
    }

    /**
     * Returns true if the list contains an equivalent cca as the given argument.
     */
    public boolean containsCca(Cca toCheck) {
        requireNonNull(toCheck);
        assert internalCcaList != null : "internalCcaList is null.";
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }

    /**
     * Returns the number of {@code Cca} in the current {@code CcaList}.
     *
     * @return size of the current internalCcaList.
     */
    public int size() {
        assert internalCcaList != null : "internalCcaList is null.";
        return internalCcaList.size();
    }

    /**
     * Adds an cca to the cca list.
     */
    public void addCca(Cca cca) {
        requireNonNull(cca);
        if (internalCcaList.contains(cca)) {
            throw new DuplicateCcaException();
        }

        assert internalCcaList != null : "internalCcaList is null.";
        internalCcaList.add(cca);
    }

    /**
     * Adds a cca {@code cca} to the cca list at the specified {@code targetIndex}.
     */
    public void addCca(Index targetIndex, Cca cca) {
        requireAllNonNull(targetIndex, cca);
        assert targetIndex.getZeroBased() >= 0 : "Target index is negative.";
        if (internalCcaList.contains(cca)) {
            throw new DuplicateCcaException();
        }

        assert internalCcaList != null : "internalCcaList is null.";
        internalCcaList.add(targetIndex.getZeroBased(), cca);
    }

    /**
     * Updates the cca name.
     *
     * @param toBeUpdatedCca
     * @param updatedCca
     */
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
        requireAllNonNull(toBeUpdatedCca, updatedCca);
        int toBeUpdatedCcaIndex = internalCcaList.indexOf(toBeUpdatedCca);
        if (toBeUpdatedCcaIndex == -1) {
            throw new CcaNotFoundException();
        }

        if (!toBeUpdatedCca.isSameCca(updatedCca) && containsCca(updatedCca)) {
            throw new DuplicateCcaException();
        }

        assert internalCcaList != null : "internalCcaList is null.";
        internalCcaList.set(toBeUpdatedCcaIndex, updatedCca);
    }

    /**
     * Adds a progress to the chosen {@code Cca}.
     *
     * @param toBeUpdatedCca is the chosen Cca.
     * @param ccaMilestoneList is added to the Cca.
     */
    public void addProgress(Cca toBeUpdatedCca, CcaMilestoneList ccaMilestoneList) {
        requireAllNonNull(toBeUpdatedCca, ccaMilestoneList);

        toBeUpdatedCca.addProgress(ccaMilestoneList);
    }

    /**
     * Removes {@code toBeRemovedCca} from the {@code CcaList}.
     */
    public void removeCca(Cca toBeRemovedCca) {
        requireNonNull(toBeRemovedCca);
        if (!internalCcaList.contains(toBeRemovedCca)) {
            throw new CcaNotFoundException();
        }

        assert internalCcaList != null : "internalCcaList is null.";
        internalCcaList.remove(toBeRemovedCca);
    }

    /**
     * Increments the progress of the chosen {@code Cca} by 1 {@code Milestone} based on the {@code Index} of the Cca.
     */
    public void increaseProgress(Index index) {
        requireNonNull(index);
        assert index.getZeroBased() >= 0 : "Target index is negative.";
        Cca cca = getCca(index);
        cca.increaseProgress();
    }

    /**
     * Checks if {@code} Cca contains progress already.
     */
    public boolean ccaContainsProgress(Index targetIndex) {
        assert targetIndex.getZeroBased() >= 0 : "Target index is negative.";
        Cca targetCca = getCca(targetIndex);
        if (targetCca.containsProgress()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the progress in {@code Cca} at {@code targetIndex} can be incremented any further.
     */
    public boolean ccaAtMaxIncrement(Index targetIndex) {
        assert targetIndex.getZeroBased() >= 0 : "Target index is negative.";
        Cca targetCca = getCca(targetIndex);
        if (targetCca.progressAtMaxIncrement()) {
            return true;
        }

        return false;
    }

    /**
     * Removes {@code toRemoveCcaMilestoneList} from the {@code targetCca}.
     */
    public void removeCcaMilestoneList(Cca targetCca, CcaMilestoneList toRemoveCcaMilestoneList) {
        requireAllNonNull(targetCca, toRemoveCcaMilestoneList);

        targetCca.removeCcaMilestoneList(toRemoveCcaMilestoneList);
    }

    /**
     * Returns true if the progress in {@code Cca} at {@code targetIndex} is at its minimum.
     */
    public boolean ccaProgressAtMinLevel(Index targetIndex) {
        requireNonNull(targetIndex);
        assert targetIndex.getZeroBased() >= 0 : "Target index is negative.";
        Cca targetCca = getCca(targetIndex);
        return targetCca.ccaProgressAtMinLevel();
    }

    /**
     * Decreases the progress at the {@code targetIndex}.
     */
    public void decreaseProgress(Index targetIndex) {
        requireNonNull(targetIndex);
        assert targetIndex.getZeroBased() >= 0 : "Target index is negative.";
        Cca targetCca = getCca(targetIndex);
        targetCca.decreaseProgress();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaList // instanceof handles nulls
                && internalCcaList.equals(((CcaList) other).internalCcaList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ccas in the ccas list: ");
        for (Cca cca : internalCcaList) {
            sb.append(cca);
            sb.append("\n");
        }
        return sb.toString();
    }
}
