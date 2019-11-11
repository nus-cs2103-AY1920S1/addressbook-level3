package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import seedu.address.model.order.Price;
import seedu.address.model.phone.Cost;

/**
 * Utility methods related to Price and Cost.
 */
public class MoneyUtil {

    /**
     * Convert {@Code price} to a corresponding {@Code Double}.
     * @param price A price. Cannot be null.
     * @return The price as a Double.
     */
    public static Double convertToDouble(Price price) {
        requireNonNull(price);
        String priceString = price.toString();
        String value = priceString.substring(1); // remove $
        return Double.parseDouble(value);
    }

    /**
     * Convert {@Code cost} to a corresponding {@Code Double}.
     * @param cost A Cost. Cannot be null.
     * @return The cost as a Double.
     */
    public static Double convertToDouble(Cost cost) {
        requireNonNull(cost);
        String costString = cost.toString();
        String value = costString.substring(1);
        return Double.parseDouble(value);
    }

}
