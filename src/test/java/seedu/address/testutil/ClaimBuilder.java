package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.claim.Amount;
import seedu.address.model.claim.ApprovedClaim;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Description;
import seedu.address.model.claim.PendingClaim;
import seedu.address.model.claim.RejectedClaim;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Helps with building of claim object.
 */
public class ClaimBuilder {

    public static final String DEFAULT_DESCRIPTION = "Logistics";
    public static final String DEFAULT_AMOUNT = "152.86";
    public static final String DEFAULT_DATE = "15-12-2019";
    public static final String DEFAULT_NAME = "John";
    public static final String DEFAULT_PHONE = "91234567";

    private Description description;
    private Amount amount;
    private Date date;
    private Name name;
    private Set<Tag> tags;

    public ClaimBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    public ClaimBuilder(Claim claimToCopy) {
        description = claimToCopy.getDescription();
        amount = claimToCopy.getAmount();
        date = claimToCopy.getDate();
        name = claimToCopy.getName();
        tags = new HashSet<>(claimToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Claim} that we are building.
     */
    public ClaimBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Claim build() {
        return new PendingClaim(description, amount, date, name, tags);
    }

    public ApprovedClaim buildApproved() {
        return new ApprovedClaim(description, amount, date, name, tags);
    }

    public RejectedClaim buildRejected() {
        return new RejectedClaim(description, amount, date, name, tags);
    }
}
