package seedu.deliverymans.storage.restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String name;
    private final String price;
    private final int quantity;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFood} with the given {@code foodName}.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("name") String name,
                           @JsonProperty("price") String price,
                           @JsonProperty("quantity") int quantity,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        name = source.getName().fullName;
        price = source.getPrice().toPlainString();
        quantity = source.getQuantity();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "price"));
        }
        final BigDecimal modelPrice = ParserUtil.parsePrice(price);
        if (!Food.isValidPrice(modelPrice)) {
            throw new IllegalValueException(Food.PRICE_CONSTRAINTS);
        }

        if (!Food.isValidQuantity(quantity)) {
            throw new IllegalValueException(Food.QUANTITY_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(foodTags);
        return new Food(modelName, modelPrice, quantity, modelTags);
    }
}
