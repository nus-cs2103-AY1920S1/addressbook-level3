package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.commonvariables.Id;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Helps with building of contact object
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;
    private Set<Id> claims;

    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
        claims = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        tags = new HashSet<>(contactToCopy.getTags());
        claims = new HashSet<>(contactToCopy.getClaims());
    }

    /**
     * Sets the {@code Name} of the {@code FinSec} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FinSec} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code claims} into a {@code Set<Id>} and set it to the {@code FinSec} that we are building.
     */
    public ContactBuilder withClaims(String ... claims) {
        this.claims = SampleDataUtil.getClaimSet(claims);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FinSec} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code FinSec} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Contact build() {
        return new Contact(name, phone, email, tags, claims);
    }

}
