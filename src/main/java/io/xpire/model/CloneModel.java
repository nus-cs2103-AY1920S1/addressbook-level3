package io.xpire.model;

import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import javafx.collections.transformation.FilteredList;

/**
 * A clone of the model that copies over the data from input model.
 */
public class CloneModel {

    private final Xpire xpire;
    private final ReadOnlyListView<Item> replenishList;
    private final ReadOnlyUserPrefs userPrefs;
    private final FilteredList<? extends Item> currentList;

    public CloneModel(Xpire xpire, ReadOnlyListView<Item> replenishList,
                      ReadOnlyUserPrefs userPrefs, FilteredList<? extends Item> currentList,
                      XpireMethodOfSorting method) {
        this.xpire = new Xpire(xpire);
        this.xpire.setMethodOfSorting(method);
        this.replenishList = new ReplenishList(replenishList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.currentList = new FilteredList<>(currentList);
    }

    public CloneModel(Xpire xpire, ReadOnlyListView<Item> replenishList,
                      ReadOnlyUserPrefs userPrefs, FilteredList<? extends Item> currentList) {
        this.xpire = xpire;
        this.replenishList = replenishList;
        this.userPrefs = userPrefs;
        this.currentList = new FilteredList<>(currentList);
    }

    public ReadOnlyListView<XpireItem> getXpire() {
        return this.xpire;
    }

    public ReadOnlyListView<Item> getReplenishList() {
        return this.replenishList;
    }

    public ReadOnlyUserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    public FilteredList<? extends Item> getCurrentList() {
        return this.currentList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CloneModel)) {
            return false;
        } else {
            CloneModel other = (CloneModel) obj;
            System.out.println(other.currentList.getSource().containsAll(this.currentList.getSource()));
            return other.getXpire().getItemList().containsAll(this.getXpire().getItemList())
                    && other.getReplenishList().getItemList().containsAll(this.getReplenishList().getItemList())
                    && other.currentList.getSource().containsAll(this.currentList.getSource());
        }
    }
}
