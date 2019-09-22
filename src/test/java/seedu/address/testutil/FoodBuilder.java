package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        phone = foodToCopy.getPhone();
        email = foodToCopy.getEmail();
        tags = new HashSet<>(foodToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Food} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Food} that we are building.
     */
    public FoodBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Food} that we are building.
     */
    public FoodBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Food build() {
        return new Food(name, phone, email, tags);
    }

}
