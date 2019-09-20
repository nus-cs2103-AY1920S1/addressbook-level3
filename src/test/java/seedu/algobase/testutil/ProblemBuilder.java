package seedu.algobase.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.algobase.model.Problem.Address;
import seedu.algobase.model.Problem.Email;
import seedu.algobase.model.Problem.Name;
import seedu.algobase.model.Problem.Problem;
import seedu.algobase.model.Problem.Phone;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.util.SampleDataUtil;

/**
 * A utility class to help with building Problem objects.
 */
public class ProblemBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public ProblemBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProblemBuilder with the data of {@code problemToCopy}.
     */
    public ProblemBuilder(Problem problemToCopy) {
        name = problemToCopy.getName();
        phone = problemToCopy.getPhone();
        email = problemToCopy.getEmail();
        address = problemToCopy.getAddress();
        tags = new HashSet<>(problemToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Problem} that we are building.
     */
    public ProblemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Problem} that we are building.
     */
    public ProblemBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Problem build() {
        return new Problem(name, phone, email, address, tags);
    }

}
