package seedu.jarvis.model.cca;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;


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
    public Cca getCca(Index index) throws CommandException;

    public void updateFilteredCcaList(Predicate<Cca> predicate);

    public ObservableList<Cca> getFilteredCcaList();
}
