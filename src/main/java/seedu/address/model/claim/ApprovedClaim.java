package seedu.address.model.claim;

import java.util.Set;

import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Id;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.tag.Tag;

//@@author{weigenie}
/**
 * Represent an approved claim in the Financial Planner
 */
public class ApprovedClaim extends Claim {

    private static final Status status = Status.APPROVED;

    public ApprovedClaim(Id id, Description description, Amount amount, Date date, Name name, Set<Tag> tags) {
        super(id, description, amount, date, name, tags, status);
    }

    public ApprovedClaim(Description description, Amount amount, Date date, Name name, Set<Tag> tags) {
        super(description, amount, date, name, tags, status);
    }

    public ApprovedClaim(Claim source) {
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

        if (!(obj instanceof ApprovedClaim)) {
            return false;
        }

        ApprovedClaim otherClaim = (ApprovedClaim) obj;
        return otherClaim.getId().equals(getId())
                && otherClaim.getDescription().equals(getDescription())
                && otherClaim.getName().equals(getName())
                && otherClaim.getDate().equals(getDate())
                && otherClaim.getAmount().equals(getAmount())
                && otherClaim.getTags().equals(getTags());
    }
}
