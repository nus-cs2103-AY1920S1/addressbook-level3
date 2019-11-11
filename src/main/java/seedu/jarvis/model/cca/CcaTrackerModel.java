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
     */
    public void addCca(Cca cca);

    /**
     * Adds the given {@code Cca} at the given {@code targetIndex}.
     */
    public void addCca(Index targetIndex, Cca cca);

    /**
     * Removes the given cca {@code cca} from the cca tracker.
     */
    public void removeCca(Cca cca);

    /**
     * Updates the given cca {@code toBeUpdatedCca} with the given {@code updatedCca}.
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

    /**
     * Updates the cca list with the given {@code predicate}.
     */
    public void updateFilteredCcaList(Predicate<Cca> predicate);

    /**
     * Gets the filtered cca list as an {@code ObservableList}.
     */
    public ObservableList<Cca> getFilteredCcaList();

    /**
     * Adds a {@code CcaMilestoneList} to the {@code targetCca}.
     */
    public void addProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList);

    /**
     * Increases the progress of the cca at the target {@code index}.
     */
    public void increaseProgress(Index index);

    /**
     * Decreases the progress of the cca at the target {@code index}.
     */
    public void decreaseProgress(Index targetIndex);

    /**
     * Checks if the {@code Cca} at the {@code targetIndex} contains a {@code CcaProgress}.
     */
    public boolean ccaContainsProgress(Index targetIndex);

    /**
     * Checks if the {@code Cca} at the {@code targetIndex} has its progress at max.
     */
    public boolean ccaAtMaxIncrement(Index targetIndex);

    /**
     * Removes the {@code CcaMilestoneList} from the {@code targetCca}.
     */
    public void removeProgress(Cca targetCca, CcaMilestoneList toAddCcaMilestoneList);

    /**
     * Checks if the {@code Cca} at the {@code targetIndex} has its progress at min.
     */
    public boolean ccaProgressAtMinLevel(Index targetIndex);
}
