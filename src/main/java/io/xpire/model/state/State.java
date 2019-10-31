package io.xpire.model.state;

import io.xpire.model.CloneModel;
import io.xpire.model.Model;
import io.xpire.model.item.sort.XpireMethodOfSorting;

/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList.
 */
public class State {

    private static XpireMethodOfSorting method = new XpireMethodOfSorting("name");
    private CloneModel cloneModel = null;

    public State(Model model) {
        this.cloneModel = clone(model);
    }

    public State(Model model, XpireMethodOfSorting method) {
        this.cloneModel = clone(model);
        State.method = method;
    }

    /**
     * Clones a copy of model.
     */
    private CloneModel clone(Model model) {
        return new CloneModel(model.getXpire(), model.getReplenishList(), model.getUserPrefs(),
                model.getFilteredXpireItemList(), model.getFilteredReplenishItemList(),
                model.getListToView());
    }

    public CloneModel getCloneModel() {
        return cloneModel;
    }

    public static XpireMethodOfSorting getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof State)) {
            return false;
        } else {
            State other = (State) obj;
            return other.getCloneModel().equals(this.getCloneModel());
        }
    }
}
