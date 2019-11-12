package io.xpire.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;

//@@author febee99
/**
 * An Immutable Xpire that is serializable to JSON format.
 */
@JsonRootName(value = "xpire")
public class JsonSerializableList {

    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";
    private final List<JsonAdaptedItem> replenishItems = new ArrayList<>();
    private final List<JsonAdaptedXpireItem> xpireItems = new ArrayList<>();

    @JsonCreator
    public JsonSerializableList(@JsonProperty("xpireItems") List<JsonAdaptedXpireItem> xpireItems,
                                @JsonProperty("replenishItems") List<JsonAdaptedItem> replenishItems) {
        this.xpireItems.addAll(xpireItems);
        this.replenishItems.addAll(replenishItems);
    }

    /**
     * Converts a given {@code ReadOnlyListView} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableXpire}.
     */
    @SuppressWarnings("unchecked")
    public JsonSerializableList(ReadOnlyListView[] source) {
        ReadOnlyListView<XpireItem> xpirelist = (ReadOnlyListView<XpireItem>) source[0];
        ReadOnlyListView<Item> replenishlist = (ReadOnlyListView<Item>) source[1];
        xpireItems.addAll(xpirelist.getItemList().stream().map(JsonAdaptedXpireItem::new)
                .collect(Collectors.toList()));
        replenishItems.addAll(replenishlist.getItemList()
                .stream().map(JsonAdaptedItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expiry date tracker into the model's {@code ExpiryDateTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReadOnlyListView[] toModelType() throws IllegalValueException {
        Xpire xpire = new Xpire();
        for (JsonAdaptedXpireItem jsonAdaptedXpireItem : xpireItems) {
            XpireItem xpireItem = jsonAdaptedXpireItem.toModelType();
            if (xpire.hasItem(xpireItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            xpire.addItem(xpireItem);
        }
        ReplenishList replenishList = new ReplenishList();
        for (JsonAdaptedItem jsonAdaptedItem : replenishItems) {
            Item item = jsonAdaptedItem.toModelType();
            if (replenishList.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            replenishList.addItem(item);
        }
        return new ReadOnlyListView[]{xpire, replenishList};
    }
}
