package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Main class for the CcaTracker. Used to store all the lists related to CcaTracker
 */
public class CcaTracker {

    private CcaList ccaList = new CcaList();

    /**
     * Default constructor to be used when Jarvis is started.
     */
    public CcaTracker() {

    }

    /**
     * Constructor to be used if cca list is already present.
     *
     * @param ccaList
     */
    public CcaTracker(CcaList ccaList) {
        requireNonNull(ccaList);
        this.ccaList = ccaList;
    }

    /**
     * Returns true if the list contains an equivalent cca {@code toCheck } as the given argument.
     *
     * @param toCheck
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return ccaList.contains(toCheck);
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
     *
     * @param cca
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
}
