package io.xpire.testutil;

import io.xpire.model.ReplenishList;
import io.xpire.model.item.Item;

/**
 * Replenish List Builder.
 */
public class ReplenishListBuilder {

    private ReplenishList replenishList;

    public ReplenishListBuilder() {
        replenishList = new ReplenishList();
    }

    public ReplenishListBuilder(ReplenishList replenishList) {
        this.replenishList = replenishList;
    }

    /**
     * Adds a new {@code Item} to the {@code ReplenishList} that we are building.
     */
    public ReplenishListBuilder withItem(Item item) {
        replenishList.addItem(item);
        return this;
    }

    public ReplenishList build() {
        return replenishList;
    }
}
