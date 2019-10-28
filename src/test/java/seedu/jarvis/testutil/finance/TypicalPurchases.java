package seedu.jarvis.testutil.finance;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.finance.purchase.Purchase;

/**
 * A utility class containing a list of {@code Purchase} objects to be used in tests.
 */
public class TypicalPurchases {

    public static final Purchase LUNCH_JAPANESE = new PurchaseBuilder()
            .withDescription("Lunch at Deck Japanese stall")
            .withMoneySpent("4.50")
            .withDateOfPurchase("29/11/2019")
            .build();

    public static final Purchase DINNER_REEDZ = new PurchaseBuilder()
            .withDescription("Dinner at Reedz Cafe")
            .withMoneySpent("5.90")
            .withDateOfPurchase("29/11/2019")
            .build();

    public static List<Purchase> getTypicalPurchases() {
        return Arrays.asList(LUNCH_JAPANESE, DINNER_REEDZ);
    }
}
