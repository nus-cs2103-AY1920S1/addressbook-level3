package seedu.address.model;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.item.ItemList;

/**
 * The central storage of all the items in the program.
 */
public class ItemStorage {
    private ItemList items = new ItemList();

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
    public ItemList getItems() {
        return this.items;
    }

    public String toJson() throws JsonProcessingException {
        return JsonUtil.toJsonString(items.getList());
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
            Item item = Item.fromJson(json);
            itemStorage.add(item);
        }
        return itemStorage;
    }
}
