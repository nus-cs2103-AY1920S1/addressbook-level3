package io.xpire.model.state;

import io.xpire.model.CloneModel;
import io.xpire.model.Model;
import io.xpire.model.item.sort.XpireMethodOfSorting;

/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList. FilteredState denotes
 * a state in which the current view has been modified but not the items.
 */
public class FilteredState implements State {

    private XpireMethodOfSorting method;
    private CloneModel cloneModel;


    public FilteredState(Model model) {
        this.method = model.getXpire().getMethodOfSorting();
        this.cloneModel = clone(model, this.method);
    }

    /**
     * Clones a complete copy of model.
     */
    public CloneModel clone(Model model, XpireMethodOfSorting method) {
        return new CloneModel(model.getXpire(), model.getReplenishList(), model.getUserPrefs(),
                model.getFilteredXpireItemList(), model.getFilteredReplenishItemList(),
                model.getListToView());
    }

    public CloneModel getCloneModel() {
        return this.cloneModel;
    }

    public XpireMethodOfSorting getMethod() {
        return this.method;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FilteredState)) {
            return false;
        } else {
            FilteredState other = (FilteredState) obj;
            return other.getCloneModel().equals(this.getCloneModel());
        }
    }
}
