package seedu.address.model.claim;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.claim.exceptions.ClaimNotFoundException;
import seedu.address.model.claim.exceptions.DuplicateClaimException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of claims that enforces uniqueness between its elements and does not allow nulls.
 * A claim is considered unique by comparing using {@code Claims#isSameClaim(Claim)}. As such, adding and updating of
 * claim uses Claim#IsSameClaim(claim) for equality so as to ensure that the claim being added or updated is
 * unique in terms of identity in the UniqueClaimsList. However, the removal of a claim uses Claim#equals(Object) so
 * as to ensure that the claim with exactly the same fields will be removed.
 *
 *
 * @see Claim#isSameClaim(Claim)
 */
public class UniqueClaimsList implements Iterable<Claim> {

    private final ObservableList<Claim> internalList = FXCollections.observableArrayList();
    private final ObservableList<Claim> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent claim as the given argument.
     */
    public boolean contains(Claim toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClaim);
    }

    /**
     * Adds a claim to the list.
     * The claim must not already exist in the list.
     */
    public void add(Claim toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the claim {@code target} in the list with {@code editedClaim}.
     * {@code target} must exist in the list.
     * The claim of {@code editedClaim} must not be the same as another existing claims in the list.
     */
    public void setClaim(Claim target, Claim editedClaim) {
        requireAllNonNull(target, editedClaim);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClaimNotFoundException();
        }

        if (!target.isSameClaim(editedClaim) && contains(editedClaim)) {
            throw new DuplicateClaimException();
        }

        internalList.set(index, editedClaim);
    }

    /**
     * Removes the equivalent claim from the list.
     * The claim must exist in the list.
     */
    public void remove(Claim toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClaimNotFoundException();
        }
    }

    public void setClaims(UniqueClaimsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code claims}.
     * {@code claims} must not contain duplicate claims.
     */
    public void setClaims(List<Claim> claims) {
        requireAllNonNull(claims);
        if (!claimsAreUnique(claims)) {
            throw new DuplicateClaimException();
        }

        internalList.setAll(claims);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Claim> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Claim> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClaimsList // instanceof handles nulls
                        && internalList.equals(((UniqueClaimsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code claims} contains only unique claims.
     */
    private boolean claimsAreUnique(List<Claim> claims) {
        for (int i = 0; i < claims.size() - 1; i++) {
            for (int j = i + 1; j < claims.size(); j++) {
                if (claims.get(i).isSameClaim(claims.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
