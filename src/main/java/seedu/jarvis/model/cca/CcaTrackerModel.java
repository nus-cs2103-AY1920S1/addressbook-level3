package seedu.jarvis.model.cca;

/**
 * The API of the CcaTrackerModel component
 *
 */
public interface CcaTrackerModel {

    /**
     * Checks if cca tracker contains the given cca.
     */
    public void contains(Cca cca);

    /**
     * Adds the given cca {@code cca} the cca tracker.
     *
     */
    public void addCc(Cca cca);

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
}
