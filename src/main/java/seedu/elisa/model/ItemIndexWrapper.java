package seedu.elisa.model;

import seedu.elisa.commons.core.item.Item;

/**
 * Stores an item along with its indices in the separate item lists
 * */

public class ItemIndexWrapper {
    private Item item; //the item
    private int visual; //its index in VisualizeList
    private int storage; //index in ItemStorage
    private int task; //index in TaskList
    private int eve; //index in EventList
    private int rem; //index in ReminderList
    private int frem; //index in FutureReminders
    private int arem; //index in ActiveReminders
    //All indices are -1 if not in the respective list.

    public ItemIndexWrapper(Item item, int visual, int storage, int task, int eve, int rem, int frem, int arem) {
        this.item = item;
        this.storage = storage;
        this.visual = visual;
        this.task = task;
        this.eve = eve;
        this.rem = rem;
        this.frem = frem;
        this.arem = arem;
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

    public int getArem() {
        return arem;
    }
}
