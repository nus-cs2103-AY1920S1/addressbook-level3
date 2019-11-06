package seedu.jarvis.testutil.finance;

import static seedu.jarvis.testutil.finance.TypicalInstallments.getTypicalInstallments;
import static seedu.jarvis.testutil.finance.TypicalPurchases.getTypicalPurchases;

import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;

/**
 * A utility class containing a list of {@code Installment} and {@code Purchase} objects to be used in tests.
 */
public class TypicalFinances {

    /**
     * Returns a {@code FinanceTracker} with typical installments and purchases pre-loaded.
     *
     * @return a new {@code FinanceTracker} object
     */
    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker financeTracker = new FinanceTracker();
        for (Purchase purchase : getTypicalPurchases()) {
            financeTracker.addPurchaseToBack(purchase);
        }
        for (Installment installment : getTypicalInstallments()) {
            financeTracker.addInstallment(installment);
        }
        return financeTracker;
    }
}
