package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;

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
     * @param the cca list to be copied.
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

    public Cca getCca(Index index) throws CommandException {
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
     * Updates {@code filtered} according to the give {@code Predicate}.
     *
     * @param predicate {@code Predicate} to be applied to filter {@code filteredCcas}.
     */
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        requireNonNull(predicate);
        filteredCcas.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Cca} backed by its internal list.
     */
    public ObservableList<Cca> getFilteredCcaList() {
        return filteredCcas;
    }

}
