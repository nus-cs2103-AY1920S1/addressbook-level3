package seedu.savenus.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.Wallet;

/**
 * Unmodifiable view of an address book
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

    /**
     * Returns the user's {@code RemainingBudget}'s {@code FloatProperty}.
     */
    FloatProperty getRemainingBudgetProperty();

    /**
     * Returns the user's {@code DaysToExpire}'s {@code IntegerProperty}.
     */
    IntegerProperty getDaysToExpireProperty();
}
