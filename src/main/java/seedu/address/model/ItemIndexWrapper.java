package seedu.address.model;

import seedu.address.commons.core.item.Item;

/**
 * Stores an item along with its indices in the separate item lists
 * */

public class ItemIndexWrapper {
    private Item item;
    private int visual;
    private int storage;
    private int task;
    private int eve;
    private int rem;
    private int frem;

    public ItemIndexWrapper(Item item, int visual, int storage, int task, int eve, int rem, int frem) {
        this.item = item;
        this.storage = storage;
        this.visual = visual;
        this.task = task;
        this.eve = eve;
        this.rem = rem;
        this.frem = frem;
    }

    public int getVisual() {
        return visual;
    }

    public int getStorage() {
        return storage;
    }

    public int getTask() {
        return task;
    }

    public int getEve() {
        return eve;
    }

    public int getRem() {
        return rem;
    }

    public int getFrem() {
        return frem;
    }

    public Item getItem() {
        return item;
    }
}
