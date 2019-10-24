package io.xpire.model.state;

import java.util.List;

import io.xpire.model.Model;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList.
 */
public class State {

    private Xpire xpire;
    private UserPrefs userPrefs;
    private List<Item> filteredList;

    public State(Model model) {
        this.xpire = new Xpire(model.getXpire());
        this.userPrefs = new UserPrefs(model.getUserPrefs());
        this.filteredList = this.xpire.getItemList(); //need to work around it
    }
}
