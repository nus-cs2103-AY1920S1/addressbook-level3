package seedu.address.storage.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ShoppingList;
import seedu.address.model.food.ShoppingItem;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "shoppinglist")
class JsonSerializableShoppingList {

    public static final String MESSAGE_DUPLICATE_SHOPPING_ITEMS = "Shopping list contains duplicate shopping_items(s).";

    private final List<JsonAdaptedShoppingItem> shoppingItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableShoppingList(@JsonProperty("shoppingItems") List<JsonAdaptedShoppingItem> shoppingItems) {
        this.shoppingItems.addAll(shoppingItems);
    }

    /**
     * Converts a given {@code ReadOnlyShoppingList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableShoppingList}.
     */
    public JsonSerializableShoppingList(ReadOnlyShoppingList source) {
        shoppingItems.addAll(source.getShoppingList().stream()
                .map(JsonAdaptedShoppingItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this shopping list into the model's {@code ShoppingList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ShoppingList toModelType() throws IllegalValueException {
        ShoppingList shoppingList = new ShoppingList();
        for (JsonAdaptedShoppingItem jsonAdaptedShoppingItem : shoppingItems) {
            ShoppingItem shoppingItem = jsonAdaptedShoppingItem.toModelType();
            if (shoppingList.hasShoppingItem(shoppingItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SHOPPING_ITEMS);
            }
            shoppingList.addShoppingItem(shoppingItem);
        }
        return shoppingList;
    }

}
