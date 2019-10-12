package seedu.address.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.IllegalValueException;
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

    public String toJson() throws JsonProcessingException{
        return JsonUtil.toJsonString(items.getList());
    }

    public static ItemStorage fromJson(String jsonString) throws IOException, IllegalValueException{
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
