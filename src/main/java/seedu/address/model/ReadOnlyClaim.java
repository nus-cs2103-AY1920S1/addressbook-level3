package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;

/**
 * Unmodifiable view of Contact
 */
public interface ReadOnlyClaim {

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<Claim> getClaimList();

}
