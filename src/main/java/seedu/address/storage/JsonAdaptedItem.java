package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.ExpiryDate;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Item}.
 */
class JsonAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String name;
    private final String expiryDate;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedItem} with the given item details.
     */
    @JsonCreator
    public JsonAdaptedItem(@JsonProperty("name") String name, @JsonProperty("expiryDate") String expiryDate,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.expiryDate = expiryDate;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Item} into this class for Jackson use.
     */
    public JsonAdaptedItem(Item source) {
        name = source.getName().fullName;
        expiryDate = source.getExpiryDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted item object into the model's {@code Item} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpiryDate.class.getSimpleName()));
        }
        if (!ExpiryDate.isValidExpiryDate(expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);

        final Set<Tag> modelTags = new HashSet<>(itemTags);
        return new Item(modelName, modelExpiryDate, modelTags);
    }

}
