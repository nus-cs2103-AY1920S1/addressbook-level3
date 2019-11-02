package seedu.jarvis.model.cca;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;

/**
 * The API of the CcaTrackerModel component
 *
 */
public interface CcaTrackerModel {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Cca> PREDICATE_SHOW_ALL_CCAS = unused -> true;

    /**
     * Checks if cca tracker contains the given cca.
     */
    public boolean containsCca(Cca cca);

    /**
     * Adds the given cca {@code cca} to the cca tracker.
     *
     */
    public void addCca(Cca cca);

    /**
     * Removes the given cca {@code cca} from the cca tracker.
     */
    public void removeCca(Cca cca);

    /**
     * Updates the given cca {@code toBeUpdatedCca} with the given {@code updatedCca}.
     *
     * @param toBeUpdatedCca
     * @param updatedCca
     */
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca);

    /**
     * Returns the CcaTracker {@Code CcaTracker}.
     */
    public CcaTracker getCcaTracker();

    /**
     * Returns the number of {@code Cca} in the CcaTracker.
     *
     * @return the number of {@code Cca}.
     */
    public int getNumberOfCcas();

    /**
     * Return the {@code Cca} based on its index.
     *
     * @return the {@code Cca} based on its index.
     */
    public Cca getCca(Index index);

    public void updateFilteredCcaList(Predicate<Cca> predicate);

    public ObservableList<Cca> getFilteredCcaList();

    public void addProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList);

    public void increaseProgress(Index index);

    public boolean ccaContainsProgress(Index targetIndex);

    public boolean ccaAtMaxIncrement(Index targetIndex);

    public void removeProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList);

    public boolean ccaProgressAtMinLevel(Index targetIndex);

    public void decreaseProgress(Index targetIndex);
}
