package seedu.weme.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meme objects.
 */
public class MemeBuilder {

    public static final String DEFAULT_NAME = "Alice Kingsleigh";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Address address;
    private Set<Tag> tags;

    public MemeBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemeBuilder with the data of {@code memeToCopy}.
     */
    public MemeBuilder(Meme memeToCopy) {
        name = memeToCopy.getName();
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

    public Meme build() {
        return new Meme(name, address, tags);
    }

}
