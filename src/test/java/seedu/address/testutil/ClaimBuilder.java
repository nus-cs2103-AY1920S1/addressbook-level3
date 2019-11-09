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
import seedu.address.model.commonvariables.Id;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Helps with building of claim object.
 */
public class ClaimBuilder {

    public static final String DEFAULT_ID = "1";
    public static final String DEFAULT_DESCRIPTION = "Logistics";
    public static final String DEFAULT_AMOUNT = "152.86";
    public static final String DEFAULT_DATE = "15-12-2019";
    public static final String DEFAULT_NAME = "John";

    private Id id;
    private Description description;
    private Amount amount;
    private Date date;
    private Name name;
    private Set<Tag> tags;

    public ClaimBuilder() {
        id = new Id(DEFAULT_ID);
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    public ClaimBuilder(Claim claimToCopy) {
        id = claimToCopy.getId();
        description = claimToCopy.getDescription();
        amount = claimToCopy.getAmount();
        date = claimToCopy.getDate();
        name = claimToCopy.getName();
        tags = new HashSet<>(claimToCopy.getTags());
    }

    /**
     * Sets the {@code Id} of the {@code Claim} that we are building.
     */
    public ClaimBuilder withId(String id) {
        this.id = new Id(id);
        return this;
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

    public PendingClaim build() {
        return new PendingClaim(id, description, amount, date, name, tags);
    }

    public ApprovedClaim buildApproved() {
        return new ApprovedClaim(id, description, amount, date, name, tags);
    }

    public RejectedClaim buildRejected() {
        return new RejectedClaim(id, description, amount, date, name, tags);
    }
}
