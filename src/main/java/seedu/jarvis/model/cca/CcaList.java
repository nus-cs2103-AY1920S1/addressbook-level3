package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.jarvis.model.cca.exceptions.CcaNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaException;

/**
 * Represents list of Cccas.
 */
public class CcaList {

    private ArrayList<Cca> internalCcaList = new ArrayList<>();

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
    public CcaList(ArrayList<Cca> internalCcaList) {
        requireNonNull(internalCcaList);
        this.internalCcaList = internalCcaList;
    }

    /**
     * Returns true if the list contains an equivalent cca as the given argument.
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }

    /**
     * Adds an cca to the cca list.
     *
     * @param cca
     */
    public void addCca(Cca cca) {
        requireNonNull(cca);
        if (!internalCcaList.contains(cca)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.add(cca);
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

        internalCcaList.set(toBeUpdatedCcaIndex, updatedCca);
    }

    /**
     * Removes cca from the cca list.
     *
     * @param toBeRemovedCca
     */
    public void removeCca(Cca toBeRemovedCca) {
        requireNonNull(toBeRemovedCca);
        if (!internalCcaList.contains(toBeRemovedCca)) {
            throw new CcaNotFoundException();
        }

        internalCcaList.remove(toBeRemovedCca);

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
