package io.xpire.model.state;

import io.xpire.logic.commands.Command;
import io.xpire.model.CloneModel;
import io.xpire.model.Model;

/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList.
 */
public class State {

    private CloneModel cloneModel = null;
    private Command command = null;

    public State(Model model) {
        this.cloneModel = clone(model);
    }

    /**
     * Clones a copy of model.
     */
    private CloneModel clone(Model model) {
        return new CloneModel(model.getXpire(), model.getReplenishList(), model.getUserPrefs(),
                model.getFilteredXpireItemList(), model.getFilteredReplenishItemList(),
                model.getCurrentFilteredItemList());
    }

    public CloneModel getCloneModel() {
        return cloneModel;
    }
}
