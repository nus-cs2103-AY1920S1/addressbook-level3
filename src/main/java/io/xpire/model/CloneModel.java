package io.xpire.model;

import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import javafx.collections.transformation.FilteredList;

/**
 * A clone of the model that copies over the data from input model.
 */
public class CloneModel {

    private Xpire xpire;
    private ReadOnlyListView<Item> replenishList;
    private ReadOnlyUserPrefs userPrefs;
    private FilteredList<XpireItem> filteredXpireItemList;
    private FilteredList<Item> filteredReplenishItemList;
    private ListToView listToView;
    private FilteredList<? extends Item> currentList;

    public CloneModel(Xpire xpire, ReadOnlyListView<Item> replenishList,
                      ReadOnlyUserPrefs userPrefs, FilteredList<XpireItem> filteredXpireItemList,
                      FilteredList<Item> filteredReplenishItemList,
                      ListToView listToView, XpireMethodOfSorting method) {
        this.xpire = new Xpire(xpire);
        this.xpire.setMethodOfSorting(method);
        this.replenishList = new ReplenishList(replenishList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredXpireItemList = new FilteredList<>(this.xpire.getItemList());
        this.filteredXpireItemList.setPredicate(filteredXpireItemList.getPredicate());
        this.filteredReplenishItemList = new FilteredList<>(this.replenishList.getItemList());
        this.filteredReplenishItemList.setPredicate(filteredReplenishItemList.getPredicate());
        this.listToView = listToView;
        this.currentList = checkListToView(this.listToView);
    }

    public CloneModel(Xpire xpire, ReadOnlyListView<Item> replenishList,
                      ReadOnlyUserPrefs userPrefs, FilteredList<XpireItem> filteredXpireItemList,
                      FilteredList<Item> filteredReplenishItemList,
                      ListToView listToView) {
        this.xpire = xpire;
        this.replenishList = replenishList;
        this.userPrefs = userPrefs;
        this.filteredXpireItemList = new FilteredList<>(this.xpire.getItemList());
        this.filteredXpireItemList.setPredicate(filteredXpireItemList.getPredicate());
        this.filteredReplenishItemList = new FilteredList<>(this.replenishList.getItemList());
        this.filteredReplenishItemList.setPredicate(filteredReplenishItemList.getPredicate());
        this.listToView = listToView;
        this.currentList = checkListToView(listToView);
    }

    /**
     * Checks the model's list to view
     */
    private FilteredList<? extends Item> checkListToView(ListToView listToView) {
        if (listToView.equals(new ListToView("main"))) {
            return filteredXpireItemList;
        } else {
            return filteredReplenishItemList;
        }
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

    public ListToView getListToView() {
        return listToView;
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
