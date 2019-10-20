package seedu.address.model;

import seedu.address.commons.core.item.Item;

public class ItemIndexWrapper {
    Item item;
    int visual, storage, task, eve, rem, frem;
    public ItemIndexWrapper(Item item, int visual, int storage, int task, int eve, int rem, int frem){
        this.item = item;
        this.storage = storage;
        this.visual = visual;
        this.task = task;
        this.eve = eve;
        this.rem = rem;
        this.frem = frem;
    }
}
