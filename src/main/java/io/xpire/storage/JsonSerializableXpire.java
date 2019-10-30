package io.xpire.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.Xpire;
import io.xpire.model.item.XpireItem;

/**
 * An Immutable Xpire that is serializable to JSON format.
 */
@JsonRootName(value = "xpire")
class JsonSerializableXpire {

    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";

    private final List<JsonAdaptedXpireItem> items = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableXpire} with the given items.
     */
    @JsonCreator
    public JsonSerializableXpire(@JsonProperty("items") List<JsonAdaptedXpireItem> items) {
        this.items.addAll(items);
    }


    /**
     * Converts a given {@code ReadOnlyListView} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableXpire}.
     */
    public JsonSerializableXpire(ReadOnlyListView<XpireItem> source) {
        items.addAll(source.getItemList().stream().map(JsonAdaptedXpireItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expiry date tracker into the model's {@code ExpiryDateTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Xpire toModelType() throws IllegalValueException {
        Xpire xpire = new Xpire();
        for (JsonAdaptedXpireItem jsonAdaptedXpireItem : items) {
            XpireItem xpireItem = jsonAdaptedXpireItem.toModelType();
            if (xpire.hasItem(xpireItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            xpire.addItem(xpireItem);
        }
        return xpire;
    }

}
