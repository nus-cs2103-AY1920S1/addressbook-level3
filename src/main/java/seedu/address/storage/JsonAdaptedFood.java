package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Price;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String name;
    private final String price;
    private final String email;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFood} with the given food details.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("name") String name, @JsonProperty("price") String price,
                           @JsonProperty("email") String email,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.price = price;
        this.email = email;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        email = source.getEmail().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {
        final List<Tag> foodTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            foodTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Tag> modelTags = new HashSet<>(foodTags);
        return new Food(modelName, modelPrice, modelEmail, modelTags);
    }

}
