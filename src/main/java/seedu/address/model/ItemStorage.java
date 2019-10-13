package seedu.address.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;

/**
 * The central storage of all the items in the program.
 */
public class ItemStorage {
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Adds an item to the item list.
     * @param item the item to be added to the item list.
     */
    public void add(Item item) {
        items.add(item);
    }

    /**
     * Retrieve the item list.
     * @return the item list.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * Gets the item at the index.
     * @param index the index of the item to be retrieved.
     * @return the item at that index.
     */
    public Item get(int index) {
        return items.get(index);
    }

    /**
     * Return the size of the storage.
     * @return the size of the storage as an integer.
     */
    public int size() {
        return items.size();
    }

    /**
     * Remove the item from within the storage.
     * @param item the item to be removed from the storage.
     * @return
     */
    public Item remove(Item item) {
        items.remove(item);
        return item;
    }

    /**
     * Returns the index of the item in the storage. If the item is not in the storage,
     * a index of -1 is returned.
     * @param item the item to be searched for.
     * @return the index of the item in the storage.
     */
    public int indexOf(Item item) {
        return items.indexOf(item);
    }

    /**
     * Set the item at the specific index to a new item.
     * @param index the index of the item to be replaced.
     * @param newItem the item to replace the old item.
     * @return
     */
    public Item setItem(int index, Item newItem) {
        return items.set(index, newItem);
    }

    /**
     * Converts the storage into a JSON string.
     * @return the JSON representation of the storage.
     * @throws JsonProcessingException
     */
    public String toJson() throws JsonProcessingException {
        return JsonUtil.toJsonString(items);
    }

    /**
     * Creates the item storage from a json string.
     * @param jsonString the string representation of the item storage.
     * @return the item storage with all items added
     * @throws IOException when the file cannot be read from
     * @throws DataConversionException when the item is not in a proper format
     */
    public static ItemStorage fromJson(String jsonString) throws IOException, DataConversionException {
        ItemStorage itemStorage = new ItemStorage();
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            String json = it.next().toString();
            try {
                Item item = Item.fromJson(json);
                itemStorage.add(item);
            } catch (NullPointerException e) {
                throw new DataConversionException(e);
            }
        }
        return itemStorage;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (!(other instanceof ItemStorage)) {
                return false;
            } else {
                return this.items.equals(((ItemStorage) other).items);
            }
        }
    }
}
