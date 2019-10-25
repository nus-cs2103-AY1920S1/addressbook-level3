package seedu.ifridge.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
public class JsonAdaptedGroceryItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grocery item's %s field is missing!";

    private final String name;
    private final String amount;
    private final String expiryDate;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedGroceryItem(
            @JsonProperty("name") String name,
            @JsonProperty("amount") String amount,
            @JsonProperty("expiryDate") String expiryDate,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.amount = amount;
        this.expiryDate = expiryDate;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedGroceryItem(GroceryItem source) {
        name = source.getName().fullName;
        amount = source.getAmount().fullAmt;
        expiryDate = source.getExpiryDate().expiryDate;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public GroceryItem toModelType() throws IllegalValueException {
        final List<Tag> groceryListTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            groceryListTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Amount modelAmount = new Amount(amount);
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);

        final Set<Tag> modelTags = new HashSet<>(groceryListTags);
        return new GroceryItem(modelName, modelAmount, modelExpiryDate, modelTags);
    }

}
