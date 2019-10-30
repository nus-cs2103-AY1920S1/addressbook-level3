package io.xpire;

import static io.xpire.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;

import java.io.IOException;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.Model;
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
        model.updateItemTags();
        try {
            this.storage.saveList(this.model.getLists());
        } catch (IOException ioe) {
            logger.info(FILE_OPS_ERROR_MESSAGE + ioe);
        }
    }

}
