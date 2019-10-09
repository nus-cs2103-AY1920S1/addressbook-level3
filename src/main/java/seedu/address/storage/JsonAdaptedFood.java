package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Category;
import seedu.address.model.food.Description;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Price;
import seedu.address.model.food.OpeningHours;
import seedu.address.model.food.Restrictions;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String name;
    private final String price;
    private final String description;
    private final String category;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String openingHours;
    private final String restrictions;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given food details.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("name") String name, @JsonProperty("price") String price,
                           @JsonProperty("description") String description,
                           @JsonProperty("category") String category,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("openingHours") String openingHours,
                           @JsonProperty("restrictions") String restrictions) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.openingHours = openingHours;
        this.restrictions = restrictions;
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        description = source.getDescription().value;
        category = source.getCategory().category;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        openingHours = source.getOpeningHours().openingHours;
        restrictions = source.getRestrictions().restrictions;
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

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDesciption = new Description(description);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = new Category(category);

        if (openingHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OpeningHours.class.getSimpleName()));
        }
        if (!OpeningHours.isValidOpeningHours(openingHours)) {
            throw new IllegalValueException(OpeningHours.MESSAGE_CONSTRAINTS);
        }
        final OpeningHours modelOpeningHours = new OpeningHours(openingHours);

        if (restrictions == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Restrictions.class.getSimpleName()));
        }
        if (!Restrictions.isValidRestrictions(restrictions)) {
            throw new IllegalValueException(Restrictions.MESSAGE_CONSTRAINTS);
        }
        final Restrictions modelRestrictions = new Restrictions(restrictions);

        final Set<Tag> modelTags = new HashSet<>(foodTags);

        return new Food(modelName, modelPrice, modelDesciption, modelCategory, modelTags,
                        modelOpeningHours, modelRestrictions);
    }
}
