package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;

/**
 * Wraps all data at the claims-book level
 * Duplicates are not allowed (by .isSameClaim comparison)
 */
public class ClaimsBook implements ReadOnlyClaimsBook {

    private final UniqueClaimsList claims;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        claims = new UniqueClaimsList();
    }

    public ClaimsBook() {}

    /**
     * Creates an ClaimsBook using the Claims in the {@code toBeCopied}
     */
    public ClaimsBook(ReadOnlyClaimsBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the claims list with {@code claims}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setClaims(List<Claim> claims) {
        this.claims.setClaims(claims);
    }

    /**
     * Replaces the given claim {@code target} in the list with {@code editedClaim}.
     * {@code target} must exist in the address book.
     * The claim identity of {@code editedClaim} must not be the same as another existing claim in the address book.
     */
    public void setClaims(Claim target, Claim editedClaim) {
        requireNonNull(editedClaim);

        claims.setClaim(target, editedClaim);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyClaimsBook newData) {
        requireNonNull(newData);

        setClaims(newData.getClaimsList());
    }

    //// claim-level operations

    /**
     * Returns true if a claim with the same content as {@code claim} exists in the claims book.
     */
    public boolean hasClaim(Claim claim) {
        requireNonNull(claim);
        return claims.contains(claim);
    }

    /**
     * Adds a claim to the claims book
     * The claim must not already exist in the book
     */
    public void addClaim(Claim claim) {
        claims.add(claim);
    }


    /**
     * Removes {@code key} from this {@code ClaimsBook}.
     * {@code key} must exist in the claims book.
     */
    public void removeClaim(Claim key) {
        claims.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return claims.asUnmodifiableObservableList().size() + " claims";
        // TODO: refine later
    }

    @Override
    public ObservableList<Claim> getClaimsList() {
        return claims.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClaimsBook // instanceof handles nulls
                && claims.equals(((ClaimsBook) other).claims));
    }

    @Override
    public int hashCode() {
        return claims.hashCode();
    }
}
