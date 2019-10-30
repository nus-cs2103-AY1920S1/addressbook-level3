package io.xpire.model;

import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import javafx.collections.transformation.FilteredList;

/**
 * A clone of the model that copies over the data from input model.
 */
public class CloneModel {

    private ReadOnlyListView<XpireItem> xpire;
    private ReadOnlyListView<Item> replenishList;
    private ReadOnlyUserPrefs userPrefs;
    private FilteredList<XpireItem> filteredXpireItemList;
    private FilteredList<Item> filteredReplenishItemList;
    private FilteredList<? extends Item> currentFilteredItemList;

    public CloneModel(ReadOnlyListView<XpireItem> xpire, ReadOnlyListView<Item> replenishList,
                      ReadOnlyUserPrefs userPrefs, FilteredList<XpireItem> filteredXpireItemList,
                      FilteredList<Item> filteredReplenishItemList,
                      FilteredList<? extends Item> currentFilteredItemList) {
        this.xpire = new Xpire(xpire);
        this.replenishList = new ReplenishList(replenishList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredXpireItemList = new FilteredList<>(filteredXpireItemList);
        this.filteredReplenishItemList = new FilteredList<>(filteredReplenishItemList);
        this.currentFilteredItemList = new FilteredList<>(currentFilteredItemList);
    }

    public ReadOnlyListView<XpireItem> getXpire() {
        return xpire;
    }

    public ReadOnlyListView<Item> getReplenishList() {
        return replenishList;
    }

    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    public FilteredList<XpireItem> getFilteredXpireItemList() {
        return filteredXpireItemList;
    }

    public FilteredList<Item> getFilteredReplenishItemList() {
        return filteredReplenishItemList;
    }

    public FilteredList<? extends Item> getCurrentFilteredItemList() {
        return currentFilteredItemList;
    }

}
