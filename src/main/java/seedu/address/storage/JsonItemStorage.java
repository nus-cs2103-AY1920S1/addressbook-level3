package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ItemStorage;

/**
 * A class that contains all the JSON representation of the string
 */
public class JsonItemStorage {
    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate items";

    private final List<String> items = new ArrayList<>();

    public JsonItemStorage(List<String> items) {
        this.items.addAll(items);
    }

    /**
     * Converts this JSON item storage into the model's storage.
     * @return an ItemStorage with all the items
     * @throws IllegalValueException if there were any data constraints violated.
     * @throws IOException if there are any problem with reading from the string.
     */
    public ItemStorage toModelType() throws IllegalValueException, IOException {
        ItemStorage itemStorage = new ItemStorage();
        for (String itemString: items) {
            Item item = Item.fromJson(itemString);
            itemStorage.add(item);
        }
        return itemStorage;
    }
}
