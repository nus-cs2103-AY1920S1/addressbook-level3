package seedu.savenus.model;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.Wallet;

/**
 * Unmodifiable view of an menu
 */
public interface ReadOnlyMenu {

    /**
     * Returns an unmodifiable view of the foods list.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns the user's wallet.
     */
    Wallet getWallet();

}
