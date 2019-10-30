package io.xpire.model;

import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.SortedUniqueReplenishItemList;
import io.xpire.model.item.SortedUniqueXpireItemList;
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
                      ListToView listToView) {
        this.xpire = new Xpire(xpire);
        this.replenishList = new ReplenishList(replenishList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredXpireItemList = new FilteredList<>(this.xpire.getItemList());
        this.filteredXpireItemList.setPredicate(filteredXpireItemList.getPredicate());
        this.filteredReplenishItemList = new FilteredList<>(this.replenishList.getItemList());
        this.filteredReplenishItemList.setPredicate(filteredReplenishItemList.getPredicate());
        //this.filteredXpireItemList = cloneFilteredXpireItemList(filteredXpireItemList);
        //this.filteredReplenishItemList = cloneFilteredReplenishItemList(filteredReplenishItemList);
        this.currentFilteredItemList = checkListToView(listToView);
        //this.currentFilteredItemList.forEach(System.out::println);
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

    /**
     * Clones the FilteredXpireItemList.
     */
    private FilteredList<XpireItem> cloneFilteredXpireItemList(FilteredList<XpireItem> filteredXpireItemList) {
        SortedUniqueXpireItemList items = new SortedUniqueXpireItemList();
        for (XpireItem item: filteredXpireItemList) {
            System.out.println("....");
            System.out.println(item);
            System.out.println("....");
            items.add(new XpireItem(item));
        }
        FilteredList<XpireItem> result = new FilteredList<>(items.asUnmodifiableObservableList());
        result.setPredicate(filteredXpireItemList.getPredicate());
        System.out.println("GGGGG");
        result.forEach(System.out::println);
        System.out.println("GGGGG");
        return result;
    }

    /**
     * Clones the FilteredReplenishItemList.
     */
    private FilteredList<Item> cloneFilteredReplenishItemList(FilteredList<Item> filteredReplenishItemList) {
        SortedUniqueReplenishItemList items = new SortedUniqueReplenishItemList();
        for (Item item: filteredReplenishItemList) {
            items.add(new Item(item));
        }
        FilteredList<Item> result = new FilteredList<>(items.asUnmodifiableObservableList());
        result.setPredicate(filteredReplenishItemList.getPredicate());
        //result.forEach(System.out::println);
        return result;
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
