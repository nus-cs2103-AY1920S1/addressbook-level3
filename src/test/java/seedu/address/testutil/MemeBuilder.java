package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.meme.Address;
import seedu.address.model.meme.Email;
import seedu.address.model.meme.Meme;
import seedu.address.model.meme.Name;
import seedu.address.model.meme.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meme objects.
 */
public class MemeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public MemeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemeBuilder with the data of {@code memeToCopy}.
     */
    public MemeBuilder(Meme memeToCopy) {
        name = memeToCopy.getName();
        phone = memeToCopy.getPhone();
        email = memeToCopy.getEmail();
        address = memeToCopy.getAddress();
        tags = new HashSet<>(memeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Meme} that we are building.
     */
    public MemeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Meme} that we are building.
     */
    public MemeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Meme} that we are building.
     */
    public MemeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Meme} that we are building.
     */
    public MemeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Meme} that we are building.
     */
    public MemeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Meme build() {
        return new Meme(name, phone, email, address, tags);
    }

}
