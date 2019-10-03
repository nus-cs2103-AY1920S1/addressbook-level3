package io.xpire.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.ExpiryDateTracker;
import io.xpire.model.ReadOnlyExpiryDateTracker;
import io.xpire.model.item.Item;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableExpiryDateTracker {

    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";

    private final List<JsonAdaptedItem> items = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExpiryDateTracker} with the given items.
     */
    @JsonCreator
    public JsonSerializableExpiryDateTracker(@JsonProperty("items") List<JsonAdaptedItem> items) {
        this.items.addAll(items);
    }

    /**
     * Converts a given {@code ReadOnlyExpiryDateTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExpiryDateTracker}.
     */
    public JsonSerializableExpiryDateTracker(ReadOnlyExpiryDateTracker source) {
        items.addAll(source.getItemList().stream().map(JsonAdaptedItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expiry date tracker into the model's {@code ExpiryDateTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExpiryDateTracker toModelType() throws IllegalValueException {
        ExpiryDateTracker expiryDateTracker = new ExpiryDateTracker();
        for (JsonAdaptedItem jsonAdaptedItem : items) {
            Item item = jsonAdaptedItem.toModelType();
            if (expiryDateTracker.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            expiryDateTracker.addItem(item);
        }
        return expiryDateTracker;
    }

}
