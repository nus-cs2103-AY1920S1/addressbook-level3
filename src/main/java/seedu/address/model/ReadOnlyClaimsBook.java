package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.claim.Claim;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an claims book
 */
public interface ReadOnlyClaimsBook {

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<Claim> getClaimsList();

}
