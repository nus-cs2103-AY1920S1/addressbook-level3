package io.xpire;

import static io.xpire.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.model.tag.Tag.EXPIRED_TAG;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.storage.Storage;

/**
 * Checks expiry status of items in expiry date tracker.
 */
public class ItemManager {

    private static final Logger logger = LogsCenter.getLogger(ItemManager.class);

    private final Model model;
    private final Storage storage;

    /**
     * Manages item in Xpire storage based on their expiry date.
     *
     * @param model See {@code ModelManager}.
     * @param storage See {@code Storage}.
     */
    ItemManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    /**
     * Adds 'expired' as item tag if expiry date of item is past given expiry date.
     */
    public void updateItemTags() {
        tagExpiredItems();
        try {
            this.storage.saveList(this.model.getLists());
        } catch (IOException ioe) {
            logger.info(FILE_OPS_ERROR_MESSAGE + ioe);
        }
    }

    /**
     * Tags all expired items.
     */
    private void tagExpiredItems() {
        List<? extends Item> xpireItems = this.model.getItemList(XPIRE);
        XpireItem itemToCheck;
        for (int i = 0; i < xpireItems.size(); i++) {
            itemToCheck = (XpireItem) xpireItems.get(i);
            if (itemToCheck.isExpired()) {
                XpireItem updatedItem = getItemWithUpdatedTags(itemToCheck);
                model.setItem(XPIRE, itemToCheck, updatedItem);
            }
        }
    }

    /**
     * Instantiates a new Item with updated Tags.
     *
     * @param itemToUpdate Item that contains the tags and other information to be copied over.
     * @return a new Item with updated Tags.
     */
    private XpireItem getItemWithUpdatedTags(XpireItem itemToUpdate) {
        Set<Tag> newTagSet = new TreeSet<>(new TagComparator());
        newTagSet.addAll(itemToUpdate.getNewTagSet(new Tag(EXPIRED_TAG)));
        XpireItem updatedItem = new XpireItem(itemToUpdate.getName(), itemToUpdate.getExpiryDate(),
                itemToUpdate.getQuantity(), newTagSet, itemToUpdate.getReminderThreshold());
        return updatedItem;
    }
}
