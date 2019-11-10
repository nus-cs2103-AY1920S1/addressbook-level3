package seedu.address.model.claim;

import java.util.Set;

import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Id;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.tag.Tag;

//@@author{weigenie}
/**
 * Represent a rejected claim in the Financial Planner
 */
public class RejectedClaim extends Claim {

    private static final Status status = Status.REJECTED;

    public RejectedClaim(Id id, Description description, Amount amount, Date date, Name name, Set<Tag> tags) {
        super(id, description, amount, date, name, tags, status);
    }

    public RejectedClaim(Description description, Amount amount, Date date, Name name, Set<Tag> tags) {
        super(description, amount, date, name, tags, status);
    }

    public RejectedClaim(Claim source) {
        super(source.getId(),
                source.getDescription(),
                source.getAmount(),
                source.getDate(),
                source.getName(),
                source.getTags(),
                status);
    }

    /**
     * Returns true if both claims have same identity and data fields.
     * This defines a stronger notion of equality between two claims.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof RejectedClaim)) {
            return false;
        }

        RejectedClaim otherClaim = (RejectedClaim) obj;
        return otherClaim.getId().equals(getId())
                && otherClaim.getDescription().equals(getDescription())
                && otherClaim.getName().equals(getName())
                && otherClaim.getDate().equals(getDate())
                && otherClaim.getAmount().equals(getAmount())
                && otherClaim.getTags().equals(getTags());
    }
}
