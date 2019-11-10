package io.xpire.model;

import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.State.StateType;

/**
 * A clone of the model that copies over the data from input model.
 * @@author Kalsyc
 */
public class CloneModel {

    private final Xpire xpire;
    private final ReplenishList replenishList;
    private final ReadOnlyUserPrefs userPrefs;

    public CloneModel(Xpire xpire, ReplenishList replenishList,
                      ReadOnlyUserPrefs userPrefs, StateType stateType) {
        switch(stateType) {
        case FILTERED:
            this.xpire = xpire;
            this.replenishList = replenishList;
            this.userPrefs = userPrefs;
            break;
        case MODIFIED:
            this.xpire = new Xpire(xpire);
            this.replenishList = new ReplenishList(replenishList);
            this.userPrefs = new UserPrefs(userPrefs);
            break;
        default:
            this.xpire = null;
            this.replenishList = null;
            this.userPrefs = null;
        }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CloneModel)) {
            return false;
        } else {
            CloneModel other = (CloneModel) obj;
            return other.getXpire().getItemList().containsAll(this.getXpire().getItemList())
                    && other.getReplenishList().getItemList().containsAll(this.getReplenishList().getItemList())
                    && other.getUserPrefs().equals(this.getUserPrefs());
        }
    }

}
