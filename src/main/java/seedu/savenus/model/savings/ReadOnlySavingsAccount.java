package seedu.savenus.model.savings;

import javafx.beans.property.ObjectProperty;
import seedu.savenus.model.util.Money;

/**
 * An unmodifiable SavingsAccount that shows only the total account balance but does not allow modifications directly.
 */
public interface ReadOnlySavingsAccount {
    /**
     * Returns an unmodifiable view of the SavingsAccount.
     */
    ObjectProperty<Money> getCurrentSavings();
}
