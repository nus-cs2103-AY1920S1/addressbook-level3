package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.testutil.finance.PurchaseBuilder;

/**
 * A utility class containing a list of {@code Purchase} objects to be used in tests.
 */
public class TypicalPurchases {

    public static final Purchase LUNCH_JAPANESE = new PurchaseBuilder()
            .withDescription("Lunch at Deck Japanese stall")
            .withMoneySpent("4.50")
            .build();

    public static final Purchase DINNER_REEDZ = new PurchaseBuilder()
            .withDescription("Dinner at Reedz Cafe")
            .withMoneySpent("5.90")
            .build();
}
